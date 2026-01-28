package util;

import javax.accessibility.AccessibleContext;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;

public final class ScreenReaderLite {
    private static AWTEventListener GLOBAL;
    private static Component lastComp;
    private static String lastText;
    private static long lastSpeakAt;
    private static final long DEBOUNCE_MS = 1500;

    private ScreenReaderLite(){}

    public static void start() {
        if (GLOBAL != null) return;
        GLOBAL = event -> {
            
            if (event instanceof MouseEvent me) {
                int id = me.getID();
                if (id == MouseEvent.MOUSE_ENTERED || id == MouseEvent.MOUSE_MOVED)
                    maybeSpeak(me.getComponent());
            } else if (event instanceof FocusEvent fe) {
                if (fe.getID() == FocusEvent.FOCUS_GAINED)
                    maybeSpeak(fe.getComponent());
            }
        };
        Toolkit.getDefaultToolkit().addAWTEventListener(
            GLOBAL,
            AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.FOCUS_EVENT_MASK
        );
    }

    public static void stop() {
        if (GLOBAL == null) return;
        Toolkit.getDefaultToolkit().removeAWTEventListener(GLOBAL);
        GLOBAL = null;
    }

    private static void maybeSpeak(Component c) {
        String text = extractText(c);
        if (text == null || text.isBlank()) return;

        long now = System.currentTimeMillis();
        if (c == lastComp && text.equals(lastText) && now - lastSpeakAt < DEBOUNCE_MS) return; // evita repetir

        lastComp = c; lastText = text; lastSpeakAt = now;
        TTSService.speakPtBr(text, 180);
    }

    private static String extractText(Component c) {
        // 1) textos diretos
        if (c instanceof JLabel jl) return clean(jl.getText());
        if (c instanceof AbstractButton b) return clean(b.getText());
        if (c instanceof JTextComponent tc) {
            String sel = tc.getSelectedText();
            return clean((sel != null && !sel.isBlank()) ? sel : tc.getText());
        }

        // 2) dica forçada pelo desenvolvedor
        if (c instanceof JComponent jc) {
            Object forced = jc.getClientProperty("a11y.read");
            if (forced != null) return clean(forced.toString());
            String tip = jc.getToolTipText();
            if (tip != null) return clean(tip);
        }

        // 3) nomes acessíveis
        AccessibleContext ac = c.getAccessibleContext();
        if (ac != null) {
            String n = clean(ac.getAccessibleName());
            if (n != null) return n;
            String d = clean(ac.getAccessibleDescription());
            if (d != null) return d;
        }
        return null;
    }

    private static String clean(String s) {
        if (s == null) return null;
        return s.replaceAll("<[^>]+>", "").trim(); // tira HTML de JLabel
    }
}
