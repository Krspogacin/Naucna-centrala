package org.upp.scholar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upp.scholar.model.CheckReviewerDto;
import org.upp.scholar.model.FormFieldsDto;
import org.upp.scholar.model.FormSubmissionDto;
import org.upp.scholar.model.TaskDto;
import org.upp.scholar.service.RegistrationService;
import org.upp.scholar.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/startProcess", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> startProcess() {
        return new ResponseEntity<>(this.registrationService.startRegistrationProcess(), HttpStatus.OK);
    }


    @PostMapping(value = "/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registration(@RequestBody List<FormSubmissionDto> formResults, @PathVariable String taskId){
        this.registrationService.registrateUser(formResults, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/verify/{processInstanceId}/{username}")
    public ResponseEntity<Void> verify(@PathVariable String username, @PathVariable String processInstanceId){
        this.userService.verifyUser(processInstanceId, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/check_reviewer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDto>> getActiveCheckReviewerTasks() {
        return new ResponseEntity<>(this.userService.getActiveCheckReviewerTasks(), HttpStatus.OK);
    }

    @GetMapping(value = "/check_reviewer/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> getCheckReviewerFormFields(@PathVariable String taskId) {
        return new ResponseEntity<>(this.userService.getCheckReviewerFormFields(taskId), HttpStatus.OK);
    }

    @PostMapping(value = "/check_reviewer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> checkReviewer(@RequestBody CheckReviewerDto checkReviewerDto) {
        this.userService.checkReviewer(checkReviewerDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
