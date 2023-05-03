package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.service.AccidentService;

@Controller
@AllArgsConstructor
public class IndexController {

    private final AccidentService accidentService;

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("user", "Petr Arsentev");
        model.addAttribute("accidents", accidentService.findAll());
        model.addAttribute("fail", fail != null);
        return "index";
    }

    @GetMapping("/errorPage")
    public String errorPage() {
        return "errorPage";
    }

}
