package ru.alex0d.javaspring.controllers;

import org.springframework.web.bind.annotation.*;
import ru.alex0d.javaspring.models.Footballer;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/footballers")
public class FootballerController {
    private final List<Footballer> footballers = new ArrayList<>();

    @GetMapping
    public List<Footballer> getAllFootballers() {
        return footballers;
    }

    @PostMapping
    public void createFootballer(@RequestBody Footballer footballer) {
        footballers.add(footballer);
    }

    @DeleteMapping("/{index}")
    public void deleteFootballer(@PathVariable int index) {
        footballers.remove(index);
    }

}
