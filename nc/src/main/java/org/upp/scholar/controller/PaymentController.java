package org.upp.scholar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upp.scholar.model.*;
import org.upp.scholar.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RedirectionResponse> registerMerchant(@RequestBody final MerchantRequest merchantRequest) {
        return new ResponseEntity<>(this.paymentService.registerMerchant(merchantRequest), HttpStatus.OK);
    }

    @GetMapping(value = "/complete_registration/{merchantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationCompleteDto> completeRegistration(@PathVariable String merchantId){
        return new ResponseEntity<>(this.paymentService.completeRegistration(merchantId), HttpStatus.OK);
    }

    @PostMapping(value = "/prepare", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RedirectionResponse> preparePayment(@RequestBody final PaymentDto paymentRequest) {
      return new ResponseEntity<>(this.paymentService.preparePayment(paymentRequest), HttpStatus.OK);
    }

    @GetMapping(value = "/complete_payment/{paymentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentCompleteDto> completePayment(@PathVariable String paymentId){
        return new ResponseEntity<>(this.paymentService.completePayment(paymentId), HttpStatus.OK);
    }

    @PostMapping(value = "/subscription", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RedirectionResponse> subscribe(@RequestBody final SubscriptionDto subscriptionRequest) {
       return new ResponseEntity<>(this.paymentService.subscribe(subscriptionRequest), HttpStatus.OK);
    }

    @GetMapping(value = "/complete_subscription/{subscriptionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentCompleteDto> completeSubscription(@PathVariable String subscriptionId){
        return new ResponseEntity<>(this.paymentService.completeSubscription(subscriptionId), HttpStatus.OK);
    }

    @PostMapping(value = "/subscription/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubscriptionCancelResponse> cancelSubscription(@RequestBody final SubscriptionCancelDto subscriptionCancelRequest) {
        return new ResponseEntity<>(this.paymentService.cancelSubscription(subscriptionCancelRequest), HttpStatus.OK);
    }
}
