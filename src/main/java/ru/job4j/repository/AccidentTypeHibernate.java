package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeHibernate implements AutoCloseable {

    private final SessionFactory sf;

    public Optional<AccidentType> save(AccidentType accidentType) {
        try (Session session = sf.openSession()) {
            session.save(accidentType);
            return Optional.of(accidentType);
        }
    }

    public List<AccidentType> getAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from AccidentType", AccidentType.class)
                    .list();
        }
    }

    public Optional<AccidentType> findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        AccidentType result = (AccidentType) session.createQuery(
                        "from AccidentType at where at.id = :fId")
                .setParameter("fId", id)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return Optional.of(result);
    }

    @Override
    public void close() {
        sf.close();
    }
}
