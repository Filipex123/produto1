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

/**
 * Classe responsável por montar a interface gráfica
 */
public class Janela {

    private final JFrame janela = new JFrame("Editor de Labirinto");
    private final JTextArea areaLog = new JTextArea("", 8, 140);
    private final JTextArea areaTexto = new JTextArea("", 31, 140);
    private final JFileChooser exploradorDeArquivos = new JFileChooser();
    private final JButton[] botoesMenu = new JButton[5];

    private String identificador = null;
    private UsuarioConexao conexao;

    /**
     * Thread responsável por verificar conexão com o servidor,
     * caso seja ligado em meio a execução ou desligado abruptamente
     */
    private class VerificaConexao extends Thread implements Runnable {
        public VerificaConexao() throws Exception {
            while (true) {
                sleep(2000);
                if (conexao == null) {
                    conexao = getConexaoNuvem();
                    if (conexao != null) {
                        JOptionPane.showMessageDialog(
                                null,
                                "O servidor foi ligado.\nAs funções ABRIR e SALVAR também funcionarão em nuvem.",
                                "Servidor Online",
                                JOptionPane.NO_OPTION);
                    }
                } else {
                    try {
                        Comunicado comunicado = null;
                        comunicado = (Comunicado) conexao.espie();
                        if (comunicado instanceof ComunicadoDeDesligamento) {
                            conexao = null;
                            JOptionPane.showMessageDialog(
                                    null,
                                    "O servidor foi desligado.\nAs funções ABRIR e SALVAR funcionarão apenas localmente.",
                                    "Servidor Offline",
                                    JOptionPane.NO_OPTION);
                        }
                    } catch (Exception ex) {
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

    /**
     * Tratador de clique nos botões do menu da aplicação
     */
    private class TratadorDeMouse implements ActionListener {
        private File selectedFile;

        private void trateClickEmAbrir() {
            String[] opcoes = conexao != null ? new String[]{"Local", "Em Nuvem"} : new String[]{"Local"};
            int escolha = JOptionPane.showOptionDialog(
                    null,
                    "Onde deseja abrir o labirinto?",
                    "Onde Abrir?",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

            botoesMenu[2].setEnabled(true);
            botoesMenu[4].setEnabled(false);
            areaLog.setForeground(Color.BLUE);
            areaLog.setText("");
            areaTexto.setEditable(true);
            if (escolha == 0) {
                int result = exploradorDeArquivos.showOpenDialog(janela);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = exploradorDeArquivos.getSelectedFile();
                    areaLog.append("\nArquivo selecionado: " + selectedFile.getAbsolutePath());
                    try {
                        areaTexto.setText(LabirintoUtils.carregarArquivo(selectedFile));
                    } catch (Exception ex) {
                        areaLog.append(ex.getMessage());
                    }
                }
            } else if (escolha == 1) {
                identificador = JOptionPane.showInputDialog("Digite seu identificador/email");
                if (identificador != null) {
                    try {
                        if (validaIdentificacao(identificador)) {
                            throw new Exception("Identificar Inválido");
                        }

                        UsuarioConexao conexao = getConexaoNuvem();

                        conexao.receba(new PedidoLabirintos(identificador));

                        Comunicado comunicado = null;
                        do {
                            comunicado = (Comunicado) conexao.espie();
                        }
                        while (!(comunicado instanceof RespostaLabirintos));
                        RespostaLabirintos res = (RespostaLabirintos) comunicado;

                        ArrayList<String> labs = new ArrayList<>();

                        res.getLabirintos().forEach(item -> {
                            labs.add(item.getNome());
                        });

                        JComboBox<String> labOptions = new JComboBox<>(labs.toArray(new String[0]));

                        JOptionPane.showConfirmDialog(null, labOptions, "Escolha ai", JOptionPane.DEFAULT_OPTION);

                        String labTexto = res.getLabirintos().get(labOptions.getSelectedIndex()).getConteudo()
                                .replaceAll("[0-9]", "")
                                .replaceFirst("\n", "");

                        areaTexto.setText(labTexto);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                janela,
                                "Identificar Inválido.",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE);
                        areaLog.setForeground(Color.RED);
                        areaLog.setText("");
                        areaLog.append(ex.getMessage());
                    }
                }
            }
        }

        private void trateClickEmSalvar() {
            try {
                String text = areaTexto.getText();
                LabirintoUtils.verifica(text);
                Integer linhas = areaTexto.getText().split("\n").length;
                String[] opcoes = conexao != null ? new String[]{"Local", "Em Nuvem"} : new String[]{"Local"};
                int escolha = JOptionPane.showOptionDialog(
                        null,
                        "Onde deseja salvar o labirinto?",
                        "Onde Salvar?",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

                if (escolha == 0) {
                    int result = exploradorDeArquivos.showSaveDialog(janela);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        selectedFile = exploradorDeArquivos.getSelectedFile();
                        FileWriter writer = new FileWriter(selectedFile);
                        BufferedWriter buffer = new BufferedWriter(writer);

                        buffer.write(linhas.toString());
                        buffer.newLine();
                        buffer.write(text);
                        buffer.flush();
                        JOptionPane.showMessageDialog(null, "Labirinto salvo com sucesso!");
                    }
                } else if (escolha == 1) {
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

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        janela,
                        "Não foi possível salvar.\nConfira o Log de Erros.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                areaLog.setForeground(Color.RED);
                areaLog.setText("");
                areaLog.append(ex.getMessage());
            }

        }

        private void trateClickEmExecutar() {
            try {
                botoesMenu[2].setEnabled(false);
                LabirintoEntity labirintoEntity = LabirintoUtils.carregaString(areaTexto.getText());
                areaLog.setText(labirintoEntity.resolve());
                areaTexto.setText(labirintoEntity.imprimeLabirinto());
                botoesMenu[4].setEnabled(true);
            } catch (Exception ex) {
                areaLog.setForeground(Color.RED);
                areaLog.setText("");
                areaLog.append(ex.getMessage());
            }
        }

        private void trateClickEmNovo() {
            botoesMenu[2].setEnabled(true);
            botoesMenu[4].setEnabled(false);
            areaTexto.setEditable(true);
            areaTexto.setText("");
            areaLog.setText("");
        }

        private void trateClickEmRestaurar() {
            String text = areaTexto.getText().replaceAll("\\*", " ");
            areaTexto.setText(text);
            botoesMenu[2].setEnabled(true);
            botoesMenu[4].setEnabled(false);
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

    /**
     * Contrutor da interface
     *
     * @throws Exception exceção de abertura da aplicação
     */
    public Janela() throws Exception {
        this.conexao = this.getConexaoNuvem();
        if (conexao == null) {
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

        this.botoesMenu[0] = new JButton("Novo Labirinto");
        this.botoesMenu[0].addActionListener(new TratadorDeMouse());
        botoes.add(this.botoesMenu[0]);
        this.botoesMenu[1] = new JButton("Abrir Labirinto");
        this.botoesMenu[1].addActionListener(new TratadorDeMouse());
        botoes.add(this.botoesMenu[1]);
        this.botoesMenu[3] = new JButton("Executar Labirinto");
        this.botoesMenu[3].addActionListener(new TratadorDeMouse());
        botoes.add(this.botoesMenu[3]);
        this.botoesMenu[2] = new JButton("Salvar Labirinto");
        this.botoesMenu[2].addActionListener(new TratadorDeMouse());
        this.botoesMenu[2].setEnabled(false);
        botoes.add(this.botoesMenu[2]);
        this.botoesMenu[4] = new JButton("Restaurar Labirinto");
        this.botoesMenu[4].addActionListener(new TratadorDeMouse());
        this.botoesMenu[4].setEnabled(false);
        botoes.add(this.botoesMenu[4]);

        //set de painel de area de edicao
        Border borderArea = BorderFactory.createLineBorder(Color.BLACK, 1);
        this.areaTexto.setBorder(borderArea);
        this.areaTexto.setFont(new Font("Courier New", Font.BOLD, 16));
        this.areaTexto.setEditable(false);
        areaEdicao.add(this.areaTexto);

        //set de painel de area de log
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        this.areaLog.setEditable(false);
        this.areaLog.setBackground(Color.LIGHT_GRAY);
        this.areaLog.setBorder(border);
        this.areaLog.setForeground(Color.BLUE);
        this.areaLog.setLayout(new GridBagLayout());
        this.areaLog.setFont(new Font("Courier New", Font.BOLD, 14));
        areaLog.add(this.areaLog);

        this.exploradorDeArquivos.setCurrentDirectory(new File(System.getProperty("user.home")));
        this.exploradorDeArquivos.setAcceptAllFileFilterUsed(false);
        this.exploradorDeArquivos.setFileFilter(new FileNameExtensionFilter("Arquivo de texto (.txt)", "txt"));

        this.janela.setSize(1030, 655);
        this.janela.getContentPane().setLayout(new BorderLayout());

        this.janela.add(botoes, BorderLayout.NORTH);
        this.janela.add(scrollArea, BorderLayout.CENTER);
        scrollLog.setPreferredSize(new Dimension(300, 180));
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

    /**
     * Método para criar conexão com o servidor
     *
     * @return UsuarioConexao entidade de conexão com o servidor
     */
    private UsuarioConexao getConexaoNuvem() {
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

    /**
     * Método de validação de campos de entrada
     *
     * @param valor valor a ser validado
     * @return resultado da validação
     */
    private boolean validaIdentificacao(String valor) {
        return valor.replaceAll("\\s", "").equals("");
    }

    /**
     * Verifica se há um identificador
     *
     * @return o identificador
     */
    private String getIndentificador() {
        if (this.identificador != null)
            return this.identificador;

        return JOptionPane.showInputDialog("Digite seu identificador/email");
    }
}
