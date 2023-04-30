package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.repository.AccidentMem;

import java.util.Collection;

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

}
