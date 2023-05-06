package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.AccidentType;
import ru.job4j.repository.AccidentTypeMem;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeService {

    private final AccidentTypeMem accidentTypeMem;

    public List<AccidentType> getAccidentTypes() {
        return accidentTypeMem.getAccidentTypes();
    }

    public Optional<AccidentType> findAccidentTypeById(int id) {
        Optional<AccidentType> optionalAccidentType = accidentTypeMem.findAccidentTypeById(id);
        return optionalAccidentType.isEmpty() ? Optional.empty() : optionalAccidentType;
    }
}
