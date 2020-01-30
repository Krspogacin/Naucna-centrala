package org.upp.scholar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upp.scholar.entity.Edition;
import org.upp.scholar.entity.Magazine;
import org.upp.scholar.model.*;
import org.upp.scholar.service.MagazineCreationProcessService;
import org.upp.scholar.service.MagazineService;

import javax.ws.rs.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/magazine")
public class MagazineController {

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private MagazineCreationProcessService magazineCreationProcessService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MagazineDto> getMagazine(@PathVariable Long id){
        Magazine magazine = this.magazineService.findById(id);
        return new ResponseEntity<>(new MagazineDto(magazine), HttpStatus.OK);
    }

    @GetMapping(value = "/editor/{editor}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MagazineDto>> getMagazines(@PathVariable String editor){
        return new ResponseEntity<>(this.magazineService.findMagazinesByEditor(editor), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MagazineDto>> getActiveMagazines(){
        return new ResponseEntity<>(this.magazineService.findActiveMagazines() , HttpStatus.OK);
    }

    @GetMapping(value= "/editions/{magazineId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EditionDto>> getEditions(@PathVariable Long magazineId, @PathVariable String username) {
        return new ResponseEntity<>(this.magazineService.findMagazineEditions(magazineId, username), HttpStatus.OK);
    }

    @GetMapping(value= "/edition/{editionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EditionDto> getEdition(@PathVariable Long editionId){
        return new ResponseEntity<>(new EditionDto(this.magazineService.findEditionById(editionId)), HttpStatus.OK);
    }

    @GetMapping(value= "/works/{editionId}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScientificWorkDto>> getScientificWorks(@PathVariable Long editionId, @PathVariable String username){
        return new ResponseEntity<>(this.magazineService.getEdtionScientificWorks(editionId, username), HttpStatus.OK);
    }

    @PostMapping(value= "/save_edition/{magazineId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EditionDto> saveEdition(@PathVariable Long magazineId, @RequestBody Edition edition){
        Magazine magazine = this.magazineService.findById(magazineId);
        edition.setCreationDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy")));
        edition.setMagazine(magazine);
        return new ResponseEntity<>(this.magazineService.saveEdition(edition), HttpStatus.OK);
    }

    @GetMapping(value = "/startProcess/{editor}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> startMagazineCreationProcess(@PathVariable String editor){
        return new ResponseEntity<>(this.magazineCreationProcessService.startMagazineCreationProcess(editor), HttpStatus.OK);
    }

    @PostMapping(value = "/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveMagazine(@RequestBody List<FormSubmissionDto> formResults, @PathVariable String taskId){
        this.magazineCreationProcessService.saveMagazine(formResults, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/editors_and_reviewers_form_fields/{processInstanceId}/{editor}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> getEditorAndReviewersFormFields(@PathVariable String processInstanceId, @PathVariable String editor){
        return new ResponseEntity<>(this.magazineCreationProcessService.getEditorAndReviewerFormField(processInstanceId, editor), HttpStatus.OK);
    }

    @PostMapping(value = "/save_editors_and_reviewers/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveEditorsAndReviewers(@RequestBody EditorsAndReviewersDto editorsAndReviewersDto, @PathVariable String taskId){
        this.magazineCreationProcessService.saveEditorsAndReviewers(editorsAndReviewersDto, taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/check_magazine", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDto>> getActiveCheckMagazineTasks() {
        return new ResponseEntity<>(this.magazineCreationProcessService.getActiveCheckMagazineTasks(), HttpStatus.OK);
    }

    @GetMapping(value = "/check_magazine/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> getCheckMagazineFormFields(@PathVariable String taskId) {
        return new ResponseEntity<>(this.magazineCreationProcessService.getCheckMagazineFormFields(taskId), HttpStatus.OK);
    }

    @PostMapping(value = "/check_magazine", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> checkMagazine(@RequestBody CheckReviewerDto checkReviewerDto) {
        this.magazineCreationProcessService.checkMagazine(checkReviewerDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/edited/{processInstanceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormFieldsDto> getEditedFormFields(@PathVariable String processInstanceId){
        return new ResponseEntity<>(this.magazineCreationProcessService.getEditedFormFields(processInstanceId), HttpStatus.OK);
    }

}
