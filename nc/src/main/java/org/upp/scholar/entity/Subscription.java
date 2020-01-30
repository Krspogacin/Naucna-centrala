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
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String subscriptionId;

    @Column
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Magazine magazine;
}
