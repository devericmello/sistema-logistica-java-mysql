package GUI.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/** Utilidades de UI para telas Swing. */
public final class UIUtil {

    private UIUtil() {}

    /** Maximiza a janela e define um tamanho mínimo seguro. */
    public static void maximizar(JFrame frame, int minWidth, int minHeight) {
        frame.setMinimumSize(new Dimension(minWidth, minHeight));
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
    }

    /** Ajusta todos os JSplitPane do frame (layout contínuo e divisor inicial). */
    public static void configurarSplitPanes(JFrame frame) {
        SwingUtilities.invokeLater(() -> {
            for (Component c : getAllComponents(frame.getContentPane())) {
                if (c instanceof JSplitPane sp) {
                    sp.setContinuousLayout(true);
                    sp.setResizeWeight(0.25); // 25% esquerda
                    int w = sp.getWidth() > 0 ? sp.getWidth() : frame.getWidth();
                    sp.setDividerLocation(Math.max(220, (int) (w * 0.25)));
                }
            }
        });
    }

    /** Ajusta a tabela para não cortar cabeçalhos e aplica larguras preferidas. */
    public static void ajustarTabela(JTable table, int... largurasPreferidas) {
        if (table == null) return;

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getTableHeader().setReorderingAllowed(false);

        int total = Arrays.stream(largurasPreferidas).sum();
        if (total <= 0) total = 900;
        table.setPreferredScrollableViewportSize(new Dimension(total + 30, 320));

        TableColumnModel cm = table.getColumnModel();
        int cols = cm.getColumnCount();
        for (int i = 0; i < cols; i++) {
            TableColumn col = cm.getColumn(i);
            int largura = (i < largurasPreferidas.length && largurasPreferidas[i] > 0)
                    ? largurasPreferidas[i]
                    : calcularLarguraPeloConteudo(table, i, 12, 360);
            col.setPreferredWidth(largura);
            col.setMinWidth(Math.min(80, largura));
        }

        // força re-layout do scroll
        Component parent = table.getParent();
        while (parent != null && !(parent instanceof JScrollPane)) {
            parent = parent.getParent();
        }
        if (parent instanceof JScrollPane sp) sp.revalidate();
    }

    /** Estima largura ideal: maior entre cabeçalho e n primeiras linhas. */
    private static int calcularLarguraPeloConteudo(JTable t, int colIdx, int linhasAmostra, int maxWidth) {
        var fm = t.getFontMetrics(t.getFont());
        int largura = 0;

        // cabeçalho
        String header = String.valueOf(t.getColumnModel().getColumn(colIdx).getHeaderValue());
        largura = Math.max(largura, fm.stringWidth(header) + 24);

        // conteúdo (amostra)
        int rows = Math.min(t.getRowCount(), linhasAmostra);
        for (int r = 0; r < rows; r++) {
            Object v = t.getValueAt(r, colIdx);
            String s = v == null ? "" : String.valueOf(v);
            largura = Math.max(largura, fm.stringWidth(s) + 28);
        }
        largura = Math.min(largura, maxWidth);
        largura = Math.max(largura, 80);
        return largura;
    }

    /** Retorna todos os componentes descendentes de um Container. */
    private static List<Component> getAllComponents(Container root) {
        List<Component> list = new ArrayList<>();
        for (Component c : root.getComponents()) {
            list.add(c);
            if (c instanceof Container child) {
                list.addAll(getAllComponents(child));
            }
        }
        return list;
    }
}
