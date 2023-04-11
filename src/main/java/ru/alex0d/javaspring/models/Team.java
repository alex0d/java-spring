package ru.alex0d.javaspring.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Team {
    private String name;
    private LocalDate creationDate;
}
