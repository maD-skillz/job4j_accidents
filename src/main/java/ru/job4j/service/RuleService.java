package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Rule;
import ru.job4j.repository.RuleHibernate;
import ru.job4j.repository.RuleMem;

import java.util.List;

@Service
@AllArgsConstructor
public class RuleService {

    private final RuleMem ruleMem;

    private final RuleHibernate ruleHibernate;

    public List<Rule> getAllRules() {
        return ruleHibernate.getAll();
    }

}
