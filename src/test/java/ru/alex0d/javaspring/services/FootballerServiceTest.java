package ru.alex0d.javaspring.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.alex0d.javaspring.models.Footballer;
import ru.alex0d.javaspring.models.Team;
import ru.alex0d.javaspring.repositories.FootballerRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FootballerServiceTest {

    @Mock
    private FootballerRepository footballerRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private FootballerService footballerService;

    @Test
    public void testCreateFootballer() {
        Footballer footballer = new Footballer();
        footballerService.createFootballer(footballer);

        Mockito.verify(footballerRepository).save(footballer);
        Mockito.verify(emailService).sendEmail(Mockito.nullable(String.class), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void testDeleteFootballerById() {
        Long id = 1L;

        footballerService.deleteFootballerById(id);

        Mockito.verify(footballerRepository).deleteById(id);
    }

    @Test
    public void testGetAllFootballers() {
        List<Footballer> footballers = new ArrayList<>();
        footballers.add(new Footballer());
        Mockito.when(footballerRepository.findAll()).thenReturn(footballers);

        List<Footballer> result = footballerService.getAllFootballers();
        assertEquals(footballers, result);
    }

    @Test
    public void testFilterFootballers() {
        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        Team team = new Team();
        List<Footballer> footballers = new ArrayList<>();
        Mockito.when(footballerRepository.findByIdOrFirstNameContainingOrLastNameContainingOrTeam(id, firstName, lastName, team)).thenReturn(footballers);

        List<Footballer> result = footballerService.filterFootballers(id, firstName, lastName, team);
        assertEquals(footballers, result);
    }
}
