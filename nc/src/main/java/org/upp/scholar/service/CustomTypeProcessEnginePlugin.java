package org.upp.scholar.service;

import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.springframework.stereotype.Service;
import org.upp.scholar.model.MultipleEnumFormType;
import org.upp.scholar.model.PasswordFormType;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomTypeProcessEnginePlugin extends AbstractProcessEnginePlugin {

    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        if (processEngineConfiguration.getCustomFormTypes() == null) {
            processEngineConfiguration.setCustomFormTypes(new ArrayList<AbstractFormFieldType>());
        }

        List<AbstractFormFieldType> formTypes = processEngineConfiguration.getCustomFormTypes();
        formTypes.add(new PasswordFormType());
        formTypes.add(new MultipleEnumFormType("naucne_oblasti"));
        formTypes.add(new MultipleEnumFormType("editori"));
        formTypes.add(new MultipleEnumFormType("recenzenti"));
    }
}
