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
import ru.alex0d.javaspring.models.Footballer;
import ru.alex0d.javaspring.models.Team;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FootballerService {
    private final SessionFactory sessionFactory;
    private Session session;

    @PostConstruct
    void init() {
        session = sessionFactory.openSession();
    }

    public List<Footballer> getAllFootballers() {
        return session.createQuery("FROM Footballer",
                Footballer.class).getResultList();
    }

    public void createFootballer(Footballer footballer) {
        Transaction transaction = session.beginTransaction();
        session.merge(footballer);
        transaction.commit();
    }

    public void deleteFootballerById(Long id) {
        Transaction transaction = session.beginTransaction();
        Footballer footballer = session.get(Footballer.class, id);
        session.remove(footballer);
        transaction.commit();
    }

    public List<Footballer> filterFootballers(Long id, String firstName, String lastName, Team team) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Footballer> cq = cb.createQuery(Footballer.class);
        Root<Footballer> root = cq.from(Footballer.class);

        List<Predicate> predicates = new ArrayList<>();

        if (id != null) {
            predicates.add(cb.equal(root.get("id"), id));
        }

        if (firstName != null && !firstName.isEmpty()) {
            predicates.add(cb.like(root.get("firstName"), "%" + firstName + "%"));
        }

        if (lastName != null && !lastName.isEmpty()) {
            predicates.add(cb.like(root.get("lastName"), "%" + lastName + "%"));
        }

        if (team != null) {
            predicates.add(cb.equal(root.get("team"), team));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Footballer> query = session.createQuery(cq);

        return query.getResultList();
    }

}
