package org.upp.scholar.model;

import org.camunda.bpm.engine.impl.form.type.StringFormType;

public class PasswordFormType extends StringFormType {
    public String getName() { return "password"; }
}
