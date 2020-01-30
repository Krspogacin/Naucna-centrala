package org.upp.scholar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.upp.scholar.entity.Edition;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditionDto {

    private Long id;
    private Long magazineId;
    private String description;
    private String creationDate;
    private Double price;
    private boolean flag;

    public EditionDto(Edition edition){
        this.id = edition.getId();
        this.magazineId = edition.getMagazine().getId();
        this.description = edition.getDescription();
        this.creationDate = edition.getCreationDate();
        this.price = edition.getPrice();
        this.flag = false;
    }
}
