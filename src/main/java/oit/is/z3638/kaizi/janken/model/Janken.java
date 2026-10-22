package oit.is.z3638.kaizi.janken.model;

public class Janken {
  public static String cpuHand() {
    return "Gu"; // CPUは常に同じ手でOK
  }

  public static String judge(String you, String cpu) {
    if (you.equals(cpu)) {
      return "Draw";
    } else if ((you.equals("Gu") && cpu.equals("Choki"))
        || (you.equals("Choki") && cpu.equals("Pa"))
        || (you.equals("Pa") && cpu.equals("Gu"))) {
      return "Win";
    } else {
      return "Lose";
    }
  }
}
