package ru.alex0d.javaspring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.alex0d.javaspring.models.Team;
import ru.alex0d.javaspring.services.TeamService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<Team> filterByAllFields(@RequestParam(required = false) Long id,
                                        @RequestParam(required = false) String name,
                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate creationDate) {
        return teamService.filterTeams(id, name, creationDate);
    }

    @PostMapping
    public void createTeam(@RequestBody Team team) {
        teamService.createTeam(team);
    }

    @DeleteMapping("/{id}")
    public void deleteTeamById(@PathVariable Long id) {
        teamService.deleteTeamById(id);
    }

}
