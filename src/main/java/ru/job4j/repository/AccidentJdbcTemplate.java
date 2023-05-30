package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;
    private final AtomicInteger atomicId = new AtomicInteger(1);

    private final RowMapper<Accident> accidentRowMapper = (resultSet, row) -> {
        Accident accident = new Accident();
        accident.setId(resultSet.getInt("id"));
        accident.setName(resultSet.getString("name"));
        accident.setText(resultSet.getString("text"));
        accident.setAddress(resultSet.getString("address"));
        accident.setType(new AccidentType(
                resultSet.getInt("id"),
                resultSet.getString("name")));
        return accident;
    };

    private final RowMapper<Rule> ruleRowMapper = (resultSet, row) -> {
        Rule rule = new Rule();
        rule.setId(resultSet.getInt("id"));
        rule.setName(resultSet.getString("name"));
        return rule;
    };

    private List<Rule> getRulesForAccident(int id) {
        String sql = "SELECT * FROM rules WHERE id IN ("
                + "SELECT rules_id FROM accidents_rules WHERE accidents_id = ?)";
        return jdbc.query(sql, new Object[]{id}, ruleRowMapper);
    }

    public Optional<Accident> save(Accident accident, String[] rids) {
        int id = atomicId.getAndIncrement();
        jdbc.update("insert into accidents (name, text, address, types_id) values(?, ?, ?, ?)",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId());
                accident.setId(id);

                for (String i : rids) {
                jdbc.update("insert into accidents_rules (accidents_id, rules_id) values(?, ?)",
                        accident.getId(),
                        Integer.parseInt(i));
            }
            return Optional.of(accident);
    }

    public List<Accident> getAll() {
        List<Accident> accidents = jdbc.query("select * from accidents", accidentRowMapper);
        for (Accident accident : accidents) {
            accident.setRules(new HashSet<>(getRulesForAccident(accident.getId())));
        }
        return accidents;
    }

    public Optional<Accident> findById(int id) {
        Accident accident = jdbc.queryForObject("select * from accidents where id = ?",
                new Object[]{id},
                accidentRowMapper);
        List<Rule> rules = getRulesForAccident(accident.getId());
        accident.setRules(new HashSet<>(rules));
        return Optional.of(accident);
    }

    public void update(Accident accident, String[] rids) {
        jdbc.update("update accidents set name = ?, text = ?, address = ? "
                        + "where id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getId());
        for (String i : rids) {
            jdbc.update("insert into accidents_rules (accidents_id, rules_id) values(?, ?)",
                    accident.getId(),
                    Integer.parseInt(i));
        }
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM accidents WHERE id = ?", id);
    }

}
