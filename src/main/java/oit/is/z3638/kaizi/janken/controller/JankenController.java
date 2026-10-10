package oit.is.z3638.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JankenController {
  @GetMapping("/janken")
  public String jankenGet() {
    return "janken";
  }

  @PostMapping("/janken")
  public String janken(@RequestParam("input_name") String input_name, ModelMap model) {
    model.addAttribute("input_name", input_name);
    return "janken";
  }
}
