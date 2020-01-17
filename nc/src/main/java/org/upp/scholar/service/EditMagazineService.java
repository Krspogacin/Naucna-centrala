package org.upp.scholar.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upp.scholar.entity.Magazine;
import org.upp.scholar.entity.MagazineStatus;

import javax.ws.rs.NotFoundException;

@Service
public class EditMagazineService implements JavaDelegate {

    @Autowired
    private MagazineService magazineService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Enter edit service");
        String issn = (String) delegateExecution.getVariable("issn");
        System.out.println("issn:" + issn);
        Magazine magazine = this.magazineService.findByIssn(issn);
        if (magazine == null || issn == null){
            throw new NotFoundException();
        }
        magazine.setMagazineStatus(MagazineStatus.EDIT);
        this.magazineService.save(magazine);
        System.out.println("Casopis je neophodno izmeniti");
    }
}
