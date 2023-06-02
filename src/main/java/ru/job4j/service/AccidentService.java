package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;
import ru.job4j.repository.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentJdbcTemplate accidentMem;

    private final AccidentHibernate accidentHibernate;

    private final AccidentTypeHibernate accidentTypeHibernate;

    private final RuleHibernate ruleHibernate;

    public Optional<Accident> create(Accident accident, int typeId, String[] rids) {
        Optional<AccidentType> accidentType = accidentTypeHibernate.findById(typeId);
        accidentType.ifPresent(accident::setType);
        accident.setRules(new HashSet<>(ruleHibernate.getSelectedRules(ruleIdsFormRequest(rids))));
        Optional<Accident> optionalAccident = accidentHibernate.save(accident);
        return optionalAccident.isEmpty() ? Optional.empty() : optionalAccident;
    }

    public void update(Accident accident, String[] rids) {
        Optional<AccidentType> accidentType =
                accidentTypeHibernate.findById(accident.getType().getId());
        accidentType.ifPresent(accident::setType);
        accident.setRules(new HashSet<>(ruleHibernate.getSelectedRules(ruleIdsFormRequest(rids))));
        accidentHibernate.update(accident);
    }

    public Collection<Accident> findAll() {
        return accidentHibernate.getAll();
    }

    public Optional<Accident> findById(int id) {
        Optional<Accident> optionalAccident = accidentHibernate.findById(id);
        return optionalAccident.isEmpty() ? Optional.empty() : optionalAccident;
    }

    public void delete(int id) {
        accidentMem.delete(id);
    }

    public List<Integer> ruleIdsFormRequest(String[] ids) {
        return Arrays.stream(ids).map(Integer::parseInt).collect(Collectors.toList());
    }

}
