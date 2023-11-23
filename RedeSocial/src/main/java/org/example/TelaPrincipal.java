package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal {
    private UsuarioSistema usuario;
    private JFrame frame;

    public TelaPrincipal(UsuarioSistema usuario) {
        this.usuario = usuario;

        frame = new JFrame("Rede Social - Bem-vindo, " + usuario.getNome() + "! (ID: " + usuario.getId() + ")");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 1));

        JButton incluirAmigoButton = new JButton("Incluir Amigo");
        JButton consultarAmigosButton = new JButton("Consultar Amigos");
        JButton excluirAmigoButton = new JButton("Excluir Amigo");
        JButton logoutButton = new JButton("Logout");

        panel.add(incluirAmigoButton);
        panel.add(consultarAmigosButton);
        panel.add(excluirAmigoButton);

        frame.add(panel, BorderLayout.CENTER);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resposta = JOptionPane.showConfirmDialog(frame,
                        "Deseja realmente sair da conta?", "Confirmação",
                        JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.YES_OPTION) {
                    usuario.logout();
                    abrirTelaLogin();
                    frame.dispose();
                }
            }
        });


        panel.add(logoutButton); // Adicione o botão de logout ao painel

        incluirAmigoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaIncluirAmigo();
            }
        });

        consultarAmigosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usuario.consultarAmigos();
            }
        });

        excluirAmigoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaExcluirAmigo();
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void abrirTelaIncluirAmigo() {
        SwingUtilities.invokeLater(() -> {
            JFrame frameIncluirAmigo = new JFrame("Incluir Amigo");
            frameIncluirAmigo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameIncluirAmigo.setSize(400, 200);
            frameIncluirAmigo.setLayout(new BorderLayout());

            JPanel panelIncluirAmigo = new JPanel(new GridLayout(3, 2));
            JTextField nomeAmigoField = new JTextField();
            JTextField emailAmigoField = new JTextField();
            JButton incluirAmigoButton = new JButton("Incluir Amigo");

            panelIncluirAmigo.add(new JLabel("Nome do Amigo:"));
            panelIncluirAmigo.add(nomeAmigoField);
            panelIncluirAmigo.add(new JLabel("Email do Amigo:"));
            panelIncluirAmigo.add(emailAmigoField);
            panelIncluirAmigo.add(new JLabel()); // Espaço em branco
            panelIncluirAmigo.add(incluirAmigoButton);

            frameIncluirAmigo.add(panelIncluirAmigo, BorderLayout.CENTER);

            incluirAmigoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    UsuarioSistema amigo = UsuarioSistema.buscarUsuarioPorEmail(emailAmigoField.getText());
                    if (amigo != null) {
                        usuario.incluirAmigo(amigo);
                        JOptionPane.showMessageDialog(null, "Amigo incluído com sucesso!");
                        frameIncluirAmigo.dispose(); // Fechar a tela de inclusão após a conclusão
                    } else {
                        JOptionPane.showMessageDialog(null, "Não foi possível encontrar o usuário com o email fornecido.");
                    }
                }
            });

            frameIncluirAmigo.setVisible(true);
        });
    }

    private void abrirTelaExcluirAmigo() {
        SwingUtilities.invokeLater(() -> {
            JFrame frameExcluirAmigo = new JFrame("Excluir Amigo");
            frameExcluirAmigo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameExcluirAmigo.setSize(400, 200);
            frameExcluirAmigo.setLayout(new BorderLayout());

            JPanel panelExcluirAmigo = new JPanel(new GridLayout(2, 2));
            JTextField emailAmigoField = new JTextField();
            JButton excluirAmigoButton = new JButton("Excluir Amigo");

            panelExcluirAmigo.add(new JLabel("Email do Amigo a Excluir:"));
            panelExcluirAmigo.add(emailAmigoField);
            panelExcluirAmigo.add(new JLabel()); // Espaço em branco
            panelExcluirAmigo.add(excluirAmigoButton);

            frameExcluirAmigo.add(panelExcluirAmigo, BorderLayout.CENTER);

            excluirAmigoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    UsuarioSistema amigo = UsuarioSistema.buscarUsuarioPorEmail(emailAmigoField.getText());
                    if (amigo != null) {
                        usuario.excluirAmigo(amigo);
                        JOptionPane.showMessageDialog(null, "Amigo excluído com sucesso!");
                        frameExcluirAmigo.dispose(); // Fechar a tela de exclusão após a conclusão
                    } else {
                        JOptionPane.showMessageDialog(null, "Não foi possível encontrar o usuário com o email fornecido.");
                    }
                }
            });

            frameExcluirAmigo.setVisible(true);
        });
    }

    private void abrirTelaLogin() {
        SwingUtilities.invokeLater(() -> {
            TelaLogin telaLogin = new TelaLogin();
            telaLogin.setOnLoginSuccess(usuario -> {
                abrirTelaPrincipal(usuario);
            });
        });
    }

    private void abrirTelaPrincipal(UsuarioSistema usuario) {

        new TelaPrincipal(usuario);
    }

}
