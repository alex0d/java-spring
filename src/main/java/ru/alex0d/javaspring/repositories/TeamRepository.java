package ru.alex0d.javaspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alex0d.javaspring.models.Team;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByIdOrNameContainingOrCreationDate(Long id, String name, LocalDate creationDate);
}
