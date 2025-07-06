package Model;


import java.util.ArrayList;

public class Professor {
    private String nome;
    private String login;
    private String senha;
    private ArrayList<String> disciplinas;


    public Professor(String nome, String login, String senha){
        setNome(nome);
        setLogin(login);
        setSenha(senha);
    }
    public Professor(String login, String senha){
        setLogin(login);
        setSenha(senha);
    }

    public String getLogin(){
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void addDisciplina(Disciplina disciplina){
        if(disciplina.isCompleto()){
            disciplinas.add(disciplina.getCodigo());
        }
    }


    public boolean isCompleto(){
        if(nome!=null && login != null && senha != null){
            return true;
        }else{
            nome = null;
            login = null;
            senha = null;
            return false;
        }
    }

    @Override
    public String toString() {
        return nome;
    }
}
