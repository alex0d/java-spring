package ru.alex0d.javaspring.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex0d.javaspring.models.Footballer;
import ru.alex0d.javaspring.models.Team;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@ManagedResource
public class LoadDataService {
    private final FootballerService footballerService;
    private final TeamService teamService;

    private final String DIRECTORY = "loaded_data";

    @Autowired
    public LoadDataService(FootballerService footballerService, TeamService teamService) {
        this.footballerService = footballerService;
        this.teamService = teamService;
    }

    @Scheduled(fixedDelay = 30 * 60_000)  // 30 minutes
    @ManagedOperation(description = "Clear and load data to csv files")
    @Transactional(readOnly = true)
    public void clearAndLoadData() {
        File directory = new File(DIRECTORY);
        if (directory.exists()) {
            for (File file : directory.listFiles()) {
                file.delete();
            }
        } else {
            directory.mkdir();
        }

        try (PrintWriter writer = new PrintWriter(DIRECTORY + "/footballers.csv", StandardCharsets.UTF_8)) {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

            List<Footballer> footballers = footballerService.getAllFootballers();
            csvPrinter.printRecord("id", "first_name", "last_name", "team_id");
            for (Footballer footballer : footballers) {
                csvPrinter.printRecord(footballer.getId(), footballer.getFirstName(), footballer.getLastName(), footballer.getTeam().getId());
            }
            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (PrintWriter writer = new PrintWriter(DIRECTORY + "/teams.csv", StandardCharsets.UTF_8)) {
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

            List<Team> teams = teamService.getAllTeams();
            csvPrinter.printRecord("id", "name", "creation_date");
            for (Team team : teams) {
                csvPrinter.printRecord(team.getId(), team.getName(), team.getCreationDate());
            }
            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
