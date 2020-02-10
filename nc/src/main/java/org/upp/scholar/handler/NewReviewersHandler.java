package org.upp.scholar.handler;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upp.scholar.entity.Magazine;
import org.upp.scholar.entity.ScientificArea;
import org.upp.scholar.entity.User;
import org.upp.scholar.model.MultipleEnumFormType;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewReviewersHandler implements TaskListener {

    @Autowired
    private FormService formService;

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("Dodavanje novih recenzenata u enum");
        Magazine magazine = (Magazine) delegateTask.getVariable("scientificWorkMagazine");
        if (magazine == null) {
            throw new NotFoundException("Magazine not found in system variable");
        }
        List<String> oldReviewers  = (List<String>) delegateTask.getVariable("selected_reviewers");
        if (oldReviewers == null) {
            throw new NotFoundException("Selected reviewers not found in system variable");
        }

        List<User> reviewers = magazine.getReviewers();
        List<User> newReviewers = new ArrayList<>();
        for (User reviewer: reviewers) {
            if (!oldReviewers.contains(reviewer.getUsername())) {
                newReviewers.add(reviewer);
            }
        }

        TaskFormData taskFormData = formService.getTaskFormData(delegateTask.getId());
        List<FormField> formFields = taskFormData.getFormFields();
        for (FormField formField : formFields){
            if (formField.getId().equals("odabrani_recenzent")) {
                MultipleEnumFormType multipleEnumFormType = (MultipleEnumFormType) formField.getType();

                for(User reviewer: newReviewers){
                    List<String> scientificAreas = new ArrayList<>();
                    for (ScientificArea scientificArea : reviewer.getScientificAreas()){
                        scientificAreas.add(scientificArea.getName());
                    }
                    multipleEnumFormType.getValues().put(reviewer.getId().toString(), reviewer.getId() + ". " + reviewer.getUsername() + ' ' + scientificAreas.toString());
                }
            }
        }
    }
}
