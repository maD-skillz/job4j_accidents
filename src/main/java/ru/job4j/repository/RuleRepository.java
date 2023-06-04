package ru.job4j.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Rule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface RuleRepository extends CrudRepository<Rule, Integer> {

    @Query("from Rule r where r.id IN(:ids)")
    List<Rule> getSelectedRules(@Param("ids") List<Integer> ids);

    @Override
    List<Rule> findAll();

}
