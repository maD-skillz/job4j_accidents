package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

    public int update(Accident accident, String[] rIds) {
        String updateSql = "UPDATE accidents "
                + "SET name = ?, text = ?, address = ?, types_id = ? "
                + "WHERE id = ?";

        int rowsAffected = jdbc.update(updateSql,
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId());

        if (rowsAffected > 0) {
            String selectRulesSql = "SELECT rules_id FROM accidents_rules WHERE accidents_id = ?";
            List<Integer> currentRuleIds = jdbc.queryForList(
                    selectRulesSql, Integer.class, accident.getId());
            List<Integer> newRuleIds = new ArrayList<>();
            for (String i : rIds) {
                newRuleIds.add(Integer.parseInt(i));
            }

            List<Integer> rulesToDelete = currentRuleIds.stream()
                    .filter(ruleId -> !newRuleIds.contains(ruleId))
                    .collect(Collectors.toList());

            List<Integer> rulesToInsert = newRuleIds.stream()
                    .filter(ruleId -> !currentRuleIds.contains(ruleId))
                    .collect(Collectors.toList());

            if (!rulesToDelete.isEmpty()) {
                String deleteSql =
                        "DELETE FROM accidents_rules WHERE accidents_id = ? AND rules_id = ?";
                jdbc.batchUpdate(deleteSql, new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, accident.getId());
                        ps.setInt(2, rulesToDelete.get(i));
                    }

                    @Override
                    public int getBatchSize() {
                        return rulesToDelete.size();
                    }
                });
            }

            if (!rulesToInsert.isEmpty()) {
                String insertSql =
                        "INSERT INTO accidents_rules (accidents_id, rules_id) VALUES (?, ?)";
                jdbc.batchUpdate(insertSql, new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, accident.getId());
                        ps.setInt(2, rulesToInsert.get(i));
                    }

                    @Override
                    public int getBatchSize() {
                        return rulesToInsert.size();
                    }
                });
            }
        }

        return rowsAffected;
    }

    public void delete(int id) {
        jdbc.update("DELETE FROM accidents WHERE id = ?", id);
    }

}
