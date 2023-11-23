package org.example;
public abstract class Usuario {

    private String nome;
    private String email;
    private String senha;
    private int id ;

    public Usuario(String nome, String email, String senha) {

        this.nome = nome;
        this.email = email;
        this.senha = senha;



    }
    public abstract int getId();



    public String getNome() {
        return nome;
    }

    public abstract void login(String email, String senha);
}
