package org.upp.scholar.handler;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormType;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upp.scholar.entity.Magazine;
import org.upp.scholar.entity.ScientificArea;
import org.upp.scholar.model.MultipleEnumFormType;
import org.upp.scholar.repository.ScientificAreaRepository;

import java.util.List;

@Service
public class ScientificAreaHandler implements TaskListener {

    @Autowired
    private FormService formService;

    @Autowired
    private ScientificAreaRepository scientificAreaRepository;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("Dodavanje vrednosti enuma");
        List<ScientificArea> scientificAreas = scientificAreaRepository.findAll();
        TaskFormData taskFormData = formService.getTaskFormData(delegateTask.getId());
        List<FormField> formFields = taskFormData.getFormFields();
        for (FormField formField : formFields){
            if (formField.getId().equals("naucne_oblasti")){
               MultipleEnumFormType multipleEnumFormType = (MultipleEnumFormType) formField.getType();

               for(ScientificArea scientificArea: scientificAreas){
                   multipleEnumFormType.getValues().put(scientificArea.getId().toString(), scientificArea.getName());
               }
            }else if (formField.getId().equals("naucna_oblast")){
                Magazine magazine = (Magazine) runtimeService.getVariable(delegateTask.getProcessInstanceId() ,"scientificWorkMagazine");
                List<ScientificArea> magazineScientificAreas = magazine.getScientificAreas();
                EnumFormType enumFormType = (EnumFormType) formField.getType();

                for(ScientificArea scientificArea: magazineScientificAreas){
                    enumFormType.getValues().put(scientificArea.getId().toString(), scientificArea.getName());
                }
            }
        }
    }
}
