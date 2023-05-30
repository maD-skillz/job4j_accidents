package ru.job4j.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Rule;

import java.util.*;

@Repository
@AllArgsConstructor
public class RuleJdbcTemplate {

    private final JdbcTemplate jdbc;

    public Set<Rule> getSetRule(int id) {
        return new HashSet<Rule>(
                jdbc.query("select * from rules join accidents_rules "
                        + "on (rules.id = accidents_rules.rules_id) "
                        + "where accidents_rules.accidents_id = ?",
                        new BeanPropertyRowMapper<>(Rule.class), id)
        );
    }

    public Collection<Rule> getAll() {
        return jdbc.query("select * from rules",
                (resultSet, i) -> new Rule(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
                ));
    }

    public Rule getById(int id) {
        return (Rule) jdbc.queryForObject(
                "select * from rules where id = ?", new BeanPropertyRowMapper(Rule.class), id);
    }

    public Set<Rule> getRulesByIds(List<Integer> ids) {
        jdbc.execute("CREATE TEMPORARY TABLE IF NOT EXISTS rule_tmp (id INT NOT NULL)");
        List<Object[]> ruleIds = new ArrayList<>();
        for (Integer id : ids) {
            ruleIds.add(new Object[] {id});
        }
        jdbc.batchUpdate("INSERT INTO rule_tmp VALUES(?)", ruleIds);
        List<Rule> rules = jdbc.query(
                "SELECT * FROM rules WHERE id IN (SELECT id FROM rule_tmp)",
                (rs, rowNum) -> new Rule(rs.getInt("id"), rs.getString("name")));
        jdbc.update("DELETE FROM rule_tmp");
        return new HashSet<>(rules);
    }

}
