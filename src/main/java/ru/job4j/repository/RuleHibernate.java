package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Rule;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RuleHibernate implements AutoCloseable {

    private final SessionFactory sf;

    public Optional<Rule> save(Rule rule) {
        try (Session session = sf.openSession()) {
            session.save(rule);
            return Optional.of(rule);
        }
    }

    public List<Rule> getAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from Rule", Rule.class)
                    .list();
        }
    }

    public Optional<Rule> findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Rule result = (Rule) session.createQuery(
                        "from Rule r where r.id = :fId")
                .setParameter("fId", id)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return Optional.of(result);
    }

    public List<Rule> getSelectedRules(List<Integer> ids) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Rule> result = session.createQuery(
                        "from Rule r where r.id IN(:fRules)", Rule.class)
                .setParameter("fRules", ids)
                .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void close() {
        sf.close();
    }

}
