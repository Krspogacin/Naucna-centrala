package org.upp.scholar.service;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upp.scholar.entity.Edition;
import org.upp.scholar.entity.Magazine;
import org.upp.scholar.entity.ScientificWork;
import org.upp.scholar.entity.User;
import org.upp.scholar.repository.ScientificWorkRepository;

import javax.ws.rs.NotFoundException;
import java.util.HashMap;

@Service
public class SaveScientificWorkService implements JavaDelegate {

    @Autowired
    private UserService userService;

    @Autowired
    private ScientificAreaService scientificAreaService;

    @Autowired
    private ScientificWorkRepository scientificWorkRepository;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Enter save scientific work service");
        HashMap<String, Object> magazineForm = (HashMap<String, Object>) delegateExecution.getVariable("scientific_work_info");
        Magazine magazine = (Magazine) delegateExecution.getVariable("scientificWorkMagazine");
        Edition edition = magazine.getEditions().get(0);
        if (edition == null){
            throw new NotFoundException("Magazine don't have editions");
        }
        User author = this.userService.findByUsername(delegateExecution.getVariable("autor").toString());
        if (author == null){
            throw new NotFoundException("Author with username: " + delegateExecution.getVariable("autor").toString() + " not found");
        }

        ScientificWork scientificWork = ScientificWork.builder()
                .description("Opis rada")
                .edition(edition)
                .enabled(false)
                .keyTerms(magazineForm.get("kljucni_pojmovi").toString())
                .title(magazineForm.get("naslov").toString())
                .scientificArea(this.scientificAreaService.findOne(Long.parseLong(magazineForm.get("naucna_oblast").toString())))
                .workAbstract(magazineForm.get("apstrakt").toString())
                .author(author)
                .price(20.5)
                .build();

        this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "naucna_oblast", this.scientificAreaService.findOne(Long.parseLong(magazineForm.get("naucna_oblast").toString())).getName());
        this.scientificWorkRepository.save(scientificWork);
        this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "scientific_work", scientificWork);
        System.out.println("Scientific work saved");
    }
}
