package com.agag.currency.controller;

import com.agag.currency.service.CurrencyService;
import com.agag.currency.model.ConversionRecord;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;
    private List<String> getCurrencyList() {
        return List.of("USD", "MXN", "EUR", "GBP", "JPY", "CAD", "AUD", "CHF", "BRL", "CNY");
    }

    @GetMapping("/")
    public String showHome(Model model) {
        model.addAttribute("currencies", getCurrencyList());
        return "index";
    }

    @GetMapping("/convert")
    public String convertCurrency(
            @RequestParam("amount") double amount,
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            Model model,
            HttpSession session) {

        Double result = currencyService.convert(amount, from.toUpperCase(), to.toUpperCase());

        List<ConversionRecord> history = (List<ConversionRecord>) session.getAttribute("history");
        if(history == null) {
            history = new ArrayList<>();
        }

        if (result != null) {
            model.addAttribute("result", result);
            history.add(new ConversionRecord(amount, from.toUpperCase(), to.toUpperCase(), result));
            session.setAttribute("history", history);
        } else {
            model.addAttribute("result", "Error retrieving exchange rate.");
        }

        model.addAttribute("history", history);
        model.addAttribute("currencies", getCurrencyList());
        model.addAttribute("fromSelected", from.toUpperCase());
        model.addAttribute("toSelected", to.toUpperCase());

        return "index";
    }

    @GetMapping("/clear-history")
    public String clearHistory(HttpSession session) {
        session.invalidate(); // Clear all in session
        return "redirect:/";
    }
}
