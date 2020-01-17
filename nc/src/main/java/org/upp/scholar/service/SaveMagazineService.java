package org.upp.scholar.service;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upp.scholar.entity.*;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SaveMagazineService implements JavaDelegate {

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScientificAreaService scientificAreaService;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Enter save magazine service");
        HashMap<String, Object> magazineForm = (HashMap<String, Object>) delegateExecution.getVariable("newMagazine");
        System.out.println(magazineForm);

        Magazine magazine = this.magazineService.findByIssn(magazineForm.get("issn").toString());
        User editor = this.userService.findByUsername(delegateExecution.getVariable("pokretac").toString());
        System.out.println("EDITOR: " + editor);
        if (editor == null){
            throw new NotFoundException();
        }
        if (magazine == null){
            Magazine newMagazine = Magazine.builder()
                    .issn(magazineForm.get("issn").toString())
                    .chiefEditor(editor)
                    .magazineStatus(MagazineStatus.NEW)
                    .name(magazineForm.get("naziv").toString())
                    .build();

            if (magazineForm.get("nacin_placanja_clanarine").toString().equals("USER")){
                newMagazine.setPaymentType(PaymentType.USER);
            }else{
                newMagazine.setPaymentType(PaymentType.AUTHOR);
            }

            List<String> stringScientificAreas = new ArrayList<>();
            List<ScientificArea> scientificAreas = new ArrayList<>();
            for (Object value: (ArrayList) magazineForm.get("naucne_oblasti")){
                ScientificArea scientificArea = this.scientificAreaService.findOne(Long.parseLong(value.toString()));
                scientificAreas.add(scientificArea);
                stringScientificAreas.add(scientificArea.getName());
            }
            runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "naucne_oblasti_string", stringScientificAreas.toString());
            newMagazine.setScientificAreas(scientificAreas);
            this.magazineService.save(newMagazine);
            System.out.println("Magazin saved");
        }else if (magazine.getMagazineStatus() == MagazineStatus.EDIT){
            magazine.setIssn(magazineForm.get("issn").toString());
            if (magazineForm.get("nacin_placanja_clanarine").toString().equals("USER")){
                magazine.setPaymentType(PaymentType.USER);
            }else{
                magazine.setPaymentType(PaymentType.AUTHOR);
            }
            magazine.setName(magazineForm.get("naziv").toString());
            magazine.setMagazineStatus(MagazineStatus.NEW);

            List<String> stringScientificAreas = new ArrayList<>();
            List<ScientificArea> scientificAreas = new ArrayList<>();
            for (Object value: (ArrayList) magazineForm.get("naucne_oblasti")){
                ScientificArea scientificArea = this.scientificAreaService.findOne(Long.parseLong(value.toString()));
                scientificAreas.add(scientificArea);
                stringScientificAreas.add(scientificArea.getName());
            }
            runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "naucne_oblasti_string", stringScientificAreas.toString());
            magazine.setScientificAreas(scientificAreas);

        }else{
            throw new NotFoundException();
        }
    }
}
