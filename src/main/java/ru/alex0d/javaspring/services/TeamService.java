package ru.alex0d.javaspring.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import ru.alex0d.javaspring.models.Team;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TeamService {
    private final SessionFactory sessionFactory;
    private Session session;

    @PostConstruct
    void init() {
        session = sessionFactory.openSession();
    }

    public List<Team> getAllTeams() {
        return session.createQuery("FROM Team",
                Team.class).getResultList();
    }

    public void createTeam(Team team) {
        Transaction transaction = session.beginTransaction();
        session.merge(team);
        transaction.commit();
    }

    public void deleteTeamById(Long id) {
        Transaction transaction = session.beginTransaction();
        Team team = session.get(Team.class, id);
        session.remove(team);
        transaction.commit();
    }
}
