package GUI;

import javax.swing.*;

public class TelaComVoltar extends JFrame {
    protected JFrame telaAnterior;

    public TelaComVoltar(JFrame telaAnterior) {
        this.telaAnterior = telaAnterior;
    }

    protected JButton criarBotaoVoltar() {
        JButton btn = new JButton("Voltar");
        btn.addActionListener(e -> {
            this.dispose();
            telaAnterior.setVisible(true);
        });
        return btn;
    }
}
