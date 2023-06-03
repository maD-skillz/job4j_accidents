package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.repository.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentRepository accRepository;

    private final AccidentTypeRepository accTypeRepository;

    private final RuleRepository ruleRepository;

    public Optional<Accident> create(Accident accident, int typeId, String[] rids) {
        Optional<AccidentType> accidentType = accTypeRepository.findById(typeId);
        accidentType.ifPresent(accident::setType);
        accident.setRules(new HashSet<>(ruleRepository.getSelectedRules(ruleIdsFormRequest(rids))));
        Optional<Accident> optionalAccident = Optional.of(accRepository.save(accident));
        return optionalAccident.isEmpty() ? Optional.empty() : optionalAccident;
    }

    public void update(Accident accident, String[] rids) {
        Optional<AccidentType> accidentType =
                accTypeRepository.findById(accident.getType().getId());
        accidentType.ifPresent(accident::setType);
        accident.setRules(new HashSet<>(ruleRepository.getSelectedRules(ruleIdsFormRequest(rids))));
        accRepository.save(accident);
    }

    public List<Accident> findAll() {
        return (List<Accident>) accRepository.findAll();
    }

    public Optional<Accident> findById(int id) {
        Optional<Accident> optionalAccident = accRepository.findById(id);
        return optionalAccident.isEmpty() ? Optional.empty() : optionalAccident;
    }

    public void delete(int id) {
        accRepository.deleteById(id);
    }

    public List<Integer> ruleIdsFormRequest(String[] ids) {
        return Arrays.stream(ids).map(Integer::parseInt).collect(Collectors.toList());
    }

}
