package com.eleks.academy.pharmagator.controllers.ui;

import com.eleks.academy.pharmagator.services.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class NavigationController {

    private final MedicineService medicineService;

    @RequestMapping({"", "/", "index", "index.html", "home", "homepage"})
    public String getHomePage() {
        return "index";
    }

    @RequestMapping("/medicine")
    public String getMedicines(Model model) {
        model.addAttribute("meds", medicineService.findAll());
        return "medicine";
    }

    @RequestMapping("/price")
    public String getPrices() {
        return "price";
    }

}
