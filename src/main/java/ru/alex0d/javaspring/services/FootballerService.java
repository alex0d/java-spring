package ru.alex0d.javaspring.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.alex0d.javaspring.models.Footballer;

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

    @Transactional
    public void createFootballer(Footballer footballer) {
        Transaction transaction = session.beginTransaction();
        session.merge(footballer);
        transaction.commit();
    }

    @Transactional
    public void deleteFootballerById(Long id) {
        Transaction transaction = session.beginTransaction();
        Footballer footballer = session.get(Footballer.class, id);
        session.remove(footballer);
        transaction.commit();
    }

}
