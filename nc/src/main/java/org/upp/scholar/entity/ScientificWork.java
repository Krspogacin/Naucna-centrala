package org.upp.scholar.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ScientificWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String coauthors;

    @Column
    private String keyTerms;

    @Column
    private String workAbstract;

    @Column
    private String description;

    @Column
    private Double price;

    @ManyToMany
    @JoinTable(
            name = "scientific_work_scientific_areas",
            joinColumns = { @JoinColumn(name = "scientific_work_id") },
            inverseJoinColumns = { @JoinColumn(name = "scientific_area_id") }
    )
    private List<ScientificArea> scientificAreas;

    @Column
    private Boolean enabled;

    @ManyToOne(fetch = FetchType.EAGER)
    private Edition edition;

    @OneToMany(mappedBy = "scientificWork", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Payment> payments;

}
