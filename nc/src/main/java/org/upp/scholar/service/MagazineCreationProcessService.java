package org.upp.scholar.service;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upp.scholar.entity.Magazine;
import org.upp.scholar.entity.User;
import org.upp.scholar.model.*;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MagazineCreationProcessService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private MagazineService magazineService;

    public FormFieldsDto startMagazineCreationProcess(String editor){
        User editorUser = this.userService.findByUsername(editor);
        if (editorUser == null || !editorUser.getAuthorities().contains(this.authorityService.findByName("ROLE_EDITOR"))){
            throw new NotFoundException();
        }

        this.identityService.setAuthenticatedUserId(editor);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("kreiranje_novog_casopisa");
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> formFields = taskFormData.getFormFields();
        return new FormFieldsDto(task.getId(), formFields, processInstance.getId());
    }

    public void saveMagazine(List<FormSubmissionDto> formResults, String taskId){
        HashMap<String, Object> formMap = this.mapListToDto(formResults);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "newMagazine", formMap);
        formService.submitTaskForm(taskId, formMap);
    }

    public FormFieldsDto getEditorAndReviewerFormField(String processInstanceId, String editor){
        Task task = this.taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskDefinitionKey("biranje_editora_i_recenzenata")
                .active()
                .singleResult();

        if (task == null) {
            throw new NotFoundException();
        }

        /*if (!task.getAssignee().equals(editor)){
            throw new NotFoundException();
        }*/

        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> formFields = taskFormData.getFormFields();
        return new FormFieldsDto(task.getId(), formFields, processInstanceId);
    }

    public void saveEditorsAndReviewers(EditorsAndReviewersDto editorsAndReviewersDto, String taskId){
        Task task = this.taskService.createTaskQuery().active().taskId(taskId).singleResult();
        if (task == null){
            throw new NotFoundException();
        }
        List<FormSubmissionDto> formResults = new ArrayList<>();
        formResults.add(editorsAndReviewersDto.getEditors());
        formResults.add(editorsAndReviewersDto.getReviewers());
        HashMap<String, Object> formMap = this.mapListToDto(formResults);
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "editors_and_reviewers", formMap);
        runtimeService.setVariable(processInstanceId, "magazine_issn", editorsAndReviewersDto.getIssn());
        formService.submitTaskForm(taskId, formMap);
        System.out.println("Form submitted");
    }

    public List<TaskDto> getActiveCheckMagazineTasks(){
        System.out.println("Get tasks");
        return this.taskService.createTaskQuery()
                .taskDefinitionKey("provera_podataka")
                .active()
                .list()
                .stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
    }

    public FormFieldsDto getCheckMagazineFormFields(String taskId){
        System.out.println("Check reviewer form fields, task id: " + taskId);
        List<FormField> formFields = this.formService.getTaskFormData(taskId).getFormFields();
        return FormFieldsDto.builder()
                .taskId(taskId)
                .formFields(formFields)
                .build();
    }

    public void checkMagazine(CheckReviewerDto checkReviewerDto){
        System.out.println("Check magazine" + checkReviewerDto);
        HashMap<String, Object> formFields = new HashMap<>();
        formFields.put("validni_podaci", checkReviewerDto.isFlag());
        this.formService.submitTaskForm(checkReviewerDto.getTaskId(), formFields);
        System.out.println("Submit form" + formFields);
    }

    public FormFieldsDto getEditedFormFields(String processInstanceId){
        Task task = this.taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskDefinitionKey("kreiranje_casopisa")
                .active()
                .singleResult();

        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> formFields = taskFormData.getFormFields();
        return new FormFieldsDto(task.getId(), formFields, task.getProcessInstanceId());
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDto temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }
}
