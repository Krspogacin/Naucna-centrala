package org.upp.scholar.service;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.upp.scholar.entity.Authority;
import org.upp.scholar.entity.ScientificArea;
import org.upp.scholar.entity.User;
import org.upp.scholar.model.CheckReviewerDto;
import org.upp.scholar.model.FormFieldsDto;
import org.upp.scholar.model.TaskDto;
import org.upp.scholar.repository.UserRepository;

import javax.ws.rs.NotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User with username '" + username + "' is not found");
        return user;
    }

    public List<User> findAll(){
        return this.userRepository.findAll();
    }

    public User findByUsername(String username){
        return this.userRepository.findByUsername(username);
    }

    public User findOne(Long id){
        User user = this.userRepository.getOne(id);
        if (user == null){
            throw new NotFoundException();
        }
        return user;
    }

    public List<User> findEditorAndReviewers(String username, Authority authority, List<ScientificArea> scientificAreas){
        return this.userRepository.findDistinctByUsernameNotAndAuthoritiesContainsAndScientificAreasIsIn(username, authority, scientificAreas);
    }


    public void verifyUser(String processInstanceId, String username){
        /*Task task = this.taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskDefinitionKey("aktivacija_korisnika")
                .active()
                .singleResult();

        if (task == null) {
            throw new NotFoundException();
        }
        final List<FormField> formFields = this.formService.getTaskFormData(task.getId()).getFormFields();
        FormField formField = formFields.get(0);
        Map<String, Object> confirmForm = new HashMap<>();
        confirmForm.put(formField.getId(), formField.getValue());
        this.formService.submitTaskForm(task.getId(), confirmForm);*/

        User user = this.findByUsername(username);
        if (user == null){
            throw new NotFoundException();
        }
        user.setEnabled(true);
        Authority userAuthority = this.authorityService.findByName("ROLE_USER");
        Set<Authority> authorities = new HashSet<>();
        authorities.add(userAuthority);
        user.setAuthorities(authorities);
        this.userRepository.save(user);
        this.runtimeService.setVariable(processInstanceId, "korisnik_verifikovan", true);
    }

    public List<TaskDto> getActiveCheckReviewerTasks(){
        System.out.println("Get tasks");
        return this.taskService.createTaskQuery()
                .taskDefinitionKey("potvrda")
                .active()
                .list()
                .stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
    }

    public FormFieldsDto getCheckReviewerFormFields(String taskId){
        System.out.println("Check reviewer form fields, task id: " + taskId);
        List<FormField> formFields = this.formService.getTaskFormData(taskId).getFormFields();
        return FormFieldsDto.builder()
                .taskId(taskId)
                .formFields(formFields)
                .build();

    }

    public void checkReviewer(CheckReviewerDto checkReviewerDto){
        System.out.println("Check reviewer" + checkReviewerDto);
        Task task = this.taskService.createTaskQuery().taskId(checkReviewerDto.getTaskId()).singleResult();
        this.runtimeService.setVariable(task.getProcessInstanceId(), "reviewerFlag", checkReviewerDto.isFlag());
        HashMap<String, Object> formFields = new HashMap<>();
        formFields.put("potvrdi", checkReviewerDto.isFlag());
        this.formService.submitTaskForm(checkReviewerDto.getTaskId(), formFields);
        System.out.println("Submit form" + formFields);
    }
}
