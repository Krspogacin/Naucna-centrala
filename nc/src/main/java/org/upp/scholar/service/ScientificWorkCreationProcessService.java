package org.upp.scholar.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.upp.scholar.entity.Magazine;
import org.upp.scholar.entity.ScientificWork;
import org.upp.scholar.entity.User;
import org.upp.scholar.model.*;
import org.upp.scholar.repository.ScientificWorkRepository;

import javax.ws.rs.NotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScientificWorkCreationProcessService {

    @Autowired
    private UserService userService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private ScientificWorkRepository scientificWorkRepository;

    public FormFieldsDto startProcess(String username){
        log.info("Start process");
        User user = this.userService.findByUsername(username);
        if (user == null){
            throw new NotFoundException("User with username: " + username + " not found");
        }

        this.identityService.setAuthenticatedUserId(username);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("proces_obrade_podnetog_teksta");

        this.runtimeService.setVariable(processInstance.getId(), "user", user);

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> formFields = taskFormData.getFormFields();
        return new FormFieldsDto(task.getId(), formFields, processInstance.getId());
    }

    public void getScientificWorkMagazine (List<FormSubmissionDto> formResults, String taskId){
        HashMap<String, Object> formMap = this.mapListToDto(formResults);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null){
            throw new NotFoundException("Task with id: " + taskId + " not found");
        }
        String processInstanceId = task.getProcessInstanceId();
        Magazine magazine = this.magazineService.findById(Long.parseLong(formMap.get("casopis").toString()));
        if (magazine == null){
            throw new NotFoundException("Magazine with id:" + Long.parseLong(formMap.get("casopis").toString()) + " not found" );
        }
        runtimeService.setVariable(processInstanceId, "scientificWorkMagazine", magazine);
        runtimeService.setVariable(processInstanceId, "glavni_urednik", magazine.getChiefEditor().getUsername());
        log.info("Glavni urednik changed: " + magazine.getChiefEditor().getUsername());
        runtimeService.setVariable(processInstanceId, "glavni_urednik_email", magazine.getChiefEditor().getEmail());
        log.info("Glavni urednik email changed: " + magazine.getChiefEditor().getEmail());
        runtimeService.setVariable(processInstanceId, "placanje", magazine.getPaymentType());
        log.info("Placenje value changed: " + magazine.getPaymentType());
        runtimeService.setVariable(processInstanceId, "aktivna_clanarina", false);
        log.info("Aktivna_clanarina value changed: " + false);
        formService.submitTaskForm(taskId, formMap);
        log.info("Form submitted");
    }

    public FormFieldsDto getScientificWorkInfoFormFields (String processInstanceId, String username){
        log.info("Get scientific work info form fields with username: {} and processInstanceId: {}", username, processInstanceId);
        Task task = this.taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskDefinitionKey("osnovne_informacije")
                .active()
                .singleResult();

        if (task == null) {
            log.info("No task found");
            throw new NotFoundException();
        }

        if (!task.getAssignee().equals(username)){
            log.info("Task assignee: " + task.getAssignee());
            throw new NotFoundException();
        }

        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> formFields = taskFormData.getFormFields();
        return new FormFieldsDto(task.getId(), formFields, processInstanceId);
    }

    public void saveScientificWorkInfo (List<FormSubmissionDto> formResults, String taskId){
        log.info("Scientific work info save in process variable");
        this.saveFormInProcessVariable(formResults, taskId, "scientific_work_info");
    }

    public FormFieldsDto getCoauthorsFormFields (String processInstanceId, String username){
        log.info("Get coauthors form fields with username: {} and processInstanceId: {}", username, processInstanceId);
        Task task = this.taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskDefinitionKey("koautori")
                .active()
                .singleResult();

        if (task == null) {
            log.info("Task not found");
            throw new NotFoundException();
        }

        if (!task.getAssignee().equals(username)){
            log.info("Task assignee: " + task.getAssignee() + " not equals to username: " + username);
            throw new NotFoundException();
        }

        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> formFields = taskFormData.getFormFields();
        return new FormFieldsDto(task.getId(), formFields, processInstanceId);
    }

    public void saveCoauthor(List<FormSubmissionDto> formResults, String taskId){
        log.info("Coauthor save in process variable");
        this.saveFormInProcessVariable(formResults, taskId, "coauthor");
    }

    public FormFieldsDto getPdfFormFields (String processInstanceId, String username) {
        log.info("Get pdf form fields with username: {} and processInstanceId: {}", username, processInstanceId);
        Task task = this.taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskDefinitionKey("pdf")
                .active()
                .singleResult();

        if (task == null) {
            log.info("Task not found");
            throw new NotFoundException();
        }

        if (!task.getAssignee().equals(username)) {
            log.info("Task assignee: " + task.getAssignee() + " not equals to username: " + username);
            throw new NotFoundException("Assignee exception");
        }

        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> formFields = taskFormData.getFormFields();
        return new FormFieldsDto(task.getId(), formFields, processInstanceId);
    }

    public void savePDF(FileDto fileDto, String taskId){
        log.info("Save PDF");
        MultipartFile multipartFile = fileDto.getFile();
        Path filepath = Paths.get("E:\\FAKULTET\\PDFs", multipartFile.getOriginalFilename());

        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Task task = this.taskService.createTaskQuery()
                .active()
                .taskId(taskId)
                .singleResult();

        if (task == null){
            log.info("Task doesn't exist");
            throw new NotFoundException("Task exception");
        }
        ScientificWork scientificWork = (ScientificWork) this.runtimeService.getVariable(task.getProcessInstanceId(), "scientific_work");
        scientificWork.setFilePath(filepath.toString());
        this.scientificWorkRepository.save(scientificWork);
        HashMap<String, Object> map = new HashMap<>();
        map.put("pdf_fajl", filepath.toString());
        formService.submitTaskForm(taskId, map);
        log.info("Form submitted");
    }

    public List<TaskDto> getCheckScientificWorksTasks(String username) {
        log.info("Get tasks for viewing scientific works");
        return this.taskService.createTaskQuery()
                .taskDefinitionKey("pregled_rada")
                .taskAssignee(username)
                .active()
                .list()
                .stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
    }

    public FormFieldsDto checkScientificWorkFormFields(String taskId, String username){
        log.info("Check scientific work form fields, task id: " + taskId);
        return getFormFields(taskId, username);
    }

    public void checkScientificWork(List<FormSubmissionDto> formResults, String taskId){
        System.out.println("Check scientific work");
        this.saveFormInProcessVariable(formResults, taskId, "some_form");
    }

    public ResponseEntity<?> download(String taskId) throws MalformedURLException {
        Task task = this.taskService.createTaskQuery()
                .taskId(taskId)
                .active()
                .singleResult();

        if (task == null) {
            log.info("Task not found");
            throw new NotFoundException();
        }

        ScientificWork scientificWork = (ScientificWork) this.runtimeService.getVariable(task.getProcessInstanceId(), "scientific_work");

        File file = new File(scientificWork.getFilePath());

        UrlResource urlResource = new UrlResource("file:///" + scientificWork.getFilePath());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        return new ResponseEntity<>(urlResource, httpHeaders, HttpStatus.OK);
    }

    public List<TaskDto> getCheckPdfTasks(String username) {
        log.info("Get tasks for checking pdf");
        return this.taskService.createTaskQuery()
                .taskDefinitionKey("pregled_pdf")
                .taskAssignee(username)
                .active()
                .list()
                .stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
    }

    public FormFieldsDto checkPdfFormFields(String taskId, String username){
        log.info("Check pdf form fields, task id: " + taskId);
        return getFormFields(taskId, username);
    }

    public void checkPdf(List<FormSubmissionDto> formResults, String taskId){
        System.out.println("Check pdf");
        this.saveFormInProcessVariable(formResults, taskId, "pdf_form");
    }

    public FormFieldsDto setTimeFormFields(String processInstanceId, String username){
        log.info("Set time form field fields, processInstanceId: " + processInstanceId);
        Task task = this.taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskDefinitionKey("postavljanje_vremena_za_ispravku")
                .active()
                .singleResult();

        if (task == null) {
            log.info("Task set time not found");
            throw new NotFoundException();
        }

        if (!task.getAssignee().equals(username)) {
            log.info("Task assignee: " + task.getAssignee() + " not equals to username: " + username);
            throw new NotFoundException("Assignee exception");
        }

        List<FormField> formFields = this.formService.getTaskFormData(task.getId()).getFormFields();
        return FormFieldsDto.builder()
                .taskId(task.getId())
                .formFields(formFields)
                .processInstanceId(processInstanceId)
                .build();
    }

    public void setTime(List<FormSubmissionDto> formResults, String taskId){
        System.out.println("Set time");
        this.saveFormInProcessVariable(formResults, taskId, "set_time_form");
    }

    public List<TaskDto> getFormatChangeTasks(String username) {
        log.info("Get tasks for format changing");
        return this.taskService.createTaskQuery()
                .taskDefinitionKey("ispravka_formata")
                .taskAssignee(username)
                .active()
                .list()
                .stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
    }

    public FormFieldsDto formatChangeFormFields(String taskId, String username){
        log.info("Format change form fields, task id: " + taskId);
        return getFormFields(taskId, username);
    }


    public void changeFormat(List<FormSubmissionDto> formResults, String taskId){
        System.out.println("Change format");
        this.saveFormInProcessVariable(formResults, taskId, "change_format_form");
    }


    public List<TaskDto> getSelectReviewersTasks(String username) {
        log.info("Get tasks for reviewers selection");
        return this.taskService.createTaskQuery()
                .taskDefinitionKey("odabir_recenzenata")
                .taskAssignee(username)
                .active()
                .list()
                .stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
    }

    public FormFieldsDto selectReviewersFormFields(String taskId, String username){
        log.info("Select reviewers form fields, task id: " + taskId);
        return getFormFields(taskId, username);
    }


    public void selectReviewers(List<FormSubmissionDto> formResults, String taskId){
        System.out.println("Select reviewers");
        Task task = this.taskService.createTaskQuery()
                .active()
                .taskId(taskId)
                .singleResult();

        HashMap<String, Object> formMap = this.mapListToDto(formResults);

        if (task == null){
            log.info("Task doesn't exist");
            throw new NotFoundException();
        }

        List<String> reviewersString = new ArrayList<>();
        for (Object id: (ArrayList) formMap.get("odabrani_recenzent")){
            User reviewer = this.userService.findOne(Long.parseLong(id.toString()));
            reviewersString.add(reviewer.getUsername());
        }

        this.runtimeService.setVariable(task.getProcessInstanceId(), "selected_reviewers", reviewersString);
        log.info("Selected reviewers: " + reviewersString.toString());
        List<ReviewDto> reviews = new ArrayList<>();
        this.runtimeService.setVariable(task.getProcessInstanceId(), "reviews", reviews);

        formService.submitTaskForm(taskId, formMap);
        log.info("Form submitted");
    }


    public List<TaskDto> getReviewersSetTimeTasks(String username) {
        log.info("Get tasks for reviewers set time");
        return this.taskService.createTaskQuery()
                .taskDefinitionKey("prikaz_odabranih_recenzenata")
                .taskAssignee(username)
                .active()
                .list()
                .stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
    }

    public FormFieldsDto reviewersSetTimeFormFields(String taskId, String username){
        log.info("Reviewer set time form fields, task id: " + taskId);
        return getFormFields(taskId, username);
    }


    public void reviewersSetTime(List<FormSubmissionDto> formResults, String taskId){
        System.out.println("Change format");
        this.saveFormInProcessVariable(formResults, taskId, "set_time_form");
    }


    public List<TaskDto> getReviewTasks(String username) {
        log.info("Get tasks for reviewers set time");
        return this.taskService.createTaskQuery()
                .taskDefinitionKey("recenziranje_rada")
                .taskAssignee(username)
                .active()
                .list()
                .stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
    }

    public FormFieldsDto reviewFormFields(String taskId, String username){
        log.info("Reviewing process form fields, task id: " + taskId);
        return getFormFields(taskId, username);
    }


    public void review(List<FormSubmissionDto> formResults, String taskId, String username){
        System.out.println("Reviewing process");
        HashMap<String, Object> formMap = this.mapListToDto(formResults);

        Task task = this.taskService.createTaskQuery()
                .active()
                .taskId(taskId)
                .singleResult();

        if (task == null){
            log.info("Task doesn't exist");
            throw new NotFoundException();
        }
        List<ReviewDto> reviews = (List<ReviewDto>) this.runtimeService.getVariable(task.getProcessInstanceId(), "reviews");
        ReviewDto reviewDto = ReviewDto.builder()
                .reviewer(username)
                .commentAuthor(formMap.get("komentar_autor").toString())
                .commentEditor(formMap.get("komentar_urednik").toString())
                .recommendation(formMap.get("preporuka").toString())
                .build();

        reviews.add(reviewDto);
        this.runtimeService.setVariable(task.getProcessInstanceId(), "reviews", reviews);
        log.info("Reviews new state: " + reviews.toString());

        formService.submitTaskForm(taskId, formMap);
        log.info("Form submitted");
    }

    public List<TaskDto> getReviewResultsTasks(String username) {
        log.info("Get tasks for reviewers set time");
        return this.taskService.createTaskQuery()
                .taskDefinitionKey("prikaz_rezultata_recenzije")
                .taskAssignee(username)
                .active()
                .list()
                .stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
    }

    public FormFieldsDto reviewResultsFormFields(String taskId, String username){
        log.info("Reviewer results form fields, task id: " + taskId);
        return getFormFields(taskId, username);
    }


    public void reviewResults(List<FormSubmissionDto> formResults, String taskId){
        System.out.println("reviewer Results");
        this.saveFormInProcessVariable(formResults, taskId, "review_form");
    }

    public List<TaskDto> getEditorDecisionTasks(String username) {
        log.info("Get tasks for reviewers set time");
        return this.taskService.createTaskQuery()
                .taskDefinitionKey("urednik_donosi_odluku")
                .taskAssignee(username)
                .active()
                .list()
                .stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
    }

    public FormFieldsDto editorDecisionFormFields(String taskId, String username){
        log.info("Reviewer results form fields, task id: " + taskId);
        return getFormFields(taskId, username);
    }


    public void editorDecision(List<FormSubmissionDto> formResults, String taskId){
        System.out.println("reviewer Results");
        this.saveFormInProcessVariable(formResults, taskId, "editor_decision_form");
    }

    public FormFieldsDto setChangingTimeFormFields(String processInstanceId, String username){
        log.info("Set changing time form field fields, processInstanceId: " + processInstanceId);
        Task task = this.taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskDefinitionKey("postavljanje_vremena_za_izmene_1")
                .active()
                .singleResult();

        if (task == null) {
            log.info("Task set time not found");
            throw new NotFoundException();
        }

        if (!task.getAssignee().equals(username)) {
            log.info("Task assignee: " + task.getAssignee() + " not equals to username: " + username);
            throw new NotFoundException("Assignee exception");
        }

        List<FormField> formFields = this.formService.getTaskFormData(task.getId()).getFormFields();
        return FormFieldsDto.builder()
                .taskId(task.getId())
                .formFields(formFields)
                .processInstanceId(processInstanceId)
                .build();
    }

    public void setChangingTime(List<FormSubmissionDto> formResults, String taskId){
        System.out.println("Set changing time");
        this.saveFormInProcessVariable(formResults, taskId, "set_changing_time_form");
    }

    public List<TaskDto> getReviewerCommentTasks(String username) {
        log.info("Get tasks for reviewers set time");
        return this.taskService.createTaskQuery()
                .taskDefinitionKey("prikaz_komentara")
                .taskAssignee(username)
                .active()
                .list()
                .stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
    }

    public FormFieldsDto reviewerCommentFormFields(String taskId, String username){
        log.info("Reviewer comment form fields, task id: " + taskId);
        return getFormFields(taskId, username);
    }


    public void reviewerComment(List<FormSubmissionDto> formResults, String taskId){
        System.out.println("reviewer comment");
        this.saveFormInProcessVariable(formResults, taskId, "review_comment_form");
    }

    public List<TaskDto> getChangePdfTasks(String username) {
        log.info("Get tasks for reviewers set time");
        return this.taskService.createTaskQuery()
                .taskDefinitionKey("dodvanje_pdf")
                .taskAssignee(username)
                .active()
                .list()
                .stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
    }

    public FormFieldsDto changePdfFormFields(String taskId, String username){
        log.info("Change pdf form fields, task id: " + taskId);
        return getFormFields(taskId, username);
    }

    public List<TaskDto> getViewingWorkTasks(String username) {
        log.info("Get tasks for reviewers set time");
        return this.taskService.createTaskQuery()
                .taskDefinitionKey("pregledanje_rada")
                .taskAssignee(username)
                .active()
                .list()
                .stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
    }

    public FormFieldsDto viewingWorkFormFields(String taskId, String username){
        log.info("Viewing work form fields, task id: " + taskId);
        return getFormFields(taskId, username);
    }


    public void viewingWork(List<FormSubmissionDto> formResults, String taskId){
        System.out.println("Viewing Work");
        this.saveFormInProcessVariable(formResults, taskId, "viewing_form");
    }

    public FormFieldsDto setChangingTimeAgainFormFields(String processInstanceId, String username){
        log.info("Set changing time form field fields, processInstanceId: " + processInstanceId);
        Task task = this.taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskDefinitionKey("postavljanje_vremena_za_izmene_2")
                .active()
                .singleResult();

        if (task == null) {
            log.info("Task set time not found");
            throw new NotFoundException();
        }

        if (!task.getAssignee().equals(username)) {
            log.info("Task assignee: " + task.getAssignee() + " not equals to username: " + username);
            throw new NotFoundException("Assignee exception");
        }

        List<FormField> formFields = this.formService.getTaskFormData(task.getId()).getFormFields();
        return FormFieldsDto.builder()
                .taskId(task.getId())
                .formFields(formFields)
                .processInstanceId(processInstanceId)
                .build();
    }

    public void setChangingTimeAgain(List<FormSubmissionDto> formResults, String taskId){
        System.out.println("Set changing time");
        this.saveFormInProcessVariable(formResults, taskId, "set_changing_time_form");
    }

    public List<TaskDto> getSelectReviewersChangeTasks(String username) {
        log.info("Get tasks for reviewers change");
        return this.taskService.createTaskQuery()
                .taskDefinitionKey("odabir_novog")
                .taskAssignee(username)
                .active()
                .list()
                .stream()
                .map(TaskDto::new)
                .collect(Collectors.toList());
    }

    public FormFieldsDto selectReviewersChangeFormFields(String taskId, String username){
        log.info("Select reviewers change form fields, task id: " + taskId);
        return getFormFields(taskId, username);
    }


    public void selectReviewersChange(List<FormSubmissionDto> formResults, String taskId){
        System.out.println("Select reviewers change");
        Task task = this.taskService.createTaskQuery()
                .active()
                .taskId(taskId)
                .singleResult();

        HashMap<String, Object> formMap = this.mapListToDto(formResults);

        if (task == null){
            log.info("Task doesn't exist");
            throw new NotFoundException();
        }

        List<String> reviewersString = new ArrayList<>();
        for (Object id: (ArrayList) formMap.get("odabrani_recenzent")){
            User reviewer = this.userService.findOne(Long.parseLong(id.toString()));
            reviewersString.add(reviewer.getUsername());
        }

        this.runtimeService.setVariable(task.getProcessInstanceId(), "selected_reviewers", reviewersString);
        log.info("Selected reviewers: " + reviewersString.toString());
        List<ReviewDto> reviews = new ArrayList<>();
        this.runtimeService.setVariable(task.getProcessInstanceId(), "reviews", reviews);

        formService.submitTaskForm(taskId, formMap);
        log.info("Form submitted");
    }

    private FormFieldsDto getFormFields(String taskId, String username) {
        Task task = this.taskService.createTaskQuery()
                .taskId(taskId)
                .active()
                .singleResult();

        if (task == null) {
            log.info("Task check scientific work not found");
            throw new NotFoundException();
        }

        if (!task.getAssignee().equals(username)) {
            log.info("Task assignee: " + task.getAssignee() + " not equals to username: " + username);
            throw new NotFoundException("Assignee exception");
        }

        List<FormField> formFields = this.formService.getTaskFormData(taskId).getFormFields();
        return FormFieldsDto.builder()
                .taskId(taskId)
                .formFields(formFields)
                .processInstanceId(task.getProcessInstanceId())
                .build();
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
    {
        HashMap<String, Object> map = new HashMap<>();
        for(FormSubmissionDto temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }

    private void saveFormInProcessVariable(List<FormSubmissionDto> formResults, String taskId, String variableName){
        Task task = this.taskService.createTaskQuery()
                .active()
                .taskId(taskId)
                .singleResult();

        if (task == null){
            log.info("Task doesn't exist");
            throw new NotFoundException();
        }
        HashMap<String, Object> formMap = this.mapListToDto(formResults);
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, variableName, formMap);
        formService.submitTaskForm(taskId, formMap);
        log.info("Form submitted");
    }
}
