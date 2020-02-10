package org.upp.scholar.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upp.scholar.entity.Coauthor;
import org.upp.scholar.entity.ScientificWork;
import org.upp.scholar.repository.CoauthorRepository;

import java.util.HashMap;

@Service
public class SaveCoauthorService implements JavaDelegate {

    @Autowired
    private CoauthorRepository coauthorRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ScientificWork scientificWork = (ScientificWork) delegateExecution.getVariable("scientific_work");
        HashMap<String, Object> coauthorForm = (HashMap<String, Object>) delegateExecution.getVariable("coauthor");
        Coauthor coauthor = Coauthor.builder()
                .name(coauthorForm.get("ime").toString())
                .email(coauthorForm.get("email").toString())
                .city(coauthorForm.get("grad").toString())
                .country(coauthorForm.get("drzava").toString())
                .scientificWork(scientificWork)
                .build();

        this.coauthorRepository.save(coauthor);
        System.out.println("Coauthor saved");
    }
}
