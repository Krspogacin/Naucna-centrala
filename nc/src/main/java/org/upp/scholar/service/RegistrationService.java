package org.upp.scholar.service;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upp.scholar.model.FormFieldsDto;
import org.upp.scholar.model.FormSubmissionDto;

import java.util.HashMap;
import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    public FormFieldsDto startRegistrationProcess(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("registracija");
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> formFields = taskFormData.getFormFields();
        for (FormField formField : formFields){
            System.out.println("Id: " + formField.getId() + " Type: " + formField.getType());
        }
        return new FormFieldsDto(task.getId(), formFields, processInstance.getId());
    }

    public void registrateUser(List<FormSubmissionDto> formResults,String taskId){
        HashMap<String, Object> formMap = this.mapListToDto(formResults);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "registration", formMap);
        formService.submitTaskForm(taskId, formMap);
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDto temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }
}
