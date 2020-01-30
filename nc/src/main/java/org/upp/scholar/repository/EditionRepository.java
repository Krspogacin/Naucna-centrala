package org.upp.scholar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upp.scholar.entity.Edition;

@Repository
public interface EditionRepository extends JpaRepository<Edition, Long> {
}
