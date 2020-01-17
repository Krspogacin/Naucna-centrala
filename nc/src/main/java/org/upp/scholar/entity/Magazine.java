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
public class Magazine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String issn;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MagazineStatus magazineStatus;

    @ManyToOne(optional = false)
    private User chiefEditor;

    @ManyToMany
    @JoinTable(
            name = "magazine_editors",
            joinColumns = { @JoinColumn(name = "magazine_id") },
            inverseJoinColumns = { @JoinColumn(name = "editor_id") }
    )
    private List<User> editors;

    @ManyToMany
    @JoinTable(
            name = "magazine_reviewers",
            joinColumns = { @JoinColumn(name = "magazine_id") },
            inverseJoinColumns = { @JoinColumn(name = "reviewer_id") }
    )
    private List<User> reviewers;

    @ManyToMany
    @JoinTable(
            name = "magazine_scientific_areas",
            joinColumns = { @JoinColumn(name = "magazine_id") },
            inverseJoinColumns = { @JoinColumn(name = "scientific_area_id") }
    )
    private List<ScientificArea> scientificAreas;
}
