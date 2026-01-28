// src/gui/Acessibilidade.java
package gui;

import javax.swing.*;
import java.awt.*;

public class Acessibilidade extends JDialog {

  private final JRadioButton rbNoChange = new JRadioButton("Não mudar nada");
  private final JRadioButton rbClaro    = new JRadioButton("Tema Claro");
  private final JRadioButton rbEscuro   = new JRadioButton("Tema Escuro");

  private final JCheckBox cbKeys = new JCheckBox("Navegação por teclas (Alt+S / Alt+A / Esc)");
  private final JCheckBox cbTTS  = new JCheckBox("Leitor de texto (pt-BR) via SAPI");

  private final JSpinner spWpm = new JSpinner(new SpinnerNumberModel(180, 80, 300, 10));

  private final JButton btnSalvar   = new JButton("Salvar");
  private final JButton btnAplicar  = new JButton("Aplicar");
  private final JButton btnApFechar = new JButton("Aplicar e Fechar");
  private final JButton btnFechar   = new JButton("Fechar");
  private final JButton btnTestar   = new JButton("Testar voz");

  public Acessibilidade(Window owner){
    super(owner, "Acessibilidade", ModalityType.APPLICATION_MODAL);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setResizable(false);

    ButtonGroup bg = new ButtonGroup();
    bg.add(rbNoChange); bg.add(rbClaro); bg.add(rbEscuro);

    // estado atual → seleciona claro/escuro; caso contrário, "Não mudar nada"
    switch (A11y.getTheme()) {
      case "light" -> rbClaro.setSelected(true);
      case "dark"  -> rbEscuro.setSelected(true);
      default      -> rbNoChange.setSelected(true);
    }
    cbKeys.setSelected(A11y.isKeysEnabled());
    cbTTS.setSelected(A11y.isTTSEnabled());
    spWpm.setValue(A11y.getRateWpm());

    layoutUI();
    wireActions();
    pack();
    setLocationRelativeTo(owner);
  }

  private void layoutUI(){
    JPanel pTema = titled("Tema");
    pTema.setLayout(new BoxLayout(pTema, BoxLayout.Y_AXIS));
    pTema.add(rbNoChange); pTema.add(rbClaro); pTema.add(rbEscuro);

    JPanel pKeys = titled("Acessibilidade por Teclado");
    pKeys.setLayout(new BoxLayout(pKeys, BoxLayout.Y_AXIS));
    pKeys.add(cbKeys);
    JLabel dica = new JLabel("Atalhos: Alt+S (salvar), Alt+A (aplicar), Esc (fechar).");
    dica.setFont(dica.getFont().deriveFont(Font.ITALIC, dica.getFont().getSize()-1f));
    pKeys.add(Box.createVerticalStrut(4)); pKeys.add(dica);

    JPanel pTts = titled("Leitor de Texto (Microsoft SAPI)");
    pTts.setLayout(new FlowLayout(FlowLayout.LEFT,8,0));
    pTts.add(cbTTS);
    pTts.add(new JLabel("Velocidade (wpm):"));
    pTts.add(spWpm);
    pTts.add(btnTestar);

    JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    botoes.add(btnSalvar); botoes.add(btnAplicar); botoes.add(btnApFechar); botoes.add(btnFechar);

    JPanel root = new JPanel();
    root.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
    root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
    root.add(pTema); root.add(Box.createVerticalStrut(8));
    root.add(pKeys); root.add(Box.createVerticalStrut(8));
    root.add(pTts);  root.add(Box.createVerticalStrut(10));
    root.add(botoes);
    setContentPane(root);

    getRootPane().setDefaultButton(btnAplicar);
    A11y.wireDefaultShortcuts(getRootPane(), this::doSalvar, this::doAplicar, this::dispose);
  }

  private JPanel titled(String t){
    JPanel p = new JPanel();
    p.setBorder(BorderFactory.createTitledBorder(t));
    return p;
  }

  private void wireActions(){
    btnTestar.addActionListener(e -> A11y.speakPtBr("Teste do leitor de texto em português do Brasil.", (Integer)spWpm.getValue()));
    btnAplicar.addActionListener(e -> doAplicar());
    btnSalvar.addActionListener(e -> doSalvar());
    btnApFechar.addActionListener(e -> { doAplicar(); dispose(); });
    btnFechar.addActionListener(e -> dispose());
  }

  private void doSalvar(){
    doAplicar();
    JOptionPane.showMessageDialog(this, "Preferências salvas.");
  }

  private void doAplicar(){
    int wpm = (Integer) spWpm.getValue();
    boolean keys = cbKeys.isSelected();
    boolean tts  = cbTTS.isSelected();

    if (rbNoChange.isSelected()) {
      // não altera o tema, nem força repaint
      A11y.savePrefsKeepTheme(keys, tts, wpm);
    } else {
      String theme = rbEscuro.isSelected()? "dark" : "light";
      A11y.savePrefs(keys, tts, theme, wpm);
    }
  }
}
