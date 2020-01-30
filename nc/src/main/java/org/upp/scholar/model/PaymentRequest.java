package org.upp.scholar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private String merchantId;
    private String merchantName;
    private String merchantOrderId;
    private String item;
    private Double price;
    private String description;
    private String method;
    private String returnUrl;

    public PaymentRequest(PaymentDto paymentDto){
        this.merchantId = paymentDto.getMerchantId();
        this.item = paymentDto.getItem();
        this.price = paymentDto.getPrice();
        this.description = paymentDto.getDescription();
    }
}