package org.upp.scholar.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upp.scholar.entity.ScientificWork;
import org.upp.scholar.repository.ScientificWorkRepository;

import javax.ws.rs.NotFoundException;

@Service
@Slf4j
public class EnableScientificWorkService implements JavaDelegate {

    @Autowired
    private ScientificWorkRepository scientificWorkRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ScientificWork scientificWork = (ScientificWork) delegateExecution.getVariable("scientific_work");
        if (scientificWork == null) {
            throw new NotFoundException("Scientific work not found in process variable");
        }
        scientificWork.setEnabled(true);
        this.scientificWorkRepository.save(scientificWork);
    }
}
