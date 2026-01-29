// src/gui/A11y.java
package gui;

import javax.accessibility.AccessibleContext;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.prefs.Preferences;
import java.util.concurrent.*;

public final class A11y {

  // ===== prefs =====
  private static final Preferences PREFS = Preferences.userRoot().node("tcc/logistica/a11y");
  private static final String K_THEME="theme";   // "system"|"light"|"dark"
  private static final String K_KEYS ="keys";    // boolean
  private static final String K_TTS  ="tts";     // boolean
  private static final String K_WPM  ="wpm";     // 80..300

  // ===== estado =====
  private static String initialLaFClass;
  private static AWTEventListener GLOBAL;
  private static boolean readerOn=false;

  // TTS assíncrono/cancelável
  private static final ExecutorService TTS_EXEC =
      Executors.newSingleThreadExecutor(r -> { Thread t=new Thread(r,"tts"); t.setDaemon(true); return t; });
  private static volatile Process ttsProc;

  // Hover intent + cooldown
  private static final int HOVER_DELAY_MS = 350;
  private static final int COOLDOWN_MS    = 2500;
  private static javax.swing.Timer hoverTimer;
  private static Component hoverComp;
  private static int hoverX, hoverY;
  private static Component lastComp; private static String lastText; private static long lastAt;

  private A11y(){}

  // ===== API =====
  public static void initLAF(){
    LookAndFeel laf = UIManager.getLookAndFeel();
    initialLaFClass = laf!=null? laf.getClass().getName() : UIManager.getCrossPlatformLookAndFeelClassName();
    switchTheme(getTheme());
    updateUIAllWindows();
    syncReaderState();
  }
  public static void initAtStartup(){ initLAF(); }

  public static String  getTheme()      { return PREFS.get(K_THEME,"system"); }
  public static boolean isKeysEnabled() { return PREFS.getBoolean(K_KEYS,true); }
  public static boolean isTTSEnabled()  { return PREFS.getBoolean(K_TTS,false); }
  public static int     getRateWpm()    { return Math.max(80,Math.min(300,PREFS.getInt(K_WPM,180))); }
  public static boolean isDark()        { return "dark".equals(getTheme()); }

  /** Salva tudo e ALTERA tema. */
  public static void savePrefs(boolean keys, boolean tts, String theme, int wpm){
    PREFS.putBoolean(K_KEYS, keys);
    PREFS.putBoolean(K_TTS,  tts);
    PREFS.put(K_THEME, theme);
    PREFS.putInt(K_WPM, wpm);
    switchTheme(theme);
    updateUIAllWindows();
    syncReaderState();
  }

  /** Salva teclas/TTS/velocidade e NÃO muda tema nem UI. */
  public static void savePrefsKeepTheme(boolean keys, boolean tts, int wpm){
    PREFS.putBoolean(K_KEYS, keys);
    PREFS.putBoolean(K_TTS,  tts);
    PREFS.putInt(K_WPM, wpm);
    syncReaderState();
  }

