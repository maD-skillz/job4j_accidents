package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
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

    private final AccidentTypeJdbcTemplate accTypeJdbc;
    private final AtomicInteger atomicId = new AtomicInteger(1);

    private final ResultSetExtractor<Map<Integer, Accident>> extractor = (resultSet) -> {
        Map<Integer, Accident> result = new HashMap<>();
        while (resultSet.next()) {
            Accident accident = new Accident(
                    resultSet.getInt("aId"),
                    resultSet.getString("aName"),
                    resultSet.getString("aText"),
                    resultSet.getString("aAddress"),
                    new AccidentType(
                            resultSet.getInt("tId"),
                            resultSet.getString("tName")),
                    new HashSet<>()
            );
            result.putIfAbsent(accident.getId(), accident);
            result.get(accident.getId()).addRule(new Rule(
                    resultSet.getInt("rId"),
                    resultSet.getString("rName")
            ));
        }
        return result;
    };

    public Optional<Accident> save(Accident accident, int typeId, String[] rids) {
        int id = atomicId.incrementAndGet();
        jdbc.update("insert into accidents (name, text, address, types_id) values(?, ?, ?, ?)",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accTypeJdbc.findById(typeId).get().getId());
                accident.setId(id);
        for (String i : rids) {
            jdbc.update("insert into accidents_rules (accidents_id, rules_id) values(?, ?)",
                    accident.getId(),
                    Integer.parseInt(i));
            }
        return Optional.of(accident);
    }

    public List<Accident> getAll() {
        Collection<Accident> accidents = jdbc.query(
                "select a.id aId, a.name aName, a.text aText, a.address aAddress, "
                        + "t.id tId, t.name tName, "
                        + "r.id rId, r.name rName "
                        + "from accidents a "
                        + "join accident_types t "
                        + "on a.types_id = t.id "
                        + "join accidents_rules ar on a.id = ar.accidents_id "
                        + "join rules r on r.id = ar.rules_id", extractor).values();
        return new ArrayList<>(accidents);
    }

    public Optional<Accident> findById(int id) {
        String sql = "SELECT a.id aId, a.name aName, a.text aText, a.address aAddress, "
                + "t.id tId, t.name tName, r.id rId, r.name rName "
                + "FROM accidents a "
                + "INNER JOIN accident_types t ON a.types_id = t.id "
                + "LEFT JOIN accidents_rules ar ON a.id = ar.accidents_id "
                + "LEFT JOIN rules r ON ar.rules_id = r.id "
                + "WHERE a.id = ?";

        Map<Integer, Accident> result = jdbc.query(sql, extractor, id);
        Accident accident = result.get(id);

        return Optional.ofNullable(accident);
    }

    public void update(Accident accident, String[] rIds) {
        String deleteSql = "DELETE FROM accidents_rules ar "
                + "WHERE ar.accidents_id = ?";
        jdbc.update(deleteSql, accident.getId());
        String updateSql = "UPDATE accidents "
                + "SET name = ?, text = ?, address = ?, types_id = ? "
                + "WHERE id = ?";
        jdbc.update(updateSql,
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId());
        String insertNewRules =
                "INSERT INTO accidents_rules (accidents_id, rules_id) VALUES (?, ?) ";
        for (String rId : rIds) {
            jdbc.update(insertNewRules, accident.getId(), Integer.parseInt(rId));
        }
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM accidents WHERE id = ?", id);
    }

}
