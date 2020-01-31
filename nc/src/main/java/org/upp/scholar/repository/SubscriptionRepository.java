package org.upp.scholar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upp.scholar.entity.Magazine;
import org.upp.scholar.entity.Subscription;
import org.upp.scholar.entity.SubscriptionStatus;
import org.upp.scholar.entity.User;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findBySubscriptionId (String subscriptionId);
    List<Subscription> findByStatus (SubscriptionStatus subscriptionStatus);
    Subscription findByMagazineAndUserAndStatus(Magazine magazine, User user, SubscriptionStatus subscriptionStatus);
}
