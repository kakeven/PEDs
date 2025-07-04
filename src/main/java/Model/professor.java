package Model;

import java.util.ArrayList;

public class Professor {
    private String nome;
    private String matricula;
    private String senha;
    private ArrayList<String> disciplinas;

    public Professor(String nome, String matricula, String senha) {
        this.nome = nome;
        this.matricula = matricula;
        this.senha = senha;
        ArrayList<Disciplina> disciplinas = new ArrayList<>();
        isCompleto();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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
