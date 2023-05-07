package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.repository.AccidentMem;
import ru.job4j.repository.AccidentTypeMem;
import ru.job4j.repository.RuleMem;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentMem accidentMem;

    private final AccidentTypeMem accidentTypeMem;

    private final RuleMem ruleMem;

    public Optional<Accident> create(Accident accident, String[] rids) {
        Optional<AccidentType> accidentType =
                accidentTypeMem.findAccidentTypeById(accident.getType().getId());
        accidentType.ifPresent(accident::setType);
        accident.setRules(ruleMem.getRulesByIds(ruleIdsFormRequest(rids)));
        Optional<Accident> optionalAccident = accidentMem.create(accident);
        return optionalAccident.isEmpty() ? Optional.empty() : optionalAccident;
    }

    public void update(Accident accident, String[] rids) {
        Optional<AccidentType> accidentType =
                accidentTypeMem.findAccidentTypeById(accident.getType().getId());
        accidentType.ifPresent(accident::setType);
        accident.setRules(ruleMem.getRulesByIds(ruleIdsFormRequest(rids)));
        accidentMem.update(accident);
    }

    public Collection<Accident> findAll() {
        return accidentMem.getAll();
    }

    public Optional<Accident> findById(int id) {
        Optional<Accident> optionalAccident = accidentMem.findById(id);
        return optionalAccident.isEmpty() ? Optional.empty() : optionalAccident;
    }

    public List<Integer> ruleIdsFormRequest(String[] ids) {
        return Arrays.stream(ids).map(Integer::parseInt).collect(Collectors.toList());
    }

}
