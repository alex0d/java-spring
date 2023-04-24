package ru.alex0d.javaspring.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.alex0d.javaspring.models.Team;
import ru.alex0d.javaspring.repositories.TeamRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private TeamService teamService;

    @Test
    public void testCreateTeam() {
        Team team = new Team(1L, "Team 1", LocalDate.now());

        teamService.createTeam(team);

        Mockito.verify(teamRepository).save(team);
        Mockito.verify(emailService).sendEmail(Mockito.nullable(String.class), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void testDeleteTeamById() {
        Long id = 1L;

        teamService.deleteTeamById(id);

        Mockito.verify(teamRepository).deleteById(id);
    }

    @Test
    public void testGetAllTeams() {
        List<Team> teams = Arrays.asList(
                new Team(1L, "Team 1", LocalDate.now()),
                new Team(2L, "Team 2", LocalDate.now())
        );
        Mockito.when(teamRepository.findAll()).thenReturn(teams);

        List<Team> result = teamService.getAllTeams();

        assertEquals(teams, result);
    }

    @Test
    public void testFilterTeams() {
        List<Team> teams = Arrays.asList(
                new Team(1L, "Team 1", LocalDate.now()),
                new Team(2L, "Team 2", LocalDate.now())
        );
        Mockito.when(teamRepository.findAll()).thenReturn(teams);

        List<Team> result = teamService.filterTeams(null, null, null);

        assertEquals(teams, result);
    }
}
