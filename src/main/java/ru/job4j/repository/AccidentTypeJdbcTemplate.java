package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeJdbcTemplate {

    private final JdbcTemplate jdbc;

    public Optional<AccidentType> save(AccidentType accidentType) {
        jdbc.update("insert into accident_types(name) values(?)", accidentType.getName());
        return Optional.of(accidentType);
    }

    public Collection<AccidentType> getAll() {
        return jdbc.query("select * from accident_types",
                (resultSet, row) -> new AccidentType(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                ));
    }

    public Optional<AccidentType> findById(int id) {
        return Optional.of(jdbc.query("select * from accident_types where id = ?",
                (resultSet, i) -> new AccidentType(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                ), id).stream().findAny().orElse(new AccidentType()));
    }
}
