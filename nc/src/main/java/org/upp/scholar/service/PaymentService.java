package org.upp.scholar.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.upp.scholar.entity.*;
import org.upp.scholar.model.*;
import org.upp.scholar.repository.MerchantRepository;
import org.upp.scholar.repository.PaymentRepository;
import org.upp.scholar.repository.SubscriptionRepository;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Slf4j
@Service
public class PaymentService {

    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";
    @Value("${ip.address}")
    private String SERVER_ADDRESS;
    @Value("${server.port}")
    private String SERVER_PORT;
    @Value("${frontend.address}")
    private String FRONTEND_ADDRESS;
    @Value("${frontend.port}")
    private String FRONTEND_PORT;
    private static final String GATEWAY_PORT = "8082";

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public RedirectionResponse registerMerchant(MerchantRequest merchantRequest){
        log.info("Start register merchant");
        merchantRequest.setReturnUrl(HTTP_PREFIX + this.FRONTEND_ADDRESS + ":" + this.FRONTEND_PORT + "/magazine");
        log.info("Send registration request to gateway service");
        RedirectionResponse redirectionResponse = this.restTemplate.postForEntity(HTTPS_PREFIX + this.SERVER_ADDRESS + ":" + GATEWAY_PORT + "/register", merchantRequest, RedirectionResponse.class).getBody();
        log.info("Redirection response: {}", redirectionResponse);
        if (redirectionResponse == null || redirectionResponse.getId() == null || redirectionResponse.getRedirectionUrl() == null){
            throw new NullPointerException("Redirection response is not valid");
        }
        Magazine magazine = this.magazineService.findByIssn(merchantRequest.getIssn());
        if (magazine == null){
            throw new NotFoundException("Magazine with issn " + merchantRequest.getIssn() + "not found");
        }
        Merchant merchant = magazine.getMerchant();
        if (merchant != null){
            if (!merchant.getEnabled()) {
                log.info("Merchant already exist change merchantId", merchant);
                merchant.setMerchantId(redirectionResponse.getId());
                this.merchantRepository.save(merchant);
            }else{
                throw new NotFoundException("Magazine has already become a merchant");
            }
        }else {
            Merchant newMerchant = Merchant.builder()
                    .merchantId(redirectionResponse.getId())
                    .enabled(false)
                    .build();
            this.merchantRepository.save(newMerchant);
            log.info("Save Merchant: {}", newMerchant);
            magazine.setMerchant(newMerchant);
            this.magazineService.save(magazine);
        }
        return redirectionResponse;
    }

    public RegistrationCompleteDto completeRegistration(String merchantId){
        Assert.notNull(merchantId, "Merchant Id can't be null");
        log.info("Complete registration");
        RegistrationCompleteDto registrationCompleteDto = this.restTemplate.getForEntity(HTTPS_PREFIX + this.SERVER_ADDRESS + ":" + GATEWAY_PORT + "/complete_registration/" + merchantId, RegistrationCompleteDto.class).getBody();
        log.info("Response dto: {}", registrationCompleteDto);
        if (registrationCompleteDto == null){
            throw new NullPointerException("Registration complete object is null");
        }

        Merchant merchant = this.merchantRepository.findByMerchantId(merchantId);
        if (merchant == null){
            throw new NotFoundException("Not found merchant with id " + merchantId);
        }
        merchant.setEnabled(registrationCompleteDto.isFlag());
        this.merchantRepository.save(merchant);
        return registrationCompleteDto;
    }

