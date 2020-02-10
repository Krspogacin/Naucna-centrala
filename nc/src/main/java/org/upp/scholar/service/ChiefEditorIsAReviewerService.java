package org.upp.scholar.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ChiefEditorIsAReviewerService implements JavaDelegate {

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String chiefEditor = (String) delegateExecution.getVariable("glavni_urednik");
        if (chiefEditor == null) {
            throw new NotFoundException("Chief editor not found in process variable");
        }

        List<String> reviewers = new ArrayList<>();
        reviewers.add(chiefEditor);
        this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "selected_reviewers", reviewers);
    }
}
