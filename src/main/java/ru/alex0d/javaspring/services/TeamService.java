package ru.alex0d.javaspring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.alex0d.javaspring.models.Team;
import ru.alex0d.javaspring.repositories.TeamRepository;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public void createTeam(Team team) {
        teamRepository.save(team);
    }

    public void deleteTeamById(Long id) {
        teamRepository.deleteById(id);
    }

    public List<Team> filterTeams(Long id, String name, LocalDate creationDate) {
        if (id == null && name == null && creationDate == null) {
            return teamRepository.findAll();
        } else {
            return teamRepository.findByIdOrNameContainingOrCreationDate(id, name, creationDate);
        }
    }
}
