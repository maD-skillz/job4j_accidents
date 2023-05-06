package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Rule;
import ru.job4j.repository.RuleMem;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RuleService {

    private final RuleMem ruleMem;

    public List<Rule> getAllRules() {
        return ruleMem.getAllRules();
    }

    public Set<Rule> getRulesByIds(List<Integer> ids) {
        Set<Rule> result = new HashSet<>();
        for (int i : ids) {
            for (Rule rule : ruleMem.getAllRules()) {
                if (rule.getId() == i) {
                    result.add(rule);
                }
            }
        }
        return result;
    }
}
