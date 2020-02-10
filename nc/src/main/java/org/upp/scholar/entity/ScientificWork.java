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
    private String keyTerms;

    @Column
    private String workAbstract;

    @Column
    private String description;

    @Column
    private Double price;

    @Column
    private String filePath;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    private ScientificArea scientificArea;

    @Column
    private Boolean enabled;

    @ManyToOne(fetch = FetchType.EAGER)
    private Edition edition;

    @OneToMany(mappedBy = "scientificWork", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Payment> payments;

    @OneToMany(mappedBy = "scientificWork", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Coauthor> coauthors;

}
