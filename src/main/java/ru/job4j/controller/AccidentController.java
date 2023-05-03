package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;
import ru.job4j.service.AccidentService;
import ru.job4j.service.GetRules;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model,
                                     @RequestParam(name = "fail", required = false)
    Boolean fail) {
        model.addAttribute("types", accidentService.getAccidentTypes());
        model.addAttribute("rules", accidentService.getAllRules());
        model.addAttribute("fail", fail != null);
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        if (ids == null) {
            return "redirect:/errorPage";
        }
        Set<Rule> rules = accidentService.getRulesByIds(GetRules.ruleIdsFormRequest(ids));
        AccidentType type = accidentService.findAccidentTypeById(accident.getType().getId());
        if (GetRules.check(rules, type)) {
            return "redirect:/errorPage";
        }
        accident.setRules(rules);
        accident.setType(type);
        accidentService.create(accident);
        return "redirect:/index";
    }

    @PostMapping("/updateAccident")
    public String updateAccident(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        if (ids == null) {
            return "redirect:/errorPage";
        }
        Set<Rule> rules = accidentService.getRulesByIds(GetRules.ruleIdsFormRequest(ids));
        AccidentType type = accidentService.findAccidentTypeById(accident.getType().getId());
        if (GetRules.check(rules, type)) {
            return "redirect:/errorPage";
        }
        accident.setRules(rules);
        accident.setType(type);
        accidentService.update(accident);
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident/{accId}")
    public String formUpdateTask(Model model, @PathVariable("accId") int id,
                                 @RequestParam(name = "fail", required = false)
    Boolean fail) {
        Accident accident = accidentService.findById(id);
        if (accident == null) {
            return "redirect:/errorPage";
        }
        model.addAttribute("accident", accident);
        model.addAttribute("types", accidentService.getAccidentTypes());
        model.addAttribute("rules", accidentService.getAllRules());
        model.addAttribute("fail", fail != null);
        return "updateAccident";
    }

}
