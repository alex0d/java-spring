package ru.alex0d.javaspring.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.alex0d.javaspring.models.Footballer;
import ru.alex0d.javaspring.models.Team;
import ru.alex0d.javaspring.repositories.FootballerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FootballerService {
    private final FootballerRepository footballerRepository;

    public void createFootballer(Footballer footballer) {
        log.info("Footballer created");
        footballerRepository.save(footballer);
    }

    public void deleteFootballerById(Long id) {
        log.info("Footballer deleted by id: " + id);
        footballerRepository.deleteById(id);
    }

    public List<Footballer> filterFootballers(Long id, String firstName, String lastName, Team team) {
        if (id == null && firstName == null && lastName == null && team == null) {
            log.info("Footballers filtered by all null: returning all");
            return footballerRepository.findAll();
        } else {
            List<Footballer> footballers = footballerRepository.findByIdOrFirstNameContainingOrLastNameContainingOrTeam(id, firstName, lastName, team);
            List<Long> ids = footballers.stream().map(Footballer::getId).toList();
            Long teamId = team == null ? null : team.getId();
            log.info("Footballers filtered by id=" + id + " firstName=" + firstName + " lastName=" + lastName + " teamId=" + teamId + ": " + ids);
            return footballers;
        }
    }
}
