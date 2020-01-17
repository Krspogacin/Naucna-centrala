package org.upp.scholar.handler;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upp.scholar.entity.Authority;
import org.upp.scholar.entity.Magazine;
import org.upp.scholar.entity.ScientificArea;
import org.upp.scholar.entity.User;
import org.upp.scholar.model.MultipleEnumFormType;
import org.upp.scholar.service.AuthorityService;
import org.upp.scholar.service.MagazineService;
import org.upp.scholar.service.UserService;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EditorsAndReviewersHandler implements TaskListener {

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private UserService userService;

    @Autowired
    private FormService formService;

    @Autowired
    private AuthorityService authorityService;

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("Dodavanje editora i recenzenta");
        Magazine magazine = this.magazineService.findByIssn(delegateTask.getExecution().getVariable("issn").toString());
        if (magazine == null){
            throw new NotFoundException();
        }
        Authority authorityEditor = this.authorityService.findByName("ROLE_EDITOR");
        Authority authorityReviewer = this.authorityService.findByName("ROLE_REVIEWER");


        List<User> editors = this.userService.findEditorAndReviewers(magazine.getChiefEditor().getUsername(), authorityEditor, magazine.getScientificAreas());
        System.out.println("Editori: " + editors);
        List<User> reviewers = this.userService.findEditorAndReviewers(magazine.getChiefEditor().getUsername(), authorityReviewer, magazine.getScientificAreas());
        System.out.println("Recenzenti: " + reviewers);

        TaskFormData taskFormData = formService.getTaskFormData(delegateTask.getId());
        List<FormField> formFields = taskFormData.getFormFields();
        for (FormField formField : formFields){
            if (formField.getId().equals("editori")){
                MultipleEnumFormType multipleEnumFormType = (MultipleEnumFormType) formField.getType();

                for(User editor : editors){
                    List<String> scientificAreas = new ArrayList<>();
                    for (ScientificArea scientificArea : editor.getScientificAreas()){
                        scientificAreas.add(scientificArea.getName());
                    }
                    multipleEnumFormType.getValues().put(editor.getId().toString(), editor.getId() + ". " + editor.getUsername() + ' ' + scientificAreas.toString());
                }
            }else if(formField.getId().equals("recenzenti")){
                MultipleEnumFormType multipleEnumFormType = (MultipleEnumFormType) formField.getType();

                for(User reviewer: reviewers){
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
