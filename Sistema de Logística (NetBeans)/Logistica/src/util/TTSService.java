// src/util/TTSService.java  (ou no mesmo pacote que você usa)
package util;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public final class TTSService {
  private TTSService(){}

  /** Fala pt-BR usando o TTS nativo do SO. Retorna true se OK. */
  public static boolean speakPtBr(String text, Integer wpm) {
    if (text == null || text.isBlank()) return false;
    String os = System.getProperty("os.name").toLowerCase();
    try {
      if (os.contains("win")) return speakWin(text, wpm);
      if (os.contains("mac")) return speakMac(text, wpm);
      return speakLinux(text); // spd-say
    } catch (Exception e) {
      return false;
    }
  }

  // ---------- Windows: SAPI (nenhuma instalação extra) ----------
  private static boolean speakWin(String text, Integer wpm) throws Exception {
    int rate = mapRateWin(wpm); // SAPI: -10..10
    String ps =
      "Add-Type -AssemblyName System.Speech;" +
      "$sp=New-Object System.Speech.Synthesis.SpeechSynthesizer;" +
      "$v=($sp.GetInstalledVoices()|?{ $_.VoiceInfo.Culture.Name -eq 'pt-BR'}|select -First 1).VoiceInfo.Name;" +
      "if($v){$sp.SelectVoice($v)};" +
      "$sp.Rate=" + rate + ";$sp.Volume=100;" +
      "$sp.Speak([Console]::In.ReadToEnd());";
    ProcessBuilder pb = new ProcessBuilder("powershell","-NoProfile","-Command", ps);
    pb.redirectErrorStream(true);
    Process p = pb.start();
    try (OutputStream os = p.getOutputStream()) {
      os.write(text.getBytes(StandardCharsets.UTF_8));
    }
    p.waitFor();
    return p.exitValue() == 0;
  }
  private static int mapRateWin(Integer wpm) {
    if (wpm == null) return 0;
    int clamped = Math.max(80, Math.min(300, wpm));
    return Math.max(-10, Math.min(10, Math.round((clamped - 180) / 20f)));
  }

  // ---------- macOS: say ----------
  private static boolean speakMac(String text, Integer wpm) throws Exception {
    String rate = String.valueOf(wpm != null ? wpm : 180);
    ProcessBuilder pb = new ProcessBuilder("say","-v","Luciana","-r",rate,text);
    pb.redirectErrorStream(true);
    Process p = pb.start();
    p.waitFor();
    return p.exitValue() == 0;
  }

  // ---------- Linux: Speech Dispatcher ----------
  private static boolean speakLinux(String text) throws Exception {
    ProcessBuilder pb = new ProcessBuilder("spd-say","-l","pt-BR",text);
    pb.redirectErrorStream(true);
    Process p = pb.start();
    p.waitFor();
    return p.exitValue() == 0;
  }
}
