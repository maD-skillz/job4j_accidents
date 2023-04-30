package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private final Map<Integer, Accident> store = new ConcurrentHashMap<>();

    private final List<AccidentType> accidentTypes = new ArrayList<>();

    private final Set<Rule> ruleSet = new HashSet<>();

    {
        accidentTypes.add(new AccidentType(1, "Две машины"));
        accidentTypes.add(new AccidentType(2, "Машина и человек"));
        accidentTypes.add(new AccidentType(3, "Машина и велосипед"));

        ruleSet.add(new Rule(1, "Статья 1"));
        ruleSet.add(new Rule(2, "Статья 2"));
        ruleSet.add(new Rule(3, "Статья 3"));
    }

    private final AtomicInteger ids = new AtomicInteger(1);

    public Accident create(Accident accident) {
        int id = ids.getAndIncrement();
        accident.setId(id);
        store.put(id, accident);
        return accident;
    }

    public void update(Accident accident) {
        store.replace(accident.getId(), accident);
    }

    public Collection<Accident> getAll() {
       return store.values();
    }

    public Accident findById(int id) {
       return store.get(id);
    }

    public List<AccidentType> getAccidentTypes() {
        return accidentTypes;
    }

    public AccidentType findAccidentTypeById(int id) {
        return accidentTypes.get(id);
    }

    public List<Rule> getAllRules() {
        return ruleSet.stream().toList();
    }

}
