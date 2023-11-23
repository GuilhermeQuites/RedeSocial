package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin telaLogin = new TelaLogin();
            telaLogin.setOnLoginSuccess(usuario -> {
                // Aqui você pode realizar qualquer lógica necessária após o login
                // Por exemplo, fechar a tela de login e abrir a tela principal
                telaLogin.fecharTela();
                iniciarTelaPrincipal(usuario);
            });
        });
    }

    private static void iniciarTelaPrincipal(UsuarioSistema usuario) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal(usuario));
    }
}
