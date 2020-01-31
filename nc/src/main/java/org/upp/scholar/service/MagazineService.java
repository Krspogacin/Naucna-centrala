package org.upp.scholar.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.upp.scholar.entity.*;
import org.upp.scholar.model.*;
import org.upp.scholar.repository.EditionRepository;
import org.upp.scholar.repository.MagazineRepository;
import org.upp.scholar.repository.ScientificWorkRepository;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MagazineService {

    private static final String HTTPS_PREFIX = "https://";
    @Value("${ip.address}")
    private String SERVER_ADDRESS;
    private static final String GATEWAY_PORT = "8082";

    @Autowired
    private UserService userService;

    @Autowired
    private MagazineRepository magazineRepository;

    @Autowired
    private EditionRepository editionRepository;

    @Autowired
    private ScientificWorkRepository scientificWorkRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Magazine findByIssn(String issn){
        Magazine magazine = this.magazineRepository.findByIssn(issn);
        return magazine;
    }

    public Magazine save(Magazine magazine){
        if (magazine == null){
            throw new NotFoundException();
        }
        return this.magazineRepository.save(magazine);
    }

    public List<MagazineDto> findMagazinesByEditor(String editor){
        User baseEditor = this.userService.findByUsername(editor);
        List<Magazine> magazines = this.magazineRepository.findAllByChiefEditor(baseEditor);
        List<MagazineDto> magazineDtos = new ArrayList<>();
        for (Magazine magazine: magazines){
            magazineDtos.add(new MagazineDto(magazine));
        }
        return magazineDtos;
    }

    public List<MagazineDto> findActiveMagazines(String username){
        List<MagazineDto> activeMagazines = new ArrayList<>();
        List<Magazine> magazines = this.magazineRepository.findAll();
        for (Magazine magazine: magazines){
            if (magazine.getMerchant() != null) {
                if (magazine.getMerchant().getEnabled()) {
                    MagazineDto magazineDto = new MagazineDto(magazine);
                    ResponseEntity<List<SubscriptionPlan>> subscriptionPlans = this.restTemplate.exchange(HTTPS_PREFIX + this.SERVER_ADDRESS + ":" + GATEWAY_PORT + "/subscription/plans/" + magazine.getMerchant().getMerchantId(), HttpMethod.GET, null,  new ParameterizedTypeReference<List<SubscriptionPlan>>() {});
                    if(subscriptionPlans != null && subscriptionPlans.hasBody()){
                        List<SubscriptionPlan> plans = subscriptionPlans.getBody();
                        if (plans == null){
                            magazineDto.setHasSubscription(false);
                        } else {
                            magazineDto.setHasSubscription(true);
                        }
                    } else {
                        magazineDto.setHasSubscription(false);
                    }
                    activeMagazines.add(magazineDto);
                }
            }
        }

        if (username != null && !username.equals("null")) {
            User user = this.userService.findByUsername(username);
            if (user == null) {
                throw new NotFoundException("User with username " + username + " not found");
            }
            List<Subscription> subscriptions = user.getSubscriptionList();
            if (subscriptions != null){
                for (Subscription subscription: subscriptions){
                    if (subscription.getStatus().equals(SubscriptionStatus.ACTIVE)) {
                        for (MagazineDto magazineDto: activeMagazines){
                            if (subscription.getMagazine().getId().equals(magazineDto.getId())) {
                                log.info("Change magazine flag");
                                magazineDto.setFlag(true);
                            }
                        }
                    }
                }
            }
        }
        return activeMagazines;
    }

    public Magazine findById(Long id){
        Optional<Magazine> magazine = this.magazineRepository.findById(id);
        if (magazine.isEmpty()) {
            throw new NotFoundException("Magazine with id: " + id + "not found");
        }
        return magazine.get();
    }

    public Edition findEditionById(Long id){
        Optional<Edition> edition = this.editionRepository.findById(id);
        if (edition.isEmpty()) {
            throw new NotFoundException("Edition with id: " + id + "not found");
        }
        return edition.get();
    }

    public ScientificWork findScientificWorkById(Long id){
        Optional<ScientificWork> work = this.scientificWorkRepository.findById(id);
        if (work.isEmpty()) {
            throw new NotFoundException("Scientific work with id: " + id + "not found");
        }
        return work.get();
    }

    public List<EditionDto> findMagazineEditions(Long id, String username){
        List<EditionDto> editionDtos = new ArrayList<>();
        Magazine magazine = this.findById(id);
        List<Edition> editions = magazine.getEditions();
        for (Edition edition: editions){
            editionDtos.add(new EditionDto(edition));
        }
        if(username != null && !username.equals("null")) {
            User user = this.userService.findByUsername(username);
            if (user == null) {
                throw new NotFoundException("User with username " + username + " not found");
            }

            List<Subscription> subscriptions = user.getSubscriptionList();
            if (subscriptions != null){
                for (Subscription subscription: subscriptions){
                    if (subscription.getStatus().equals(SubscriptionStatus.ACTIVE)) {
                        if (subscription.getMagazine().getId().equals(magazine.getId())) {
                            for (EditionDto editionDto: editionDtos) {
                                log.info("Change edition flag");
                                editionDto.setFlag(true);
                            }
                        }
                    }
                }
            }


            List<Payment> payments = user.getOrderList();
            if (payments != null) {
                for (Payment payment : payments) {
                    if (payment.getStatus().equals(MerchantOrderStatus.FINISHED)) {
                        if (payment.getEdition() != null) {
                            for (EditionDto editionDto : editionDtos) {
                                if (payment.getEdition().getId().equals(editionDto.getId())) {
                                    log.info("Change edition flag");
                                    editionDto.setFlag(true);
                                }
                            }
                        }
                    }
                }
            }
        }
        return editionDtos;
    }

    public List<ScientificWorkDto> getEdtionScientificWorks(Long id, String username){
        List<ScientificWorkDto> scientificWorkDtos = new ArrayList<>();
        Edition edition = this.findEditionById(id);
        List<ScientificWork> scientificWorks = edition.getScientificWorks();
        for (ScientificWork scientificWork: scientificWorks){
            scientificWorkDtos.add(new ScientificWorkDto(scientificWork));
        }
        if (username != null && !username.equals("null")) {
            User user = this.userService.findByUsername(username);
            if (user == null) {
                throw new NotFoundException("User with username " + username + " not found");
            }

            List<Subscription> subscriptions = user.getSubscriptionList();
            if (subscriptions != null){
                for (Subscription subscription: subscriptions){
                    if (subscription.getStatus().equals(SubscriptionStatus.ACTIVE)) {
                        if (subscription.getMagazine().getId().equals(edition.getMagazine().getId())) {
                            for (ScientificWorkDto scientificWorkDto: scientificWorkDtos) {
                                log.info("Change scientific work flag");
                                scientificWorkDto.setFlag(true);
                            }
                        }
                    }
                }
            }

            List<Payment> payments = user.getOrderList();
            if (payments != null) {
                for (Payment payment : payments) {
                    if (payment.getStatus().equals(MerchantOrderStatus.FINISHED)) {
                        if (payment.getEdition() != null) {
                            if (payment.getEdition().getId().equals(edition.getId())) {
                                for (ScientificWorkDto scientificWorkDto : scientificWorkDtos) {
                                    log.info("Change scientific work flag");
                                    scientificWorkDto.setFlag(true);
                                }
                                break;
                            }
                        } else if (payment.getScientificWork() != null) {
                            for (ScientificWorkDto scientificWorkDto : scientificWorkDtos) {
                                if (payment.getScientificWork().getId().equals(scientificWorkDto.getId())) {
                                    log.info("Change scientific work flag");
                                    scientificWorkDto.setFlag(true);
                                }
                            }
                        }
                    }
                }
            }
        }

        return scientificWorkDtos;
    }

    public EditionDto saveEdition (Edition edition){
        this.editionRepository.save(edition);
        return new EditionDto(edition);
    }
}
