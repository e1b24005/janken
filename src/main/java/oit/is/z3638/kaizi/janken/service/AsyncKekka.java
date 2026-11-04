package oit.is.z3638.kaizi.janken.service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z3638.kaizi.janken.model.Match;
import oit.is.z3638.kaizi.janken.model.MatchMapper;

@Service
public class AsyncKekka {
  @Autowired
  MatchMapper matchMapper;

  @Async
  public void kekka(SseEmitter emitter) {
    try {
      while (true) {
        boolean dbUpdated = false;
        Match match = null;

        for (Match m : matchMapper.selectAllMatches()) {
          if (m.isActive()) {
            match = m;
            break;
          }
        }

        if (match != null) {
          String result = "Match: id " + match.getId()
              + " user1: " + match.getUser1()
              + " user2: " + match.getUser2()
              + " user1Hand: " + match.getUser1Hand()
              + " user2Hand: " + match.getUser2Hand();

          emitter.send(SseEmitter.event().data(result));
          dbUpdated = true;
        }

        if (dbUpdated) {
          matchMapper.updateIsActiveFalse(match.getId());
          break;
        }

        TimeUnit.SECONDS.sleep(1);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } finally {
      emitter.complete();
    }
  }
}
