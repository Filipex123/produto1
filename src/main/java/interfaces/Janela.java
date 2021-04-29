package interfaces;

import entidade.Labirinto;
import ferramenta.LabirintoUtils;

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

    private final JFrame janela = new JFrame("Labirinto");
    private final JTextArea log = new JTextArea("", 8, 140);
    private final JTextArea area = new JTextArea("", 31, 140);
    private final JFileChooser fileChooser = new JFileChooser();

    private class TratadorDeMouse implements ActionListener {
        private File selectedFile;

        private void trateClickEmAbrir() {
            log.setForeground(Color.BLUE);
            log.setText("");
            area.setEditable(true);
            int result = fileChooser.showOpenDialog(janela);
            if (result == JFileChooser.APPROVE_OPTION) {
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
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                try {
                    FileWriter writer = new FileWriter(selectedFile);
                    BufferedWriter buffer = new BufferedWriter(writer);

                    String text = area.getText();
                    LabirintoUtils.verifica(text, true);
                    Integer linhas = area.getText().split("\n").length;
                    buffer.write(linhas.toString());
                    buffer.newLine();
                    buffer.write(text);
                    buffer.flush();
                    JOptionPane.showMessageDialog(fileChooser, "Arquivo salvo com sucesso!");

                } catch (Exception ex) {
                    log.setForeground(Color.RED);
                    log.setText("");
                    log.append(ex.getMessage());
                }
            }
        }

        private void trateClickEmExecutar() {
            try {
                Labirinto labirinto = LabirintoUtils.carregaString(area.getText());
                labirinto.resolve();
                area.setText(labirinto.imprimeLabirinto());
            } catch (Exception ex) {
                log.setForeground(Color.RED);
                log.setText("");
                log.append(ex.getMessage());
            }
        }

        private void trateClickEmNovo() {
            area.setEditable(true);
            area.setText("");
            log.setText("");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            char comando = e.getActionCommand().charAt(0);

            switch (comando) {
                case 'A':
                    this.trateClickEmAbrir();
                    break;
                case 'N':
                    this.trateClickEmNovo();
                    break;
                case 'S':
                    this.trateClickEmSalvar();
                    break;
                case 'E':
                    this.trateClickEmExecutar();
                    break;
                default:
                    break;
            }
        }
    }

    public Janela() {
        JPanel botoes = new JPanel();
        JPanel areaEdicao = new JPanel();
        JScrollPane scrollArea = new JScrollPane(areaEdicao);
        scrollArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel areaLog = new JPanel();
        JScrollPane scrollLog = new JScrollPane(areaLog);
        scrollLog.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        botoes.setLayout(new GridLayout(1, 3));

        JButton[] botao = new JButton[4];
        botao[0] = new JButton("Novo Labirinto");
        botao[0].addActionListener(new TratadorDeMouse());
        botoes.add(botao[0]);
        botao[1] = new JButton("Abrir Labirinto");
        botao[1].addActionListener(new TratadorDeMouse());
        botoes.add(botao[1]);
        botao[2] = new JButton("Salvar Arquivo de Labirinto");
        botao[2].addActionListener(new TratadorDeMouse());
        botoes.add(botao[2]);
        botao[3] = new JButton("Executar Labirinto");
        botao[3].addActionListener(new TratadorDeMouse());
        botoes.add(botao[3]);

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
        this.log.setForeground(Color.BLUE);
        this.log.setFont(new Font("Courier New", Font.BOLD, 14));
        areaLog.add(this.log);

        this.fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        this.fileChooser.setAcceptAllFileFilterUsed(false);
        this.fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivo de texto (.txt)", "txt"));

        this.janela.setSize(1030, 655);
        this.janela.getContentPane().setLayout(new BorderLayout());

        this.janela.add(botoes, BorderLayout.NORTH);
        this.janela.add(scrollArea, BorderLayout.CENTER);
        this.janela.add(scrollLog, BorderLayout.SOUTH);

        this.janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.janela.setVisible(true);
    }
}
