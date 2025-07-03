package Model;

public class Usuario {
    private String nome;	// nome completo do usuário
    private String login;	// login do usuário
    private String senha;	// senha do usuário

    public Usuario(String nome, String login, String senha){
        setNome(nome);
        setLogin(login);
        setSenha(senha);
    }


    //sets
    public void setNome(String nome) {
        if (nome != null) {
            this.nome = nome;
        }
    }
    public void setLogin(String login) {
        if (login != null) {
            this.login = login;
        }
    }
    public void setSenha(String senha) {
        if (senha != null) {
            this.senha = senha;
        }
    }


    //gets
    public String getNome() {
        return nome;
    }
    public String getLogin() {
        return login;
    }
    public String getSenha() {
        return senha;
    }
}
