package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.model.Rule;
import ru.job4j.service.AccidentService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
public class IndexController {

    private final AccidentService accidentService;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("user", "Petr Arsentev");
        model.addAttribute("accidents", accidentService.findAll());
        return "index";
    }

}
