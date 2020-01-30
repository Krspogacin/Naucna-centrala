package org.upp.scholar.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String merchantOrderId;

    @Column
    @Enumerated(EnumType.STRING)
    private MerchantOrderStatus status;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne
    private Edition edition;

    @ManyToOne
    private ScientificWork scientificWork;
}
