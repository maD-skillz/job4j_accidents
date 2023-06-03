package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Rule;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RuleRepository {

    private final CrudRepository repository;

    public Optional<Rule> create(Rule rule) {
        repository.run(session -> session.persist(rule));
        return Optional.of(rule);
    }

    public List<Rule> findAll() {
        return repository.query("from Rule", Rule.class);
    }

    public Optional<Rule> findById(int rId) {
        return repository.optional(
                "from Rule where id = :fId", Rule.class,
                Map.of("fId", rId)
        );
    }

    public List<Rule> getSelectedRules(List<Integer> ids) {
        return repository.query("from Rule r where r.id IN(:fRules)", Rule.class,
                Map.of("fRules", ids));
    }

}
