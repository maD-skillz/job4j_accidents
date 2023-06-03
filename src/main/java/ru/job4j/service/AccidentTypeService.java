package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.AccidentType;
import ru.job4j.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeService {

    private final AccidentTypeRepository accTypeRepository;

    public List<AccidentType> getAccidentTypes() {
        return (List<AccidentType>) accTypeRepository.findAll();
    }

    public Optional<AccidentType> findAccidentTypeById(int id) {
        Optional<AccidentType> optionalAccidentType = accTypeRepository.findById(id);
        return optionalAccidentType.isEmpty() ? Optional.empty() : optionalAccidentType;
    }
}
