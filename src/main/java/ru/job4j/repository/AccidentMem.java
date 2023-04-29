package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private final Map<Integer, Accident> store = new ConcurrentHashMap<>();

    private final AtomicInteger ids = new AtomicInteger(1);

    public Accident create(Accident accident) {
        int id = ids.getAndIncrement();
        accident.setId(id);
        store.put(id, accident);
        return accident;
    }

    public Collection<Accident> getAll() {
       return store.values();
    }

    public Accident findById(int id) {
       return store.get(id);
    }
}
