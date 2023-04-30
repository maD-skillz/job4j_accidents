package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.service.AccidentService;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentService.getAccidentTypes());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accident.setType(accidentService.findAccidentTypeById(accident.getType().getId() - 1));
        accidentService.create(accident);
        return "redirect:/index";
    }

    @GetMapping("/updateAccident")
    public String updateAccident(@ModelAttribute Accident accident) {
        accident.setType(accidentService.findAccidentTypeById(accident.getType().getId() - 1));
        accidentService.update(accident);
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident/{accId}")
    public String formUpdateTask(Model model, @PathVariable("accId") int id) {
        model.addAttribute("accident", accidentService.findById(id));
        model.addAttribute("types", accidentService.getAccidentTypes());
        return "editAccident";
    }

}