    public RedirectionResponse preparePayment(PaymentDto paymentDto) {
        log.info("Start payment process");
        PaymentRequest paymentRequest = new PaymentRequest(paymentDto);
        paymentRequest.setReturnUrl(HTTP_PREFIX + this.FRONTEND_ADDRESS + ":" + this.FRONTEND_PORT + paymentDto.getReturnUrl());
        log.info("Send payment request to gateway service : {}", paymentRequest);
        RedirectionResponse redirectionResponse = this.restTemplate.postForEntity(HTTPS_PREFIX + this.SERVER_ADDRESS + ":" + GATEWAY_PORT + "/prepare", paymentRequest, RedirectionResponse.class).getBody();
        log.info("Redirection response: {}", redirectionResponse);
        if (redirectionResponse == null || redirectionResponse.getId() == null || redirectionResponse.getRedirectionUrl() == null){
            throw new NullPointerException("Redirection response is not valid");
        }
        User user = this.userService.findByUsername(paymentDto.getUsername());
        if (user == null){
            throw new NotFoundException("Not found user with username: " + paymentDto.getUsername());
        }

        Payment payment = Payment.builder()
                .status(MerchantOrderStatus.IN_PROGRESS)
                .merchantOrderId(redirectionResponse.getId())
                .user(user)
                .build();
        if (paymentDto.getItemType().equals("WORK")){
            ScientificWork scientificWork = this.magazineService.findScientificWorkById(paymentDto.getItemId());
            if (scientificWork == null){
                throw new NotFoundException("Scientific work with id " + paymentDto.getItemId() + "not found");
            }
            payment.setScientificWork(scientificWork);
        }else if (paymentDto.getItemType().equals("EDITION")){
            Edition edition = this.magazineService.findEditionById(paymentDto.getItemId());
            if (edition == null){
                throw new NotFoundException("Edition with id " + paymentDto.getItemId() + "not found");
            }
            payment.setEdition(edition);
        }
        log.info("Save payment");
        this.paymentRepository.save(payment);
        return redirectionResponse;
    }

    public PaymentCompleteDto completePayment(String paymentId){
        Assert.notNull(paymentId, "Payment Id can't be null");
        log.info("Complete payment");
        MerchantOrderStatus merchantOrderStatus = this.restTemplate.getForEntity(HTTPS_PREFIX + this.SERVER_ADDRESS + ":" + GATEWAY_PORT + "/order_status/" + paymentId, MerchantOrderStatus.class).getBody();
        log.info("Response status: {}", merchantOrderStatus);
        if (merchantOrderStatus == null){
            throw new NullPointerException("Merchant order status is null");
        }

        Payment payment = this.paymentRepository.findByMerchantOrderId(paymentId);
        if (payment == null){
            throw new NotFoundException("Not found payment with id " + paymentId);
        }
        payment.setStatus(merchantOrderStatus);
        log.info("Save payment");
        this.paymentRepository.save(payment);

        PaymentCompleteDto paymentCompleteDto = new PaymentCompleteDto();
        if (merchantOrderStatus.equals(MerchantOrderStatus.FINISHED)){
            paymentCompleteDto.setFlag(true);
        }else{
            paymentCompleteDto.setFlag(false);
        }

        if (payment.getEdition() != null){
            paymentCompleteDto.setId(payment.getEdition().getId());
        }else if (payment.getScientificWork() != null){
            paymentCompleteDto.setId(payment.getScientificWork().getId());
        }

        return paymentCompleteDto;
    }

    @Scheduled(fixedDelay = 30000, initialDelay = 10000)
    public void updatePaymentStatus() {
        log.info("Start payment checking");
        List<Payment> paymentInProgress = this.paymentRepository.findByStatus(MerchantOrderStatus.IN_PROGRESS);
        for (Payment payment : paymentInProgress){
            log.info("Check payment with merchant order id: {}", payment.getMerchantOrderId());
            log.info("Payment request is sent to gateway service");
            MerchantOrderStatus merchantOrderStatus = this.restTemplate.getForEntity(HTTPS_PREFIX + this.SERVER_ADDRESS + ":" + GATEWAY_PORT + "/order_status/" + payment.getMerchantOrderId(), MerchantOrderStatus.class).getBody();
            if (merchantOrderStatus.equals(MerchantOrderStatus.FINISHED)){
                log.info("Payment with merchant order id : {} changed status to FINISHED", payment.getMerchantOrderId());
                payment.setStatus(MerchantOrderStatus.FINISHED);
                this.paymentRepository.save(payment);
                log.info("Payment with merchant order id : {} is successfully updated", payment.getMerchantOrderId());
            }else if(merchantOrderStatus.equals(MerchantOrderStatus.CANCELED)){
                log.info("Payment with merchant order id : {} changed status to CANCELED", payment.getMerchantOrderId());
                payment.setStatus(MerchantOrderStatus.CANCELED);
                this.paymentRepository.save(payment);
                log.info("Payment with merchant order id : {} is successfully updated", payment.getMerchantOrderId());
            }else if (merchantOrderStatus.equals(MerchantOrderStatus.EXPIRED)){
                log.info("Payment with merchant order id : {} changed status to EXPIRED", payment.getMerchantOrderId());
                payment.setStatus(MerchantOrderStatus.EXPIRED);
                this.paymentRepository.save(payment);
                log.info("Payment with merchant order id : {} is successfully updated", payment.getMerchantOrderId());
            }
        }
    }

