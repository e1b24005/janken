package oit.is.z3638.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3638.kaizi.janken.model.Janken;
import oit.is.z3638.kaizi.janken.model.Entry;
import oit.is.z3638.kaizi.janken.model.User;
import oit.is.z3638.kaizi.janken.model.UserMapper;
import oit.is.z3638.kaizi.janken.model.Match;
import oit.is.z3638.kaizi.janken.model.MatchMapper;
import oit.is.z3638.kaizi.janken.model.MatchInfo;
import oit.is.z3638.kaizi.janken.model.MatchInfoMapper;

@Controller
public class JankenController {
  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchinfoMapper;

  @Autowired
  private Entry entry;

  @GetMapping("/wait")
  public String wait(@RequestParam int id,@RequestParam String hand, Principal prin, ModelMap model) {

    String loginUserName = prin.getName();
    User me = userMapper.selectByName(loginUserName);
    User opponent = userMapper.selectById(id);

    MatchInfo matchinfo = new MatchInfo();
    matchinfo.setUser1(me.getId());
    matchinfo.setUser2(opponent.getId());
    matchinfo.setUser1Hand(hand);
    matchinfo.setActive(true);
    matchinfoMapper.insertMatchInfo(matchinfo);

    model.addAttribute("loginUserName", loginUserName);
    return "wait";
  }

  @GetMapping("/match")
  public String match(@RequestParam int id, Principal prin, ModelMap model) {
    String loginUserName = prin.getName();
    User opponent = userMapper.selectById(id);
    model.addAttribute("loginUserName", loginUserName);
    model.addAttribute("opponent", opponent);
    return "match";
  }

  @GetMapping("/fight")
  public String fight(@RequestParam int id,
      @RequestParam String hand,
      Principal prin, ModelMap model) {


    String loginUserName = prin.getName();
    User me = userMapper.selectByName(loginUserName);
    User opponent = userMapper.selectById(id);


    MatchInfo matchinfo = matchinfoMapper.selectActiveMatchInfo(me.getId());
    if (matchinfo == null) {
      MatchInfo newinfo = new MatchInfo();
      newinfo.setUser1(me.getId());
      newinfo.setUser2(opponent.getId());
      newinfo.setUser1Hand(hand);
      newinfo.setActive(true);
      matchinfoMapper.insertMatchInfo(newinfo);
      return "wait";
    }

    MatchInfo newinfo = new MatchInfo();
    newinfo.setUser1(me.getId());
    newinfo.setUser2(opponent.getId());
    newinfo.setUser1Hand(hand);
    newinfo.setActive(true);
    matchinfoMapper.insertMatchInfo(newinfo);

    Match match = new Match();
    match.setUser1(matchinfo.getUser1());
    match.setUser2(matchinfo.getUser2());
    match.setUser1Hand(matchinfo.getUser1Hand());
    match.setUser2Hand(hand);
    match.setActive(true);
    matchMapper.insertMatch(match);

    matchinfoMapper.updateIsActiveFalse(newinfo.getId());
    matchinfoMapper.updateIsActiveFalse(matchinfo.getId());

    model.addAttribute("loginUserName", loginUserName);
    model.addAttribute("opponent", opponent);
    model.addAttribute("your_hand", hand);
    return "match";
  }

  @GetMapping("/janken")
  @Transactional
  public String jankenGet(
      Principal prin,
      @RequestParam(name = "hand", required = false) String hand,
      ModelMap model) {

      ArrayList<User> Users3 = userMapper.selectAllUsers();
      model.addAttribute("Users3", Users3);

      ArrayList<Match> Matches4 = matchMapper.selectAllMatches();
      model.addAttribute("Matches4", Matches4);

      ArrayList<MatchInfo> MatchInfo1 = matchinfoMapper.selectAllMatchInfo();
      model.addAttribute("MatchInfo1", MatchInfo1);


    String loginUser = prin.getName();

    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);

    // じゃんけんの処理
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
