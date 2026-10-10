package oit.is.z3638.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3638.kaizi.janken.model.Janken;

@Controller
public class JankenController {
  @GetMapping("/janken")
  public String jankenGet(@RequestParam(name = "hand", required = false) String hand, ModelMap model) {
    if (hand != null) {
      String cpu = Janken.cpuHand();
      String result = Janken.judge(hand, cpu);
      model.addAttribute("your_hand", hand);
      model.addAttribute("cpu_hand", cpu);
      model.addAttribute("result", result);
    }
    return "janken";
  }

  @PostMapping("/janken")
  public String janken(@RequestParam("input_name") String input_name, ModelMap model) {
    model.addAttribute("input_name", input_name);
    return "janken";
  }
}
