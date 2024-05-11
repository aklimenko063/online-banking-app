package org.javaacademy.onlinebankingapp.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.onlinebankingapp.config.BankProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class BankController {
    private final BankProperties bankProperties;

    @GetMapping
    public String getIndex(Model model) {
        model.addAttribute("bankName", bankProperties.getName());
        return "index.html";
    }
}
