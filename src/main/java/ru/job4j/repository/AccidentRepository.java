package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentRepository {

    private final CrudRepository repository;

    public Optional<Accident> create(Accident accident) {
        repository.run(session -> session.persist(accident));
        return Optional.of(accident);
    }

    public void update(Accident accident) {
        repository.run(session -> session.merge(accident));
    }

    public void delete(int accId) {
        repository.run(
                "delete from Accident where id = :fId",
                Map.of("fId", accId)
        );
    }

    public List<Accident> findAll() {
        return repository.query(
                "select distinct a "
                        + "from Accident a"
                        + " join fetch a.type "
                        + "join fetch a.rules", Accident.class);
    }

    public Optional<Accident> findById(int accId) {
        return repository.optional(
                "select distinct a "
                        + "from Accident a "
                        + "join fetch a.type "
                        + "join fetch a.rules "
                        + "where a.id = :fId", Accident.class,
                Map.of("fId", accId)
        );
    }

}
