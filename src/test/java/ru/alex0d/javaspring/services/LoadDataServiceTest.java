package ru.alex0d.javaspring.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.alex0d.javaspring.models.Footballer;
import ru.alex0d.javaspring.models.Team;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class LoadDataServiceTest {

    @Mock
    private FootballerService footballerService;

    @Mock
    private TeamService teamService;

    @InjectMocks
    private LoadDataService loadDataService;

    @Test
    public void testClearAndLoadData() throws IOException {
        List<Footballer> footballers = Arrays.asList(
                new Footballer(1L, "John", "Doe", new Team(1L, "Team 1", LocalDate.now())),
                new Footballer(2L, "Jane", "Doe", new Team(2L, "Team 2", LocalDate.now()))
        );
        List<Team> teams = Arrays.asList(
                new Team(1L, "Team 1", LocalDate.now()),
                new Team(2L, "Team 2", LocalDate.now())
        );
        Mockito.when(footballerService.getAllFootballers()).thenReturn(footballers);
        Mockito.when(teamService.getAllTeams()).thenReturn(teams);

        loadDataService.clearAndLoadData();

        File footballersFile = new File("loaded_data/footballers.csv");
        assertTrue(footballersFile.exists());
        List<String> footballersLines = Files.readAllLines(footballersFile.toPath(), StandardCharsets.UTF_8);
        assertEquals("id,first_name,last_name,team_id", footballersLines.get(0));
        assertEquals("1,John,Doe,1", footballersLines.get(1));
        assertEquals("2,Jane,Doe,2", footballersLines.get(2));

        File teamsFile = new File("loaded_data/teams.csv");
        assertTrue(teamsFile.exists());
        List<String> teamsLines = Files.readAllLines(teamsFile.toPath(), StandardCharsets.UTF_8);
        assertEquals("id,name,creation_date", teamsLines.get(0));
        assertEquals("1,Team 1," + LocalDate.now(), teamsLines.get(1));
        assertEquals("2,Team 2," + LocalDate.now(), teamsLines.get(2));
    }
}