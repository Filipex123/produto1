package interfaces;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Janela {
    private JFrame janela = new JFrame("Labirinto");
    private JTextArea  log = new JTextArea("Área de Log", 8, 89);
    private JTextArea area = new JTextArea("", 27, 89);
    private JButton[] botao = new JButton[4];

    public Janela () {
        JPanel botoes = new JPanel();
        JPanel areaEdicao = new JPanel();
        JScrollPane scroll = new JScrollPane(areaEdicao);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel areaLog = new JPanel();

        botoes.setLayout (new GridLayout(1,3));

        this.botao[0] = new JButton("Novo Labirinto");
        botoes.add(this.botao[0]);
        this.botao[1] = new JButton("Abrir Labirinto");
        botoes.add(this.botao[1]);
        this.botao[2] = new JButton("Salvar Arquivo de Labirinto");
        botoes.add(this.botao[2]);
        this.botao[3] = new JButton("Executar Labirinto");
        botoes.add(this.botao[3]);

        Border borderArea = BorderFactory.createLineBorder(Color.BLACK, 1);
        this.area.setBorder(borderArea);
        areaEdicao.add(this.area);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        this.log.setEditable(false);
        this.log.setBackground(Color.GRAY);
        this.log.setBorder(border);
        areaLog.add(this.log);

        this.janela.setSize (1030,655);
        this.janela.getContentPane().setLayout(new BorderLayout());

        this.janela.add(botoes,BorderLayout.NORTH);
        this.janela.add(scroll, BorderLayout.CENTER);
        this.janela.add(areaLog, BorderLayout.SOUTH);

        this.janela.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.janela.setVisible(true);
    }
}
