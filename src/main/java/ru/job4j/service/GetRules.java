package ru.job4j.service;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public final class GetRules {

    private GetRules() { }

    public static List<Integer> ruleIdsFormRequest(HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        List<Integer> rIds = new ArrayList<>();
        for (String s : ids) {
            rIds.add(Integer.parseInt(s));
        }
        return rIds;
    }
}
