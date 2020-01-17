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
    private String magazineStatus;
    private String scientificAreas;

    public MagazineDto(Magazine magazine){
        this.id = magazine.getId();
        this.issn = magazine.getIssn();
        this.name = magazine.getName();
        this.payment = magazine.getPaymentType().name();
        this.magazineStatus = magazine.getMagazineStatus().name();
        List<String> scientificAreasString = new ArrayList<>();
        for (ScientificArea scientificArea: magazine.getScientificAreas()){
            scientificAreasString.add(scientificArea.getName());
        }
        this.scientificAreas = scientificAreasString.toString();
    }
}
