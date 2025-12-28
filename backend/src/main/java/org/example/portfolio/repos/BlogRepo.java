package org.example.portfolio.repos;

import org.example.portfolio.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BlogRepo extends JpaRepository<Blog,Long> {
    Optional<Blog> findByTitle(String title);
    boolean existsByTitle(String title);
    boolean deleteAllByTitle(String title);
    List<Blog> findAllByTitle(String title);
}
