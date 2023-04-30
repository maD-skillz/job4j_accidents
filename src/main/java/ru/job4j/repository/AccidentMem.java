package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private final Map<Integer, Accident> store = new ConcurrentHashMap<>();

    private final List<AccidentType> accidentTypes = new ArrayList<>();

    {
        accidentTypes.add(new AccidentType(1, "Две машины"));
        accidentTypes.add(new AccidentType(2, "Машина и человек"));
        accidentTypes.add(new AccidentType(3, "Машина и велосипед"));
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

}
