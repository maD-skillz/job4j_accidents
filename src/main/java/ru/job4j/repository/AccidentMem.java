package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentMem {

    private final ConcurrentHashMap<Integer, Accident> store = new ConcurrentHashMap();

    public Accident create(Accident accident) {
        store.put(accident.getId(), accident);
        return accident;
    }

    public Collection<Accident> getAll() {
       return store.values();
    }

    public Accident findById(int id) {
       return store.get(id);
    }
}
