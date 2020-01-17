package org.upp.scholar.service;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upp.scholar.entity.Authority;
import org.upp.scholar.entity.User;
import org.upp.scholar.repository.UserRepository;

import javax.ws.rs.NotFoundException;

@Service
public class UpdateReviewer implements JavaDelegate {

    @Autowired
    private IdentityService identityService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Final service task enter");
        String username = (String) delegateExecution.getVariable("korisnicko_ime");
        Boolean flag = (Boolean) delegateExecution.getVariable("reviewerFlag");
        System.out.println("korisnicko ime:" + username + " flag:" + flag);
        User user = (User) this.userService.loadUserByUsername(username);
        if (user == null || flag == null || username == null){
            throw new NotFoundException();
        }

        if (flag == true) {
            this.identityService.createMembership(username, "reviewers");
            Authority authority = this.authorityService.findByName("ROLE_REVIEWER");
            user.getAuthorities().add(authority);
            user.setReviewer(true);
            user.setEnabledReviewer(true);
            this.userRepository.save(user);
            System.out.println("Korisnik prihvacen kao recenzent");
        }else{
            System.out.println("Korisnik odbijen");
        }
    }
}