  public static void switchTheme(String theme){
    try { UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); } catch(Exception ignored){}
    for (Window w: Window.getWindows()) if (w!=null) applyColorsDeep(w);
    Palette pal = paletteFor(theme);
    UIManager.put("control", pal.bg);
    UIManager.put("text", pal.fg);
    UIManager.put("nimbusLightBackground", pal.bg);
    UIManager.put("nimbusBase", pal.accent);
    UIManager.put("Table.selectionBackground", pal.selection);
  }

  public static void updateUIAllWindows(){
    for (Window w: Window.getWindows()){
      if (w==null) continue;
      SwingUtilities.updateComponentTreeUI(w);
      w.pack();
    }
  }

  /** Alt+S, Alt+A, Esc */
  public static void wireDefaultShortcuts(JRootPane root, Runnable onSave, Runnable onApply, Runnable onClose){
    if (!isKeysEnabled()) return;
    InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap am = root.getActionMap();
    im.put(KeyStroke.getKeyStroke("alt S"), "SAVE");
    im.put(KeyStroke.getKeyStroke("alt A"), "APPLY");
    im.put(KeyStroke.getKeyStroke("ESCAPE"), "CLOSE");
    am.put("SAVE",  new AbstractAction(){ public void actionPerformed(java.awt.event.ActionEvent e){ if(onSave!=null) onSave.run(); }});
    am.put("APPLY", new AbstractAction(){ public void actionPerformed(java.awt.event.ActionEvent e){ if(onApply!=null) onApply.run(); }});
    am.put("CLOSE", new AbstractAction(){ public void actionPerformed(java.awt.event.ActionEvent e){ if(onClose!=null) onClose.run(); }});
  }

  // ===== TTS SAPI assíncrono =====
  public static boolean speakPtBr(String text){ return speakPtBr(text, getRateWpm()); }
  public static boolean speakPtBr(String text, int wpm){
    if (text==null || text.isBlank() || !isTTSEnabled()) return false;
    String osName = System.getProperty("os.name").toLowerCase();
    if (!osName.contains("win")) return false;

    int rate = mapRateWin(wpm);
    String ps =
      "Add-Type -AssemblyName System.Speech;" +
      "$sp=New-Object System.Speech.Synthesis.SpeechSynthesizer;" +
      "$v=($sp.GetInstalledVoices()|?{ $_.VoiceInfo.Culture.Name -eq 'pt-BR'}|select -First 1).VoiceInfo.Name;" +
      "if($v){$sp.SelectVoice($v)};" +
      "$sp.Rate=" + rate + ";$sp.Volume=100;" +
      "$sp.Speak([Console]::In.ReadToEnd());";

    TTS_EXEC.submit(() -> {
      Process old = ttsProc;
      if (old!=null && old.isAlive()) old.destroyForcibly();
      try {
        ProcessBuilder pb = new ProcessBuilder("powershell","-NoProfile","-Command", ps);
        pb.redirectErrorStream(true);
        Process p = pb.start();
        ttsProc = p;
        try (OutputStream out = p.getOutputStream()) {
          out.write(text.getBytes(StandardCharsets.UTF_8));
        }
        p.waitFor();
        if (ttsProc == p) ttsProc = null;
      } catch (Exception ignored) {}
    });
    return true;
  }
  private static int mapRateWin(int wpm){
    int clamped = Math.max(80, Math.min(300, wpm));
    return Math.max(-10, Math.min(10, Math.round((clamped - 180) / 20f)));
  }

  // ===== leitor por hover/foco com atraso e cooldown =====
  private static void syncReaderState(){
    boolean desired = isTTSEnabled();
    if (desired && GLOBAL == null) startReader();
    else if (!desired && GLOBAL != null) stopReader();
  }
  private static void startReader(){
    hoverTimer = new javax.swing.Timer(HOVER_DELAY_MS, e -> speakLeafUnderPointer());
    hoverTimer.setRepeats(false);

    GLOBAL = ev -> {
      if (!isTTSEnabled()) return;
      if (ev instanceof MouseEvent me) {
        int id = me.getID();
        if (id==MouseEvent.MOUSE_MOVED || id==MouseEvent.MOUSE_ENTERED) {
          hoverComp = me.getComponent();
          hoverX = me.getX(); hoverY = me.getY();
          hoverTimer.restart();
        }
      } else if (ev instanceof FocusEvent fe) {
        if (fe.getID()==FocusEvent.FOCUS_GAINED) maybeSpeak(fe.getComponent(), null);
      }
    };
    Toolkit.getDefaultToolkit().addAWTEventListener(
        GLOBAL, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.FOCUS_EVENT_MASK);
    readerOn=true;
  }
  private static void stopReader(){
    Toolkit.getDefaultToolkit().removeAWTEventListener(GLOBAL);
    GLOBAL=null; readerOn=false;
    if (hoverTimer!=null) hoverTimer.stop();
  }

  private static void speakLeafUnderPointer(){
    if (hoverComp==null) return;
    Component leaf = SwingUtilities.getDeepestComponentAt(hoverComp, hoverX, hoverY);
    maybeSpeak(leaf, new Point(hoverX, hoverY));
  }

  private static void maybeSpeak(Component c, Point pInSource){
    if (c==null) return;
    String text = extractTextAt(c, pInSource);
    if (text==null || text.isBlank()) return;

    long now = System.currentTimeMillis();
    if (c==lastComp && text.equals(lastText) && now-lastAt < COOLDOWN_MS) return;

    lastComp=c; lastText=text; lastAt=now;
    speakPtBr(text, getRateWpm());
  }

  private static String extractTextAt(Component c, Point pInSource){
    Point p = pInSource;
    if (p!=null && c != hoverComp) p = SwingUtilities.convertPoint(hoverComp, pInSource, c);

    if (c instanceof JTable t && p!=null){
      int r = t.rowAtPoint(p), col = t.columnAtPoint(p);
      if (r>=0 && col>=0) {
        Object v = t.getValueAt(r,col);
        return v!=null? v.toString() : null;
      }
    }
    if (c instanceof JList<?> l && p!=null){
      int idx = l.locationToIndex(p);
      if (idx>=0){
        Object v = l.getModel().getElementAt(idx);
        return v!=null? v.toString() : null;
      }
    }
    return extractText(c);
  }

  private static String extractText(Component c){
    if (c instanceof JLabel jl)            return clean(jl.getText());
    if (c instanceof AbstractButton b)     return clean(b.getText());
    if (c instanceof JTextComponent tc) {
      String sel = tc.getSelectedText();
      return clean((sel!=null && !sel.isBlank())? sel : tc.getText());
    }
    if (c instanceof JComponent jc) {
      Object forced = jc.getClientProperty("a11y.read");
      if (forced!=null) return clean(forced.toString());
      String tip = jc.getToolTipText();
      if (tip!=null)   return clean(tip);
    }
    AccessibleContext ac = c.getAccessibleContext();
    if (ac!=null) {
      String n = clean(ac.getAccessibleName());        if (n!=null && !n.isBlank()) return n;
      String d = clean(ac.getAccessibleDescription()); if (d!=null && !d.isBlank()) return d;
    }
    return null;
  }
  private static String clean(String s){ return s==null? null : s.replaceAll("<[^>]+>","").trim(); }

  // ===== paleta =====
  private record Palette(Color bg, Color bgAlt, Color inputBg, Color fg, Color btnBg, Color btnFg, Color accent, Color selection){}
  private static Palette paletteFor(String theme){
    boolean dark = "dark".equals(theme);
    Color bg   = dark ? new Color(0x1E1F22) : new Color(0xFFFFFF);
    Color bg2  = dark ? new Color(0x2B2D31) : new Color(0xF2F4F7);
    Color inp  = dark ? new Color(0x30323A) : new Color(0xFFFFFF);
    Color fg   = dark ? new Color(0xE8E8E8) : new Color(0x1B1B1B);
    Color btnB = dark ? new Color(0x3A3D44) : new Color(0xE6E8EB);
    Color btnF = fg;
    Color accent = new Color(0x0072B2);
    Color selection = dark ? brighten(accent, 0.35f) : dim(accent, 0.10f);
    return new Palette(bg,bg2,inp,fg,btnB,btnF,accent,selection);
  }
  private static void applyColorsDeep(Component c){
    Palette pal = paletteFor(getTheme());
    c.setForeground(pal.fg);
    if (c instanceof JPanel || c instanceof JTabbedPane ||
        c instanceof JDesktopPane || c instanceof JLayeredPane ||
        c instanceof JSplitPane || c instanceof JToolBar) c.setBackground(pal.bg);
    if (c instanceof JScrollPane sp){ sp.setBackground(pal.bg); if (sp.getViewport()!=null) sp.getViewport().setBackground(pal.bg); }
    if (c instanceof JTextField tf){ tf.setBackground(pal.inputBg); tf.setCaretColor(pal.fg); }
    if (c instanceof JTextArea ta){ ta.setBackground(pal.inputBg); ta.setCaretColor(pal.fg); }
    if (c instanceof JPasswordField pf){ pf.setBackground(pal.inputBg); pf.setCaretColor(pal.fg); }
    if (c instanceof JButton b){ b.setBackground(pal.btnBg); b.setForeground(pal.btnFg); b.setMargin(new Insets(4,12,4,12)); }
    if (c instanceof AbstractButton ab){ ab.setForeground(pal.fg); }
    if (c instanceof JTable t){
      t.setBackground(pal.bg); t.setForeground(pal.fg);
      t.setSelectionBackground(pal.selection); t.setSelectionForeground(pal.fg);
      if (t.getTableHeader()!=null){
        t.getTableHeader().setOpaque(true);
        t.getTableHeader().setBackground(pal.bgAlt);
        t.getTableHeader().setForeground(pal.fg);
      }
    }
    if (c instanceof JList<?> l){
      l.setBackground(pal.bg); l.setForeground(pal.fg);
      l.setSelectionBackground(pal.selection); l.setSelectionForeground(pal.fg);
    }
    if (c instanceof Container ct)
      for (Component ch: ct.getComponents()) applyColorsDeep(ch);
  }
  private static Color brighten(Color c, float f){ int r=(int)Math.min(255,c.getRed()+255*f), g=(int)Math.min(255,c.getGreen()+255*f), b=(int)Math.min(255,c.getBlue()+255*f); return new Color(r,g,b); }
  private static Color dim      (Color c, float f){ int r=(int)Math.max(0,c.getRed()-255*f), g=(int)Math.max(0,c.getGreen()-255*f), b=(int)Math.max(0,c.getBlue()-255*f); return new Color(r,g,b); }
}
