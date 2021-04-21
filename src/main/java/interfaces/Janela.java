package interfaces;

import Entidade.Coordenada;
import Entidade.Labirinto;
import Entidade.Pilha;
import Ferramenta.LabirintoUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Janela {

    private JFrame janela = new JFrame("Labirinto");
    private JTextArea  log = new JTextArea("", 8, 140);
    private JTextArea area = new JTextArea("", 31, 140);
    private JButton[] botao = new JButton[4];
    private JFileChooser fileChooser = new JFileChooser();
    private Labirinto labirinto;

    private class TratadorDeMouse implements ActionListener {
        private File selectedFile;

        private void trateClickEmAbrir() {
            area.setEditable(true);
            int result = fileChooser.showOpenDialog(janela);
            if(result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                log.append("\nArquivo selecionado: " + selectedFile.getAbsolutePath());

                try {
                    area.setText(LabirintoUtils.carregarArquivo(selectedFile));
                } catch (Exception ex) {
                   log.append(ex.getMessage());
                }
            }
        }

        private void trateClickEmSalvar() {
            int result = fileChooser.showSaveDialog(janela);
            if(result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                try {
                    FileWriter writer = new FileWriter(selectedFile);
                    BufferedWriter buffer = new BufferedWriter(writer);

                    String text = area.getText();
                    LabirintoUtils.verifica(text);
                    Integer linhas = area.getText().split("\n").length;
                    buffer.write(linhas.toString());
                    buffer.newLine();
                    buffer.write(text);
                    buffer.flush();
                    JOptionPane.showMessageDialog(fileChooser, "Arquivo salvo com sucesso!");

                } catch (Exception ex) {
                    log.append(ex.getMessage());
                }
            }
        }

        private void trateClickEmExecutar() {
            try {
                labirinto = LabirintoUtils.carregaString(area.getText());
                labirinto.resolve();
                area.setText(labirinto.imprimeLabirinto());
            } catch (Exception ex) {
                log.append(ex.getMessage());
            }
        }

        private void trateClickEmNovo() {
            area.setEditable(true);
            area.setText("");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            char comando = e.getActionCommand().charAt(0);

            switch (comando) {
                case 'A':this.trateClickEmAbrir();break;
                case 'N':this.trateClickEmNovo();break;
                case 'S':this.trateClickEmSalvar();break;
                case 'E':this.trateClickEmExecutar();break;
                default:break;
            }
        }
    }

    public Janela () {
        JPanel botoes = new JPanel();
        JPanel areaEdicao = new JPanel();
        JScrollPane scrollArea = new JScrollPane(areaEdicao);
        scrollArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel areaLog = new JPanel();
        JScrollPane scrollLog = new JScrollPane(areaLog);
        scrollLog.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        botoes.setLayout (new GridLayout(1,3));

        this.botao[0] = new JButton("Novo Labirinto");
        this.botao[0].addActionListener(new TratadorDeMouse());
        botoes.add(this.botao[0]);
        this.botao[1] = new JButton("Abrir Labirinto");
        this.botao[1].addActionListener(new TratadorDeMouse());
        botoes.add(this.botao[1]);
        this.botao[2] = new JButton("Salvar Arquivo de Labirinto");
        this.botao[2].addActionListener(new TratadorDeMouse());
        botoes.add(this.botao[2]);
        this.botao[3] = new JButton("Executar Labirinto");
        this.botao[3].addActionListener(new TratadorDeMouse());
        botoes.add(this.botao[3]);

        //set de painel de area de edicao
        Border borderArea = BorderFactory.createLineBorder(Color.BLACK, 1);
        this.area.setBorder(borderArea);
        this.area.setFont(new Font("Courier New", Font.BOLD, 16));
        this.area.setEditable(false);
        areaEdicao.add(this.area);

        //set de painel de area de log
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        this.log.setEditable(false);
        this.log.setBackground(Color.LIGHT_GRAY);
        this.log.setBorder(border);
        this.log.setForeground(Color.RED);
        this.log.setFont(new Font("Courier New", Font.BOLD, 14));
        areaLog.add(this.log);

        this.fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        this.fileChooser.setAcceptAllFileFilterUsed(false);
        this.fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivo de texto (.txt)", "txt"));

        this.janela.setSize (1030,655);
        this.janela.getContentPane().setLayout(new BorderLayout());

        this.janela.add(botoes,BorderLayout.NORTH);
        this.janela.add(scrollArea, BorderLayout.CENTER);
        this.janela.add(scrollLog, BorderLayout.SOUTH);

        this.janela.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.janela.setVisible(true);
    }
}
