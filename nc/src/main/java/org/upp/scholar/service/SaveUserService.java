package org.upp.scholar.service;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.upp.scholar.entity.ScientificArea;
import org.upp.scholar.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SaveUserService implements JavaDelegate {

    @Autowired
    private IdentityService identityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScientificAreaService scientificAreaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        //save camunda user
        HashMap<String, Object> registrationForm = (HashMap<String, Object>) delegateExecution.getVariable("registration");
        System.out.println(registrationForm);
        User user = identityService.newUser(registrationForm.get("korisnicko_ime").toString());
        user.setPassword(registrationForm.get("sifra").toString());
        user.setFirstName(registrationForm.get("ime").toString());
        user.setLastName(registrationForm.get("prezime").toString());
        user.setEmail(registrationForm.get("email").toString());
        identityService.saveUser(user);

        org.upp.scholar.entity.User newUser = org.upp.scholar.entity.User.builder()
                    .username(registrationForm.get("korisnicko_ime").toString())
                    .password(this.passwordEncoder.encode(registrationForm.get("sifra").toString()))
                    .firstName(registrationForm.get("ime").toString())
                    .lastName(registrationForm.get("prezime").toString())
                    .email(registrationForm.get("email").toString())
                    .city(registrationForm.get("grad").toString())
                    .country(registrationForm.get("drzava").toString())
                    .title(registrationForm.get("titula").toString())
                    .enabled(false)
                    .enabledReviewer(false)
                    .reviewer(false)
                    .build();

        if (registrationForm.get("recenzent") != null){
            if (registrationForm.get("recenzent").toString() != ""){
                newUser.setReviewer(Boolean.parseBoolean(registrationForm.get("recenzent").toString()));
            }
        }
        List<String> stringScientificAreas = new ArrayList<>();
        List<ScientificArea> scientificAreas = new ArrayList<>();
        for (Object value: (ArrayList) registrationForm.get("naucne_oblasti")){
            ScientificArea scientificArea = this.scientificAreaService.findOne(Long.parseLong(value.toString()));
            scientificAreas.add(scientificArea);
            stringScientificAreas.add(scientificArea.getName());
        }
        runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "naucne_oblasti", stringScientificAreas.toString());
        newUser.setScientificAreas(scientificAreas);

        this.userRepository.save(newUser);
    }
}
