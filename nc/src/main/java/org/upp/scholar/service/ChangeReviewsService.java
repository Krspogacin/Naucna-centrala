package org.upp.scholar.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upp.scholar.model.ReviewDto;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
@Slf4j
public class ChangeReviewsService implements JavaDelegate {

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("Change reviews and update reviewers service");
        List<String> reviewers  = (List<String>) delegateExecution.getVariable("selected_reviewers");
        List<ReviewDto> reviews = (List<ReviewDto>) delegateExecution.getVariable("reviews");
        if (reviewers == null || reviews == null){
            throw new NotFoundException("Reviewers or reviews not found in process variable");
        }
        reviewers.clear();
        for (ReviewDto review: reviews){
            reviewers.add(review.getReviewer());
        }
        reviews.clear();
        this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "selected_reviewers", reviewers);
        this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "reviews", reviews);
    }
}