    public RedirectionResponse subscribe(SubscriptionDto subscriptionRequest) {
        log.info("Start subscription process");
        MerchantRequest merchantRequest = new MerchantRequest();
        merchantRequest.setMerchantId(subscriptionRequest.getMerchantId());
        merchantRequest.setReturnUrl(HTTP_PREFIX + this.SERVER_ADDRESS + ":" + this.SERVER_PORT + "/magazine_list");
        log.info("Send subscription request to gateway service");
        RedirectionResponse redirectionResponse = this.restTemplate.postForEntity(HTTPS_PREFIX + this.SERVER_ADDRESS + ":" + GATEWAY_PORT + "/subscription", merchantRequest, RedirectionResponse.class).getBody();
        log.info("Redirection response: {}", redirectionResponse);
        if (redirectionResponse == null || redirectionResponse.getId() == null || redirectionResponse.getRedirectionUrl() == null){
            throw new NullPointerException("Redirection response is not valid");
        }
        User user = this.userService.findByUsername(subscriptionRequest.getUsername());
        if (user == null){
            throw new NotFoundException("Not found user with username: " + subscriptionRequest.getUsername());
        }
        Magazine magazine = this.magazineService.findById(subscriptionRequest.getMagazineId());
        if (magazine == null){
            throw new NotFoundException("Not found magazine with id: " + subscriptionRequest.getMagazineId());
        }
        Subscription subscription = Subscription.builder()
                .subscriptionId(redirectionResponse.getId())
                .magazine(magazine)
                .user(user)
                .status(SubscriptionStatus.APPROVAL_PENDING)
                .build();

        this.subscriptionRepository.save(subscription);
        log.info("Subscription saved");
        return redirectionResponse;
    }

    public RegistrationCompleteDto completeSubscription(String subscriptionId){
        Assert.notNull(subscriptionId , "Payment Id can't be null");
        log.info("Complete subscription");
        SubscriptionStatus subscriptionStatus = this.restTemplate.getForEntity(HTTPS_PREFIX + this.SERVER_ADDRESS + ":" + GATEWAY_PORT + "/subscription/" + subscriptionId, SubscriptionStatus.class).getBody();
        log.info("Response status: {}", subscriptionStatus);
        if (subscriptionStatus == null){
            throw new NullPointerException("Subscription status is null");
        }

        Subscription subscription = this.subscriptionRepository.findBySubscriptionId(subscriptionId);
        if (subscription == null){
            throw new NotFoundException("Not found subscription with id " + subscriptionId);
        }
        subscription.setStatus(subscriptionStatus);
        log.info("Save subscription");
        this.subscriptionRepository.save(subscription);

        RegistrationCompleteDto subscriptionCompleteDto = new RegistrationCompleteDto();
        if (subscriptionStatus.equals(SubscriptionStatus.ACTIVE)){
            subscriptionCompleteDto.setFlag(true);
        }else{
            subscriptionCompleteDto.setFlag(false);
        }

        return subscriptionCompleteDto;
    }

    public ResponseEntity<SubscriptionCancelResponse> cancelSubscription(SubscriptionCancelRequest subscriptionCancelRequest) {
        return this.restTemplate.postForEntity(HTTPS_PREFIX + this.SERVER_ADDRESS + ":" + GATEWAY_PORT + "/subscription/cancel", subscriptionCancelRequest, SubscriptionCancelResponse.class);
    }
}
