package org.upp.scholar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.upp.scholar.entity.Authority;
import org.upp.scholar.entity.ScientificArea;
import org.upp.scholar.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername( String username );
    List<User> findDistinctByUsernameNotAndAuthoritiesContainsAndScientificAreasIsIn(String username, Authority authority, List<ScientificArea> scientificAreas);
}
