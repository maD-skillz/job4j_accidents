package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.Accident;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface AccidentRepository extends CrudRepository<Accident, Integer> {

    @Override
    List<Accident> findAll();

}
