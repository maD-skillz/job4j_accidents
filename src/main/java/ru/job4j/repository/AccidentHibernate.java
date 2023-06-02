package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentHibernate implements AutoCloseable {
    private final SessionFactory sf;

    public Optional<Accident> save(Accident accident) {
        try (Session session = sf.openSession()) {
            session.save(accident);
            return Optional.of(accident);
        }
    }

    public List<Accident> getAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("select a from Accident a "
                            + "left join fetch a.rules", Accident.class)
                                .list();
        }
    }

    public Optional<Accident> findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Accident result = (Accident) session.createQuery(
                        "select distinct a from Accident a "
                                + "left join fetch a.rules where a.id = :fId")
                .setParameter("fId", id)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return Optional.of(result);
    }

    public void update(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createQuery(
                    "update Accident set name = :fName, text = :fText, address = :fAddress, "
                            + "type = :fType where id = :fId")
                    .setParameter("fId", accident.getId())
                    .setParameter("fName", accident.getName())
                    .setParameter("fText", accident.getText())
                    .setParameter("fAddress", accident.getAddress())
                    .setParameter("fType", accident.getType())
                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void close() {
        sf.close();
    }

}
