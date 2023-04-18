package ru.alex0d.javaspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alex0d.javaspring.models.Footballer;
import ru.alex0d.javaspring.models.Team;

import java.util.List;

@Repository
public interface FootballerRepository extends JpaRepository<Footballer, Long> {
    List<Footballer> findByIdOrFirstNameContainingOrLastNameContainingOrTeam(Long id, String firstName, String lastName, Team team);
}
