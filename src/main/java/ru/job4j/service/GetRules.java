package ru.job4j.service;


import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class GetRules {

    private GetRules() { }

    public static List<Integer> ruleIdsFormRequest(String[] ids) {
        List<Integer> rIds = new ArrayList<>();
        for (String s : ids) {
            rIds.add(Integer.parseInt(s));
        }
        return rIds;
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
