import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private httpClient: HttpClient) { }

  registerMerchant(merchantRequest) {
    return this.httpClient.post('/api/payment/register', merchantRequest);
  }

  completeRegistration(merchantId) {
    return this.httpClient.get('/api/payment/complete_registration/'.concat(merchantId));
  }

  preparePayment(paymentDto) {
    return this.httpClient.post('/api/payment/prepare', paymentDto);
  }

  completePayment(merchantOrderId) {
    return this.httpClient.get('/api/payment/complete_payment/'.concat(merchantOrderId));
  }

  prepareSubscription(subscriptionDto) {
    return this.httpClient.post('/api/payment/subscription', subscriptionDto);
  }

  completeSubscription(subscriptionId) {
    return this.httpClient.get('/api/payment/complete_subscription/'.concat(subscriptionId));
  }

  cancelSubscription(cancelDto) {
    return this.httpClient.post('/api/payment/subscription/cancel', cancelDto);
  }
}
