package org.upp.scholar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upp.scholar.model.FileDto;
import org.upp.scholar.model.FormFieldsDto;
import org.upp.scholar.model.FormSubmissionDto;
import org.upp.scholar.model.TaskDto;
import org.upp.scholar.service.ScientificWorkCreationProcessService;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/scientific_work")
public class ScientificWorkController {

    @Autowired
    private ScientificWorkCreationProcessService scientificWorkCreationProcessService;

    @GetMapping(value = "/startProcess/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> startMagazineCreationProcess(@PathVariable String username){
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.startProcess(username), HttpStatus.OK);
    }

    @PostMapping(value = "/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> getScientificWorkMagazine(@RequestBody List<FormSubmissionDto> formResults, @PathVariable String taskId){
        this.scientificWorkCreationProcessService.getScientificWorkMagazine(formResults, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/scientific_work_info/{processInstanceId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto>  getScientificWorkInfoFormFields(@PathVariable String processInstanceId, @PathVariable String username){
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.getScientificWorkInfoFormFields(processInstanceId, username), HttpStatus.OK);
    }

    @PostMapping(value = "/scientific_work_info/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveScientificWorkInfo(@RequestBody List<FormSubmissionDto> formResults, @PathVariable String taskId){
        this.scientificWorkCreationProcessService.saveScientificWorkInfo(formResults, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/coauthors/{processInstanceId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto>  getCoauthorFormFields(@PathVariable String processInstanceId, @PathVariable String username){
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.getCoauthorsFormFields(processInstanceId, username), HttpStatus.OK);
    }

    @PostMapping(value = "/coauthors/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCoauthor(@RequestBody List<FormSubmissionDto> formResults, @PathVariable String taskId){
        this.scientificWorkCreationProcessService.saveCoauthor(formResults, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/pdf/{processInstanceId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto>  getPdfFormFields(@PathVariable String processInstanceId, @PathVariable String username){
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.getPdfFormFields(processInstanceId, username), HttpStatus.OK);
    }

    @PostMapping(value = "/upload/{taskId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadPDF(@ModelAttribute FileDto fileDto, @PathVariable String taskId){
        this.scientificWorkCreationProcessService.savePDF(fileDto, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/scientific_work_tasks/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDto>> getCheckScientificWorksTasks(@PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.getCheckScientificWorksTasks(username), HttpStatus.OK);
    }

    @GetMapping(value = "/scientific_work_tasks/{taskId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> checkScientificWorkFormFields(@PathVariable String taskId, @PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.checkScientificWorkFormFields(taskId, username), HttpStatus.OK);
    }

    @PostMapping(value = "/scientific_work_tasks/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> checkScientificWork(@RequestBody List<FormSubmissionDto> formResults, @PathVariable String taskId){
        this.scientificWorkCreationProcessService.checkScientificWork(formResults, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/download/{taskId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> downloadPDF(@PathVariable String taskId) throws MalformedURLException {
        return this.scientificWorkCreationProcessService.download(taskId);
    }

    @GetMapping(value = "/pdf_tasks/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDto>> getCheckPdfTasks(@PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.getCheckPdfTasks(username), HttpStatus.OK);
    }

    @GetMapping(value = "/pdf_tasks/{taskId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> checkPdfFormFields(@PathVariable String taskId, @PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.checkPdfFormFields(taskId, username), HttpStatus.OK);
    }

    @PostMapping(value = "/pdf_tasks/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> checkPdf(@RequestBody List<FormSubmissionDto> formResults, @PathVariable String taskId){
        this.scientificWorkCreationProcessService.checkPdf(formResults, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/set_time/{processInstanceId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> setTimeFormFields(@PathVariable String processInstanceId, @PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.setTimeFormFields(processInstanceId, username), HttpStatus.OK);
    }

    @PostMapping(value = "/set_time/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> setTime(@RequestBody List<FormSubmissionDto> formResults, @PathVariable String taskId){
        this.scientificWorkCreationProcessService.setTime(formResults, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/change_format/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDto>> getChangeFormatTasks(@PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.getFormatChangeTasks(username), HttpStatus.OK);
    }

    @GetMapping(value = "/change_format/{taskId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> changeFormatFormFields(@PathVariable String taskId, @PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.formatChangeFormFields(taskId, username), HttpStatus.OK);
    }

    @PostMapping(value = "/change_format/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changeFormat(@RequestBody List<FormSubmissionDto> formResults, @PathVariable String taskId){
        this.scientificWorkCreationProcessService.changeFormat(formResults, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/select_reviewer/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDto>> getSelectReviewerTasks(@PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.getSelectReviewersTasks(username), HttpStatus.OK);
    }

    @GetMapping(value = "/select_reviewer/{taskId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> selectReviewerFormFields(@PathVariable String taskId, @PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.selectReviewersFormFields(taskId, username), HttpStatus.OK);
    }

    @PostMapping(value = "/select_reviewer/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> selectReviewer(@RequestBody List<FormSubmissionDto> formResults, @PathVariable String taskId){
        this.scientificWorkCreationProcessService.selectReviewers(formResults, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/reviewer_set_time/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDto>> getReviewerSetTimeTasks(@PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.getReviewersSetTimeTasks(username), HttpStatus.OK);
    }

    @GetMapping(value = "/reviewer_set_time/{taskId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> reviewerSetTimeFormFields(@PathVariable String taskId, @PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.reviewersSetTimeFormFields(taskId, username), HttpStatus.OK);
    }

    @PostMapping(value = "/reviewer_set_time/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> reviewerSetTime(@RequestBody List<FormSubmissionDto> formResults, @PathVariable String taskId){
        this.scientificWorkCreationProcessService.reviewersSetTime(formResults, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/review/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDto>> getReviewTasks(@PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.getReviewTasks(username), HttpStatus.OK);
    }

    @GetMapping(value = "/review/{taskId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> reviewFormFields(@PathVariable String taskId, @PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.reviewFormFields(taskId, username), HttpStatus.OK);
    }

    @PostMapping(value = "/review/{taskId}/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> review(@RequestBody List<FormSubmissionDto> formResults, @PathVariable String taskId, @PathVariable String username){
        this.scientificWorkCreationProcessService.review(formResults, taskId, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/review_results/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDto>> getReviewResultsTasks(@PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.getReviewResultsTasks(username), HttpStatus.OK);
    }

    @GetMapping(value = "/review_results/{taskId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> reviewResultsFormFields(@PathVariable String taskId, @PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.reviewResultsFormFields(taskId, username), HttpStatus.OK);
    }

    @PostMapping(value = "/review_results/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> reviewResults(@RequestBody List<FormSubmissionDto> formResults, @PathVariable String taskId){
        this.scientificWorkCreationProcessService.reviewResults(formResults, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/editor_decision/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDto>> getEditorDecisionTasks(@PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.getEditorDecisionTasks(username), HttpStatus.OK);
    }

    @GetMapping(value = "/editor_decision/{taskId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> editorDecisionFormFields(@PathVariable String taskId, @PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.editorDecisionFormFields(taskId, username), HttpStatus.OK);
    }

    @PostMapping(value = "/editor_decision/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> editorDecision(@RequestBody List<FormSubmissionDto> formResults, @PathVariable String taskId){
        this.scientificWorkCreationProcessService.editorDecision(formResults, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/set_changing_time/{processInstanceId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> changingTimeFormFields(@PathVariable String processInstanceId, @PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.setChangingTimeFormFields(processInstanceId, username), HttpStatus.OK);
    }

    @PostMapping(value = "/set_changing_time/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> setChangingTime(@RequestBody List<FormSubmissionDto> formResults, @PathVariable String taskId){
        this.scientificWorkCreationProcessService.setChangingTime(formResults, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/reviewer_comment/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDto>> getReviewerCommentTasks(@PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.getReviewerCommentTasks(username), HttpStatus.OK);
    }

    @GetMapping(value = "/reviewer_comment/{taskId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> reviewerCommentFormFields(@PathVariable String taskId, @PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.reviewerCommentFormFields(taskId, username), HttpStatus.OK);
    }

    @PostMapping(value = "/reviewer_comment/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> reviewerComment(@RequestBody List<FormSubmissionDto> formResults, @PathVariable String taskId){
        this.scientificWorkCreationProcessService.reviewerComment(formResults, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/change_pdf/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDto>> getChangePdfTasks(@PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.getChangePdfTasks(username), HttpStatus.OK);
    }

    @GetMapping(value = "/change_pdf/{taskId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> changePdfFormFields(@PathVariable String taskId, @PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.changePdfFormFields(taskId, username), HttpStatus.OK);
    }

    @GetMapping(value = "/viewing_work/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDto>> getViewingWorkTasks(@PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.getViewingWorkTasks(username), HttpStatus.OK);
    }

    @GetMapping(value = "/viewing_work/{taskId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> viewingWorkFormFields(@PathVariable String taskId, @PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.viewingWorkFormFields(taskId, username), HttpStatus.OK);
    }

    @PostMapping(value = "/viewing_work/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> viewingWork(@RequestBody List<FormSubmissionDto> formResults, @PathVariable String taskId){
        this.scientificWorkCreationProcessService.viewingWork(formResults, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/set_changing_time_again/{processInstanceId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> changingTimeAgainFormFields(@PathVariable String processInstanceId, @PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.setChangingTimeAgainFormFields(processInstanceId, username), HttpStatus.OK);
    }

    @PostMapping(value = "/set_changing_time_again/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> setChangingTimeAgain(@RequestBody List<FormSubmissionDto> formResults, @PathVariable String taskId){
        this.scientificWorkCreationProcessService.setChangingTimeAgain(formResults, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/new_reviewers/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDto>> getNewReviewersTasks(@PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.getSelectReviewersChangeTasks(username), HttpStatus.OK);
    }

    @GetMapping(value = "/new_reviewers/{taskId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> newReviewersFormFields(@PathVariable String taskId, @PathVariable String username) {
        return new ResponseEntity<>(this.scientificWorkCreationProcessService.selectReviewersChangeFormFields(taskId, username), HttpStatus.OK);
    }

    @PostMapping(value = "/new_reviewers/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> newReviewers(@RequestBody List<FormSubmissionDto> formResults, @PathVariable String taskId){
        this.scientificWorkCreationProcessService.selectReviewersChange(formResults, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
