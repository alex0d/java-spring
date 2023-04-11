package ru.alex0d.javaspring.controllers;

import org.springframework.web.bind.annotation.*;
import ru.alex0d.javaspring.models.Team;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {
    private final List<Team> teams = new ArrayList<>();

    @GetMapping
    public List<Team> getAllTeams() {
        return teams;
    }

    @PostMapping
    public void createTeam(@RequestBody Team team) {
        teams.add(team);
    }

    @DeleteMapping("/{index}")
    public void deleteTeam(@PathVariable int index) {
        teams.remove(index);
    }

}
