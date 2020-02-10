package org.upp.scholar.handler;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upp.scholar.entity.Magazine;
import org.upp.scholar.entity.MagazineStatus;
import org.upp.scholar.repository.MagazineRepository;

import java.util.List;

@Service
public class MagazineHandler implements TaskListener {

    @Autowired
    private MagazineRepository magazineRepository;

    @Autowired
    private FormService formService;

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("Dodavanje magazina");

        List<Magazine> magazines = this.magazineRepository.findByMagazineStatus(MagazineStatus.ACTIVE);

        TaskFormData taskFormData = formService.getTaskFormData(delegateTask.getId());
        List<FormField> formFields = taskFormData.getFormFields();
        for (FormField formField : formFields){
            if (formField.getId().equals("casopis")){
                EnumFormType enumFormType = (EnumFormType) formField.getType();

                for (Magazine magazine: magazines){
                    enumFormType.getValues().put(magazine.getId() + "" , magazine.getName() + " (issn: " + magazine.getIssn() + ")");
                }
            }
        }
    }
}
