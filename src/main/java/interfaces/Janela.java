package interfaces;

import bd.dbos.LabirintoDBO;
import entidade.LabirintoEntity;
import ferramenta.LabirintoUtils;
import network.entidade.*;
import network.servidor.UsuarioConexao;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Janela {

    private final JFrame janela = new JFrame("Editor de Labirinto");
    private final JTextArea log = new JTextArea("", 8, 140);
    private final JTextArea area = new JTextArea("", 31, 140);
    private final JFileChooser fileChooser = new JFileChooser();
    private final JButton[] botao = new JButton[5];

    private String identificador = null;
    private UsuarioConexao conexao;

    private class VerificaConexao extends Thread implements Runnable {
        public VerificaConexao() throws Exception{
            while(true){
                sleep(2000);
                if(conexao == null){
                    conexao = getConexaoNuvem();
                    if(conexao != null){
                        JOptionPane.showMessageDialog(
                                null,
                                "O servidor foi ligado.\nAs funções ABRIR e SALVAR também funcionarão em nuvem.",
                                "Servidor Online",
                                JOptionPane.NO_OPTION);
                    }
                }
                else {
                    try{
                        Comunicado comunicado = null;
                        comunicado = (Comunicado)conexao.espie();
                        if(comunicado instanceof ComunicadoDeDesligamento){
                            conexao = null;
                            JOptionPane.showMessageDialog(
                                    null,
                                    "O servidor foi desligado.\nAs funções ABRIR e SALVAR funcionarão apenas localmente.",
                                    "Servidor Offline",
                                    JOptionPane.NO_OPTION);
                        }
                    } catch (Exception ex){
                        conexao = null;
                        JOptionPane.showMessageDialog(
                                null,
                                "O servidor foi desligado.\nAs funções ABRIR e SALVAR funcionarão apenas localmente.",
                                "Servidor Offline",
                                JOptionPane.NO_OPTION);
                    }
                }
            }
        }
    }

    private class TratadorDeMouse implements ActionListener {
        private File selectedFile;

        private void trateClickEmAbrir() {
            String[] opcoes = conexao != null ? new String[]{"Local", "Em Nuvem"} : new String[]{"Local"};
            int escolha = JOptionPane.showOptionDialog(
                    null,
                    "Onde deseja abrir o labirinto?",
                    "Onde Abrir?",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

            botao[2].setEnabled(true);
            botao[4].setEnabled(false);
            log.setForeground(Color.BLUE);
            log.setText("");
            area.setEditable(true);
            if(escolha == 0){
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
            } else if (escolha == 1) {
                identificador = JOptionPane.showInputDialog("Digite seu identificador/email");
                if(identificador != null) {
                    try {
                        if (validaIdentificacao(identificador)) {
                            throw new Exception("Identificar Inválido");
                        }

                        UsuarioConexao conexao = getConexaoNuvem();

                        conexao.receba(new PedidoLabirintos(identificador));

                        Comunicado comunicado = null;
                        do {
                            comunicado = (Comunicado)conexao.espie();
                        }
                        while (!(comunicado instanceof RespostaLabirintos));
                        RespostaLabirintos res = (RespostaLabirintos)comunicado;

                        ArrayList<String> labs = new ArrayList<>();

                        res.getLabirintos().forEach(item -> {
                            labs.add(item.getNome());
                        });

                        JComboBox<String> labOptions = new JComboBox<>(labs.toArray(new String[0]));

                        JOptionPane.showConfirmDialog(null, labOptions, "Escolha ai", JOptionPane.DEFAULT_OPTION);

                        String labTexto = res.getLabirintos().get(labOptions.getSelectedIndex()).getConteudo()
                                .replaceAll("[0-9]", "")
                                .replaceFirst("\n", "");

                        area.setText(labTexto);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                janela,
                                "Identificar Inválido.",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE);
                        log.setForeground(Color.RED);
                        log.setText("");
                        log.append(ex.getMessage());
                    }
                }
            }
        }

        private void trateClickEmSalvar() {
            try{
                String text = area.getText();
                LabirintoUtils.verifica(text);
                Integer linhas = area.getText().split("\n").length;
                String[] opcoes = conexao != null ? new String[]{"Local", "Em Nuvem"} : new String[]{"Local"};
                int escolha = JOptionPane.showOptionDialog(
                        null,
                        "Onde deseja salvar o labirinto?",
                        "Onde Salvar?",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

                if(escolha == 0){
                    int result = fileChooser.showSaveDialog(janela);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        selectedFile = fileChooser.getSelectedFile();
                        FileWriter writer = new FileWriter(selectedFile);
                        BufferedWriter buffer = new BufferedWriter(writer);

                        buffer.write(linhas.toString());
                        buffer.newLine();
                        buffer.write(text);
                        buffer.flush();
                        JOptionPane.showMessageDialog(null, "Labirinto salvo com sucesso!");
                    }
                }else if (escolha == 1){
                    identificador = getIndentificador();
                    if (validaIdentificacao(identificador)) {
                        throw new Exception("Identificador Inválido");
                    }
                    String nomeLab = JOptionPane.showInputDialog("Digite o nome do labirinto:");
                    if (validaIdentificacao(nomeLab)) {
                        throw new Exception("Nome Inválido");
                    }

                    UsuarioConexao conexao = getConexaoNuvem();

                    String textoSalvar = linhas + "\n" + text;
                    conexao.receba(new PedidoSalvamento(new LabirintoDBO(nomeLab, identificador, textoSalvar)));
                    JOptionPane.showMessageDialog(null, "Labirinto salvo com sucesso!");
                }

            } catch (Exception ex){
                JOptionPane.showMessageDialog(
                        janela,
                        "Não foi possível salvar.\nConfira o Log de Erros.",
                        "Erro" ,
                        JOptionPane.ERROR_MESSAGE);
                log.setForeground(Color.RED);
                log.setText("");
                log.append(ex.getMessage());
            }

        }

        private void trateClickEmExecutar() {
            try {
                botao[2].setEnabled(false);
                LabirintoEntity labirintoEntity = LabirintoUtils.carregaString(area.getText());
                log.setText(labirintoEntity.resolve());
                area.setText(labirintoEntity.imprimeLabirinto());
                botao[4].setEnabled(true);
            } catch (Exception ex) {
                log.setForeground(Color.RED);
                log.setText("");
                log.append(ex.getMessage());
            }
        }

        private void trateClickEmNovo() {
            botao[2].setEnabled(true);
            botao[4].setEnabled(false);
            area.setEditable(true);
            area.setText("");
            log.setText("");
        }

        private void trateClickEmRestaurar(){
            String text = area.getText().replaceAll("\\*", " ");
            area.setText(text);
            botao[2].setEnabled(true);
            botao[4].setEnabled(false);
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
                case 'R':
                    this.trateClickEmRestaurar();
                    break;
                default:
                    break;
            }
        }
    }

    public Janela() throws Exception {
        this.conexao = this.getConexaoNuvem();
        if(conexao == null){
            JOptionPane.showMessageDialog(
                    null,
                    "O servidor está offline.\nAs funções ABRIR e SALVAR só funcionarão localmente.",
                    "Servidor Offline",
                    JOptionPane.WARNING_MESSAGE);
        }

        JPanel botoes = new JPanel();
        JPanel areaEdicao = new JPanel();
        JScrollPane scrollArea = new JScrollPane(areaEdicao);
        scrollArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel areaLog = new JPanel();
        JScrollPane scrollLog = new JScrollPane(areaLog);
        scrollLog.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        botoes.setLayout(new GridLayout(1, 3));

        botao[0] = new JButton("Novo Labirinto");
        botao[0].addActionListener(new TratadorDeMouse());
        botoes.add(botao[0]);
        botao[1] = new JButton("Abrir Labirinto");
        botao[1].addActionListener(new TratadorDeMouse());
        botoes.add(botao[1]);
        botao[3] = new JButton("Executar Labirinto");
        botao[3].addActionListener(new TratadorDeMouse());
        botoes.add(botao[3]);
        botao[2] = new JButton("Salvar Labirinto");
        botao[2].addActionListener(new TratadorDeMouse());
        botao[2].setEnabled(false);
        botoes.add(botao[2]);
        botao[4] = new JButton("Restaurar Labirinto");
        botao[4].addActionListener(new TratadorDeMouse());
        botao[4].setEnabled(false);
        botoes.add(botao[4]);

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
        this.log.setLayout(new GridBagLayout());
        this.log.setFont(new Font("Courier New", Font.BOLD, 14));
        areaLog.add(this.log);

        this.fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        this.fileChooser.setAcceptAllFileFilterUsed(false);
        this.fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivo de texto (.txt)", "txt"));

        this.janela.setSize(1030, 655);
        this.janela.getContentPane().setLayout(new BorderLayout());

        this.janela.add(botoes, BorderLayout.NORTH);
        this.janela.add(scrollArea, BorderLayout.CENTER);
        scrollLog.setPreferredSize(new Dimension(300,180));
        this.janela.add(scrollLog, BorderLayout.SOUTH);

        this.janela.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    conexao.getConexao().close();
                    System.exit(0);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.janela.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.janela.setVisible(true);
        this.janela.pack();

        Thread thread = new Thread(new VerificaConexao());
        thread.start();


    }

    private UsuarioConexao getConexaoNuvem(){
        Socket conexao = null;
        ObjectOutputStream transmissor = null;
        ObjectInputStream receptor = null;
        UsuarioConexao servidor = null;
        try {
            conexao = new Socket("localhost", 2021);
            transmissor = new ObjectOutputStream(conexao.getOutputStream());
            receptor = new ObjectInputStream(conexao.getInputStream());
            servidor = new UsuarioConexao(conexao, receptor, transmissor);
            return servidor;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    private boolean validaIdentificacao(String valor){
        return valor.replaceAll("\\s", "").equals("");
    }

    private String getIndentificador(){
        if(this.identificador != null)
            return this.identificador;

        return JOptionPane.showInputDialog("Digite seu identificador/email");
    }
}
