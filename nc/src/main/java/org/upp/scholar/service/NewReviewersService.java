package org.upp.scholar.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upp.scholar.entity.Magazine;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
@Slf4j
public class NewReviewersService implements JavaDelegate {

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<String> reviewersUsername = (List<String>) delegateExecution.getVariable("selected_reviewers");
        if (reviewersUsername == null || reviewersUsername.isEmpty()){
            throw new NotFoundException("Reviewers not found in process variable");
        }

        Magazine magazine = (Magazine) delegateExecution.getVariable("scientificWorkMagazine");
        if (magazine == null) {
            throw new NotFoundException("Magazine not found in process variable");
        }

        if (magazine.getReviewers().size() > reviewersUsername.size()){
            this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "new_reviewers_exist", true);
        } else {
            this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "new_reviewers_exist", false);
        }
    }
}
