package org.upp.scholar.model;

import lombok.*;
import org.upp.scholar.entity.Magazine;
import org.upp.scholar.entity.ScientificArea;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MagazineDto {
    private Long id;
    private String issn;
    private String name;
    private String payment;
    private String merchantId;
    private String magazineStatus;
    private String scientificAreas;
    private Boolean isMerchant;
    private Boolean flag;

    public MagazineDto(Magazine magazine){
        this.id = magazine.getId();
        this.issn = magazine.getIssn();
        this.name = magazine.getName();
        this.payment = magazine.getPaymentType().name();
        this.magazineStatus = magazine.getMagazineStatus().name();
        this.flag = false;
        List<String> scientificAreasString = new ArrayList<>();
        for (ScientificArea scientificArea: magazine.getScientificAreas()){
            scientificAreasString.add(scientificArea.getName());
        }
        this.scientificAreas = scientificAreasString.toString();
        if (magazine.getMerchant() != null){
            this.isMerchant = magazine.getMerchant().getEnabled();
            this.merchantId = magazine.getMerchant().getMerchantId();
        }else{
            this.isMerchant = false;
        }
    }
}
