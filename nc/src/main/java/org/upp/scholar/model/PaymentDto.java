package org.upp.scholar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {
    private String merchantId;
    private String item;
    private Double price;
    private String description;
    private String returnUrl;
    private String itemType;
    private Long itemId;
    private String username;
}
