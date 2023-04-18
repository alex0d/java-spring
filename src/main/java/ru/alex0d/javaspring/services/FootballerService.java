package ru.alex0d.javaspring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.alex0d.javaspring.models.Footballer;
import ru.alex0d.javaspring.models.Team;
import ru.alex0d.javaspring.repositories.FootballerRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FootballerService {
    private final FootballerRepository footballerRepository;

    public void createFootballer(Footballer footballer) {
        footballerRepository.save(footballer);
    }

    public void deleteFootballerById(Long id) {
        footballerRepository.deleteById(id);
    }

    public List<Footballer> filterFootballers(Long id, String firstName, String lastName, Team team) {
        if (id == null && firstName == null && lastName == null && team == null) {
            return footballerRepository.findAll();
        } else {
            return footballerRepository.findByIdOrFirstNameContainingOrLastNameContainingOrTeam(id, firstName, lastName, team);
        }
    }
}
