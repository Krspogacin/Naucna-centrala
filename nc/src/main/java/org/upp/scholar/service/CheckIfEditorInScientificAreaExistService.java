package org.upp.scholar.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upp.scholar.entity.Magazine;
import org.upp.scholar.entity.ScientificArea;
import org.upp.scholar.entity.User;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
@Slf4j
public class CheckIfEditorInScientificAreaExistService implements JavaDelegate {

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("Check if editor for scientific area exist");
        Magazine magazine = (Magazine) delegateExecution.getVariable("scientificWorkMagazine");
        if (magazine == null) {
            throw new NotFoundException("Scientific work magazine not found in process variable");
        }
        String chiefEditor = (String) delegateExecution.getVariable("glavni_urednik");
        if (chiefEditor == null) {
            throw new NotFoundException("Chief editor not found in process variable");
        }
        String scientificArea = (String) delegateExecution.getVariable("naucna_oblast");
        if (scientificArea == null){
            throw new NotFoundException("Scientific area not found in process variable");
        }

        List<User> reviewers = magazine.getReviewers();
        if (reviewers == null || reviewers.isEmpty()) {
            log.info("Reviewers don't exist, chief editor is a reviewer");
            this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "reviewers_exist", false);
        } else {
            log.info("Reviewers exists");
            this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "reviewers_exist", true);
        }


        List<User> editors = magazine.getEditors();
        if (editors == null || editors.isEmpty()){
            log.info("Editors don't exist, chief editor choose reviewers");
            this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "editor_who_selects_reviewers", chiefEditor);
        } else {
            for (User editor: editors) {
                for (ScientificArea editorArea: editor.getScientificAreas()){
                    if (scientificArea.equals(editorArea.getName())){
                        log.info("Editor exist");
                        this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "editor_who_selects_reviewers", editor.getUsername());
                        return;
                    }
                }
            }
            log.info("Editors don't exist, chief editor choose reviewers");
            this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "editor_who_selects_reviewers", chiefEditor);
        }
    }
}
