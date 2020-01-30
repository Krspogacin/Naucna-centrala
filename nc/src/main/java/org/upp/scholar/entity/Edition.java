package org.upp.scholar.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Edition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Magazine magazine;

    @Column
    private String description;

    @Column
    private String creationDate;

    @Column
    private Double price;

    @OneToMany(mappedBy = "edition", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ScientificWork> scientificWorks;

    @OneToMany(mappedBy = "edition", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Payment> payments;
}
