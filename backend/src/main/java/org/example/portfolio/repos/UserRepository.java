package org.example.portfolio.repos;
import org.example.portfolio.entities.Owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Owner,Long> {
    Optional<Owner> findByEmail(String email);
}
