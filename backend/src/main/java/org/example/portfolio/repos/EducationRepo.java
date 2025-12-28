package org.example.portfolio.repos;

import org.example.portfolio.entities.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EducationRepo  extends JpaRepository<Education,Long> {
}
