package Model;

import java.util.ArrayList;

public class Professor {
    private String nome;
    private String login;
    private String matricula;
    private String senha;
    private ArrayList<String> disciplinas;

//    adicionar posteriormente
//    public Professor(String nome, String matricula, String senha) {
//        this.nome = nome;
//        this.matricula = matricula;
//        this.senha = senha;
//        ArrayList<Disciplina> disciplinas = new ArrayList<>();
//        isCompleto();
//    }
    public Professor(String nome, String login, String senha){
        setNome(nome);
        setLogin(login);
        setSenha(senha);
    }
    public Professor(String login, String senha){
        setLogin(login);
        setSenha(senha);
    }

    //sets
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setDisciplinas(ArrayList<String> disciplinas) {
        this.disciplinas = disciplinas;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    //gets
    public String getSenha() {
        return senha;
    }
    public String getMatricula() {
        return matricula;
    }
    public String getNome() {
        return nome;
    }
    public ArrayList<String> getDisciplinas() {
        return disciplinas;
    }
    public String getLogin() {
        return login;
    }

    //metodos
    public void addDisciplina(Disciplina disciplina){
        if(disciplina.isCompleto()){
            disciplinas.add(disciplina.getCodigo());
        }
    }
    public boolean isCompleto(){
        if(nome!=null && matricula != null && senha != null){
            return true;
        }else{
            nome = null;
            matricula = null;
            senha = null;
            return false;
        }
    }
    @Override
    public String toString() {
        return nome;
    }
}
