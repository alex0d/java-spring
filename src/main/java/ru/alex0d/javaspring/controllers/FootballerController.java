package ru.alex0d.javaspring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.alex0d.javaspring.models.Footballer;
import ru.alex0d.javaspring.models.Team;
import ru.alex0d.javaspring.services.FootballerService;

import java.util.List;

@RestController
@RequestMapping("/footballers")
public class FootballerController {
    private final FootballerService footballerService;

    @Autowired
    public FootballerController(FootballerService footballerService) {
        this.footballerService = footballerService;
    }

    @GetMapping
    public List<Footballer> filterFootballers(@RequestParam(required = false) Long id,
                                              @RequestParam(required = false) String firstName,
                                              @RequestParam(required = false) String lastName,
                                              @RequestParam(required = false) Long teamId) {
        Team team = null;

        if (teamId != null) {
            team = new Team();
            team.setId(teamId);
        }

        return footballerService.filterFootballers(id, firstName, lastName, team);
    }

    @PostMapping
    public void createFootballer(@RequestBody Footballer footballer) {
        footballerService.createFootballer(footballer);
    }

    @DeleteMapping("/{id}")
    public void deleteFootballerById(@PathVariable Long id) {
        footballerService.deleteFootballerById(id);
    }

}
