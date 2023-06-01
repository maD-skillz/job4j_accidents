package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Accident;
import ru.job4j.service.AccidentService;
import ru.job4j.service.AccidentTypeService;
import ru.job4j.service.RuleService;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;

    private final AccidentTypeService accidentTypeService;

    private final RuleService ruleService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model,
                                     @RequestParam(name = "fail", required = false)
    Boolean fail) {
        model.addAttribute("types", accidentTypeService.getAccidentTypes());
        model.addAttribute("rules", ruleService.getAllRules());
        model.addAttribute("fail", fail != null);
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident,
                       @RequestParam(name = "typeId") int typeId,
                       @RequestParam(name = "rIds") String[] rids) {
        accidentService.create(accident, typeId, rids);
        return "redirect:/index";
    }

    @PostMapping("/updateAccident")
    public String updateAccident(@ModelAttribute Accident accident,
                                 @RequestParam(name = "rIds") String[] rids) {
        accidentService.update(accident, rids);
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
        model.addAttribute("types", accidentTypeService.getAccidentTypes());
        model.addAttribute("rules", ruleService.getAllRules());
        model.addAttribute("fail", fail != null);
        return "updateAccident";
    }

}
