package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class TelaLogin {
    private UsuarioSistema usuario;
    private JFrame frame;
    private JTextField emailField;
    private JPasswordField senhaField;
    private Consumer<UsuarioSistema> onLoginSuccess;

    public void setOnLoginSuccess(Consumer<UsuarioSistema> onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }

    public TelaLogin() {
        usuario = new UsuarioSistema("Nome", "email@example.com", "senha"); // Pode ser ajustado conforme necessário

        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2));
        emailField = new JTextField();
        senhaField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton cadastrarButton = new JButton("Cadastrar");

        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Senha:"));
        panel.add(senhaField);
        panel.add(loginButton);
        panel.add(cadastrarButton);
        frame.setLocationRelativeTo(null);

        frame.add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String senha = new String(senhaField.getPassword());
                usuario.login(email, senha);
                if (usuario.isAutenticado()) {
                    if (onLoginSuccess != null) {
                        onLoginSuccess.accept(usuario);
                        fecharTela(); // Fechar a tela de login após o login ser concluído
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Falha no login. Credenciais inválidas.");
                }
            }
        });

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaCadastro();
            }
        });

        frame.setVisible(true);
    }

    private void abrirTelaCadastro() {
        SwingUtilities.invokeLater(() -> {
            JFrame frameCadastro = new JFrame("Cadastro de Usuário");
            frameCadastro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameCadastro.setSize(400, 200);
            frameCadastro.setLayout(new BorderLayout());

            JPanel panelCadastro = new JPanel(new GridLayout(4, 2));
            JTextField nomeField = new JTextField();
            JTextField emailField = new JTextField();
            JPasswordField senhaField = new JPasswordField();
            JButton cadastrarButton = new JButton("Cadastrar");

            panelCadastro.add(new JLabel("Nome:"));
            panelCadastro.add(nomeField);
            panelCadastro.add(new JLabel("Email:"));
            panelCadastro.add(emailField);
            panelCadastro.add(new JLabel("Senha:"));
            panelCadastro.add(senhaField);
            panelCadastro.add(new JLabel()); // Espaço em branco
            panelCadastro.add(cadastrarButton);

            frameCadastro.add(panelCadastro, BorderLayout.CENTER);

            cadastrarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    usuario.cadastrarUsuario(nomeField.getText(), emailField.getText(), new String(senhaField.getPassword()));
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                    frameCadastro.dispose(); // Fechar a tela de cadastro após o cadastro ser concluído
                }
            });

            frameCadastro.setVisible(true);
        });
    }

    public void fecharTela() {
        frame.dispose();
    }
}
