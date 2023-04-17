package ru.alex0d.javaspring.services;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import ru.alex0d.javaspring.models.Team;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<Team> filterTeams(Long id, String name, LocalDate creationDate) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Team> cq = cb.createQuery(Team.class);
        Root<Team> root = cq.from(Team.class);

        List<Predicate> predicates = new ArrayList<>();

        if (id != null) {
            predicates.add(cb.equal(root.get("id"), id));
        }

        if (name != null && !name.isEmpty()) {
            predicates.add(cb.like(root.get("name"), "%" + name + "%"));
        }

        if (creationDate != null) {
            predicates.add(cb.equal(root.get("creationDate"), creationDate));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Team> query = session.createQuery(cq);

        return query.getResultList();
    }
}
