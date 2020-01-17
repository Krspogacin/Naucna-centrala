package org.upp.scholar.service;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upp.scholar.entity.Magazine;
import org.upp.scholar.entity.User;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SaveEditorsAndReviewersService implements JavaDelegate {

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private UserService userService;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Save editors and reviewers task enter");
        HashMap<String, Object> editorsAndReviewersForm = (HashMap<String, Object>) delegateExecution.getVariable("editors_and_reviewers");
        String issn = (String) delegateExecution.getVariable("magazine_issn");

        Magazine magazine = this.magazineService.findByIssn(issn);
        if (magazine == null){
            throw new NotFoundException();
        }

        List<User> editors = new ArrayList<>();
        List<String> editorsString = new ArrayList<>();
        for (Object id: (ArrayList) editorsAndReviewersForm.get("editori")){
            User editor = this.userService.findOne(Long.parseLong(id.toString()));
            editors.add(editor);
            editorsString.add(editor.getUsername());
        }

        List<User> reviewers = new ArrayList<>();
        List<String> reviewersString = new ArrayList<>();
        for (Object id: (ArrayList) editorsAndReviewersForm.get("recenzenti")){
            User reviewer = this.userService.findOne(Long.parseLong(id.toString()));
            reviewers.add(reviewer);
            reviewersString.add(reviewer.getUsername());
        }
        magazine.setEditors(editors);
        magazine.setReviewers(reviewers);
        this.magazineService.save(magazine);
        System.out.println("Editors and reviewers saved");

        this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "editori_string", editorsString.toString());
        this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "recenzenti_string", reviewersString.toString());
    }
}
