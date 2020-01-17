package org.upp.scholar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.upp.scholar.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(String name);
}
