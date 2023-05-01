package ru.job4j.service;


import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class GetRules {

    private GetRules() { }

    public static List<Integer> ruleIdsFormRequest(String[] ids) {
        return Arrays.stream(ids).map(Integer::parseInt).collect(Collectors.toList());
    }

    public static boolean check(Set<Rule> rules, AccidentType type) {
        if (rules == null) {
            return true;
        }
        if (type == null) {
            return true;
        }
        return false;
    }

}
