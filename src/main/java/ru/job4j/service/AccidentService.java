package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;
import ru.job4j.repository.AccidentMem;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentMem accidentMem;

    public Optional<Accident> create(Accident accident) {
        Optional<Accident> optionalAccident = accidentMem.create(accident);
        return optionalAccident.isEmpty() ? Optional.empty() : optionalAccident;
    }

    public void update(Accident accident) {
        accidentMem.update(accident);
    }

    public Collection<Accident> findAll() {
        return accidentMem.getAll();
    }

    public Optional<Accident> findById(int id) {
        Optional<Accident> optionalAccident = accidentMem.findById(id);
        return optionalAccident.isEmpty() ? Optional.empty() : optionalAccident;
    }

    public List<AccidentType> getAccidentTypes() {
        return accidentMem.getAccidentTypes();
    }

    public Optional<AccidentType> findAccidentTypeById(int id) {
        Optional<AccidentType> optionalAccidentType = accidentMem.findAccidentTypeById(id);
        return optionalAccidentType.isEmpty() ? Optional.empty() : optionalAccidentType;
    }

    public List<Rule> getAllRules() {
        return accidentMem.getAllRules();
    }

    public Set<Rule> getRulesByIds(List<Integer> ids) {
        Set<Rule> result = new HashSet<>();
        for (int i : ids) {
            for (Rule rule : accidentMem.getAllRules()) {
                if (rule.getId() == i) {
                    result.add(rule);
                }
            }
        }
        return result;
    }

    public List<Integer> ruleIdsFormRequest(String[] ids) {
        return Arrays.stream(ids).map(Integer::parseInt).collect(Collectors.toList());
    }

    public boolean checkRulesAndTypes(Set<Rule> rules, Optional<AccidentType> type) {
        if (rules == null) {
            return true;
        }
        if (type.isEmpty()) {
            return true;
        }
        return false;
    }

}
