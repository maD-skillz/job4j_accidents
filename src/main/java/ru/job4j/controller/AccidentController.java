package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;
import ru.job4j.service.AccidentService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
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
        Set<Rule> rules = accidentService.getRulesByIds(accidentService.ruleIdsFormRequest(ids));
        Optional<AccidentType> type = accidentService.findAccidentTypeById(
                accident.getType().getId());
        if (accidentService.checkRulesAndTypes(rules, type)) {
            return "redirect:/errorPage";
        }
        accident.setType(type.get());
        accident.setRules(rules);
        accidentService.create(accident);
        return "redirect:/index";
    }

    @PostMapping("/updateAccident")
    public String updateAccident(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        if (ids == null) {
            return "redirect:/errorPage";
        }
        Set<Rule> rules = accidentService.getRulesByIds(accidentService.ruleIdsFormRequest(ids));
        Optional<AccidentType> type = accidentService.findAccidentTypeById(
                accident.getType().getId());
        if (accidentService.checkRulesAndTypes(rules, type)) {
            return "redirect:/errorPage";
        }
        accident.setRules(rules);
        accident.setType(type.get());
        accidentService.update(accident);
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident/{accId}")
    public String formUpdateTask(Model model, @PathVariable("accId") int id,
                                 @RequestParam(name = "fail", required = false)
    Boolean fail) {
        Optional<Accident> accident = accidentService.findById(id);
        if (accident.isEmpty()) {
            return "redirect:/errorPage";
        }
        model.addAttribute("accident", accident);
        model.addAttribute("types", accidentService.getAccidentTypes());
        model.addAttribute("rules", accidentService.getAllRules());
        model.addAttribute("fail", fail != null);
        return "updateAccident";
    }

}
