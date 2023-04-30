package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;
import ru.job4j.repository.AccidentMem;

import java.util.*;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentMem accidentMem;

    public Accident create(Accident accident) {
        return accidentMem.create(accident);
    }

    public void update(Accident accident) {
        accidentMem.update(accident);
    }

    public Collection<Accident> findAll() {
        return accidentMem.getAll();
    }

    public Accident findById(int id) {
        return accidentMem.findById(id);
    }

    public List<AccidentType> getAccidentTypes() {
        return accidentMem.getAccidentTypes();
    }

    public AccidentType findAccidentTypeById(int id) {
        return accidentMem.findAccidentTypeById(id);
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

}
