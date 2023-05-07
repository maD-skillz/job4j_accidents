package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.Rule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Repository
public class RuleMem {

    private final Set<Rule> ruleSet = new HashSet<>();

    {
        ruleSet.add(new Rule(1, "Статья 1"));
        ruleSet.add(new Rule(2, "Статья 2"));
        ruleSet.add(new Rule(3, "Статья 3"));
    }

    public List<Rule> getAllRules() {
        return ruleSet.stream().toList();
    }

    public Set<Rule> getRulesByIds(List<Integer> ids) {
        Set<Rule> result = new HashSet<>();
        for (int i : ids) {
            for (Rule rule : ruleSet.stream().toList()) {
                if (rule.getId() == i) {
                    result.add(rule);
                }
            }
        }
        return result;
    }
}
