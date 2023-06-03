package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.AccidentType;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface AccidentTypeRepository extends CrudRepository<AccidentType, Integer> {

}
