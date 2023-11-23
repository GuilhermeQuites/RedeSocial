package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroUsuario {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Cadastro de Usuário");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLayout(new BorderLayout());

            JPanel panel = new JPanel(new GridLayout(4, 2));
            JTextField nomeField = new JTextField();
            JTextField emailField = new JTextField();
            JPasswordField senhaField = new JPasswordField();
            JButton cadastrarButton = new JButton("Cadastrar");

            panel.add(new JLabel("Nome:"));
            panel.add(nomeField);
            panel.add(new JLabel("Email:"));
            panel.add(emailField);
            panel.add(new JLabel("Senha:"));
            panel.add(senhaField);
            panel.add(new JLabel()); // Espaço em branco
            panel.add(cadastrarButton);

            frame.add(panel, BorderLayout.CENTER);

            cadastrarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cadastrarUsuario(nomeField.getText(), emailField.getText(), new String(senhaField.getPassword()));
                }
            });

            frame.setVisible(true);
        });
    }

    private static void cadastrarUsuario(String nome, String email, String senha) {
        try {
            Connection conexao = ConexaoBD.conectar();
            String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                pstmt.setString(1, nome);
                pstmt.setString(2, email);
                pstmt.setString(3, senha);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
            } finally {
                ConexaoBD.fecharConexao(conexao);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário.");
        }
    }
}
