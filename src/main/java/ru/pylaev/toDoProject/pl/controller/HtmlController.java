package ru.pylaev.toDoProject.pl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pylaev.toDoProject.bll.UserInputService;
import ru.pylaev.toDoProject.pl.view.View;

@Controller
public class HtmlController {
    private final View view;
    private final UserInputService userInputService;

    @Autowired
    public HtmlController(View view, UserInputService userInputService) {
        this.view = view;
        this.userInputService = userInputService;
    }

    @GetMapping
    public String show (Model model) {
        model.addAttribute("view", view);
        return "home";
    }

    @PostMapping
    public String processUserInput (@RequestParam String userInput) {
        UiHandler.processUserInput(userInput, view, userInputService);
        return "redirect:/";
    }
}
