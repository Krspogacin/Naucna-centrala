package org.upp.scholar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.upp.scholar.entity.ScientificWork;

public interface ScientificWorkRepository extends JpaRepository<ScientificWork, Long> {
}
