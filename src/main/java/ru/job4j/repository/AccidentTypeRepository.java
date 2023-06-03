package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.model.AccidentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeRepository {

    private final CrudRepository repository;

    public Optional<AccidentType> create(AccidentType accidentType) {
        repository.run(session -> session.persist(accidentType));
        return Optional.of(accidentType);
    }

    public List<AccidentType> findAll() {
        return repository.query("from AccidentType", AccidentType.class);
    }

    public Optional<AccidentType> findById(int accTypeId) {
        return repository.optional(
                "from AccidentType where id = :fId", AccidentType.class,
                Map.of("fId", accTypeId)
        );
    }

}
