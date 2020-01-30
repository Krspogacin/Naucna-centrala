package org.upp.scholar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upp.scholar.entity.MerchantOrderStatus;
import org.upp.scholar.entity.Payment;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByMerchantOrderId (String merchantOrderId);
    List<Payment> findByStatus(MerchantOrderStatus merchantOrderStatus);
}
