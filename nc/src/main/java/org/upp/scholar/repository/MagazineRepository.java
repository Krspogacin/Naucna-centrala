package org.upp.scholar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upp.scholar.entity.Magazine;
import org.upp.scholar.entity.MagazineStatus;
import org.upp.scholar.entity.User;

import java.util.List;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Long> {

    Magazine findByIssn(String issn);
    List<Magazine> findByMagazineStatus(MagazineStatus magazineStatus);
    List<Magazine> findAllByChiefEditor(User chiefEditor);
}
