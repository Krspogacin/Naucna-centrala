package org.upp.scholar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.upp.scholar.entity.ScientificWork;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScientificWorkDto {

    private Long id;
    private String title;
    private String coauthors;
    private String keyTerms;
    private String workAbstract;
    private String scientificAreas;
    private String description;
    private Double price;
    private boolean flag;

    public ScientificWorkDto(ScientificWork scientificWork){
        this.id = scientificWork.getId();
        this.title = scientificWork.getTitle();
        this.coauthors = scientificWork.getCoauthors();
        this.keyTerms = scientificWork.getKeyTerms();
        this.workAbstract = scientificWork.getWorkAbstract();
        this.description = scientificWork.getDescription();
        this.price = scientificWork.getPrice();
    }
}
