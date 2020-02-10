package org.upp.scholar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upp.scholar.entity.Coauthor;

@Repository
public interface CoauthorRepository extends JpaRepository<Coauthor, Long> {
}
