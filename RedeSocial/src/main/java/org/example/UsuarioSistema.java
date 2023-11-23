package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;  // Importação necessária para SQLException
import java.util.ArrayList;
import java.util.List;


public class UsuarioSistema extends Usuario implements GerenciadorAmigos {
    private List<Usuario> amigos;
    private boolean autenticado;
    private String nomeBanco; // Adiciona uma variável para armazenar o nome do banco
    private int id; // Certifique-se de que você tem um atributo id na classe UsuarioSistema



    public UsuarioSistema(String nome, String email, String senha) {
        super(nome, email, senha);
        amigos = new ArrayList<>();
        this.id = 0;
    }

    @Override
    public void login(String email, String senha) {
        try {
            Connection conexao = ConexaoBD.conectar();
            String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
            try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                pstmt.setString(1, email);
                pstmt.setString(2, senha);
                ResultSet resultSet = pstmt.executeQuery();

                if (resultSet.next()) {
                    id = resultSet.getInt("id"); // Atribui o ID do banco ao usuário
                    // Usuário autenticado com sucesso
                    nomeBanco = resultSet.getString("nome"); // Armazena o nome do banco
                    System.out.println("Login bem-sucedido. Bem-vindo, " + nomeBanco + "!");
                    setAutenticado(true);
                } else {
                    // Credenciais inválidas
                    System.out.println("Falha no login. Credenciais inválidas.");
                    setAutenticado(false);
                }
            } finally {
                ConexaoBD.fecharConexao(conexao);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Erro ao realizar login.");
            setAutenticado(false);
        }
    }
    public int getId() {

        return id;
    }


    public void logout() {
        id = 0; // ou qualquer valor que você use para representar nenhum ID
        nomeBanco = null;
        setAutenticado(false);
    }


    public void cadastrarUsuario(String nome, String email, String senha) {
        try {
            Connection conexao = ConexaoBD.conectar();
            String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                pstmt.setString(1, nome);
                pstmt.setString(2, email);
                pstmt.setString(3, senha);
                pstmt.executeUpdate();
                System.out.println("Usuário cadastrado com sucesso!");
            } finally {
                ConexaoBD.fecharConexao(conexao);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Erro ao cadastrar usuário.");
        }
    }


    @Override
    public void incluirAmigo(Usuario amigo) {
        amigos.add(amigo);
        System.out.println("Amigo adicionado: " + amigo.getNome());

        // Inserir na tabela amizade
        try {
            Connection conexao = ConexaoBD.conectar();
            String sql = "INSERT INTO amizade (usuario_id1, usuario_id2) VALUES (?, ?)";
            try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                pstmt.setInt(1, this.getId());
                pstmt.setInt(2, amigo.getId());
                pstmt.executeUpdate();
                System.out.println("Amizade registrada no banco de dados.");
            } finally {
                ConexaoBD.fecharConexao(conexao);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Erro ao registrar amizade no banco de dados.");
        }
    }



    @Override
        public void consultarAmigos() {
            if (amigos.isEmpty()) {
                System.out.println("Você não tem amigos ainda.");
            } else {
                System.out.println("Seus amigos:");
                for (Usuario amigo : amigos) {
                System.out.println(amigo.getNome());
                }
            }
        }

    @Override
    public void excluirAmigo(Usuario amigo) {
        if (amigos.contains(amigo)) {
            amigos.remove(amigo);
            System.out.println("Amigo removido: " + amigo.getNome());
        } else {
            System.out.println("Este usuário não é seu amigo.");
        }
    }

    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }
    public boolean isAutenticado() {
        return autenticado;
    }


    public static UsuarioSistema buscarUsuarioPorEmail(String email) {
        try {
            Connection conexao = ConexaoBD.conectar();
            String sql = "SELECT * FROM usuario WHERE email = ?";
            try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                pstmt.setString(1, email);
                ResultSet resultSet = pstmt.executeQuery();

                if (resultSet.next()) {
                    // Usuário encontrado
                    String nome = resultSet.getString("nome");
                    String senha = resultSet.getString("senha");
                    UsuarioSistema usuarioEncontrado = new UsuarioSistema(nome, email, senha);
                    // Defina outros atributos conforme necessário
                    return usuarioEncontrado;
                } else {
                    // Usuário não encontrado
                    return null;
                }
            } finally {
                ConexaoBD.fecharConexao(conexao);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Erro ao buscar usuário por email.");
            return null;
        }
    }

    public String getNome(){
        return nomeBanco;
    }



}
