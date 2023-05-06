package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.AccidentType;

import java.util.*;
@Repository
public class AccidentTypeMem {

    private final Map<Integer, AccidentType> accidentTypes = new HashMap();

    {
        accidentTypes.put(1, new AccidentType(1, "Две машины"));
        accidentTypes.put(2, new AccidentType(2, "Машина и человек"));
        accidentTypes.put(3, new AccidentType(3, "Машина и велосипед"));
    }

    public List<AccidentType> getAccidentTypes() {
        return new ArrayList<>(accidentTypes.values());
    }

    public Optional<AccidentType> findAccidentTypeById(int id) {
        return Optional.ofNullable(accidentTypes.get(id));
    }
}
