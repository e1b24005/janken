package oit.is.z3638.kaizi.janken.model;

public class Janken {
  public static String cpuHand() {
    return "グー"; // CPUは常に同じ手でOK
  }

  public static String judge(String you, String cpu) {
    if (you.equals(cpu)) {
      return "あいこ";
    } else if ((you.equals("グー") && cpu.equals("チョキ"))
        || (you.equals("チョキ") && cpu.equals("パー"))
        || (you.equals("パー") && cpu.equals("グー"))) {
      return "勝ち";
    } else {
      return "負け";
    }
  }
}
