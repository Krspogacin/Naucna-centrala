package org.upp.scholar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upp.scholar.entity.Merchant;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Merchant findByMerchantId(String merchantId);
}
