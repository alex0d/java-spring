package ru.alex0d.javaspring.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.alex0d.javaspring.models.Team;
import ru.alex0d.javaspring.repositories.TeamRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {
    private final TeamRepository teamRepository;

    public void createTeam(Team team) {
        log.info("Team created");
        teamRepository.save(team);
    }

    public void deleteTeamById(Long id) {
        log.info("Team deleted by id: " + id);
        teamRepository.deleteById(id);
    }

    public List<Team> filterTeams(Long id, String name, LocalDate creationDate) {
        if (id == null && name == null && creationDate == null) {
            log.info("Teams filtered by all null: returning all");
            return teamRepository.findAll();
        } else {
            List<Team> teams = teamRepository.findByIdOrNameContainingOrCreationDate(id, name, creationDate);
            List<Long> ids = teams.stream().map(Team::getId).toList();
            log.info("Teams filtered by id=" + id + " name=" + name + " creationDate=" + creationDate + ": " + ids);
            return teams;
        }
    }
}
