package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.repository.*;

import java.util.*;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentJdbcTemplate accidentMem;

    public Optional<Accident> create(Accident accident, int typeId, String[] rids) {
        Optional<Accident> optionalAccident = accidentMem.save(accident, typeId, rids);
        return optionalAccident.isEmpty() ? Optional.empty() : optionalAccident;
    }

    public void update(Accident accident, String[] rids) {
        accidentMem.update(accident, rids);
    }

    public Collection<Accident> findAll() {
        return accidentMem.getAll();
    }

    public Optional<Accident> findById(int id) {
        Optional<Accident> optionalAccident = accidentMem.findById(id);
        return optionalAccident.isEmpty() ? Optional.empty() : optionalAccident;
    }

    public void delete(int id) {
        accidentMem.delete(id);
    }

}
