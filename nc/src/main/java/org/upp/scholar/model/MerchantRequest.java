package org.upp.scholar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantRequest {

    private String name;
    private String merchantId;
    private String issn;
    private String returnUrl;
}