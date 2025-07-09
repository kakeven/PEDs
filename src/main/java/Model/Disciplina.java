
package Model;

import java.util.ArrayList;

public class Disciplina {
    private String nome;
    private String codigo;
    private int cargaTeorica;
    private int cargaPratica;
    private int cargaEaD;
    private int cargaExtensao;
    private int cargaTotal;
    private Professor professor;
    private String estruturaCurricular;
    private String obrigatoriedade;
    private ArrayList<String> preRequisito;
    private ArrayList<String> coRequisito;
    private String regimeDeOferta;
    private ArrayList<String> equivalencias;

    public Disciplina(){
        super();
        ArrayList<String> preRequisito = new ArrayList<>();
        ArrayList<String> coRequisito = new ArrayList<>();
        ArrayList<String> equivalencias = new ArrayList<>();
        cargaTeorica = 0;
        cargaPratica = 0;
        cargaEaD = 0;
        cargaExtensao = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {return codigo;}

    public void setCodigo(String codigo) {this.codigo = codigo;}

    public String getCargaTeorica() {
        if(cargaTeorica == 0){
            return "-";
        } else {
            return String.format("%d",cargaTeorica);
        }
    }

    public void setCargaTeorica(int cargaTeorica) {
        cargaTotal+=cargaTeorica;
        this.cargaTeorica = cargaTeorica;
    }

    public int getCargaPratica() {return cargaPratica;}

    public void setCargaPratica(int cargaPratica) {
        cargaTotal+=cargaPratica;
        this.cargaPratica = cargaPratica;
    }

    public String getCargaEaD() {
        if(cargaEaD == 0){
            return "-";
        } else {
            return String.format("%d",cargaEaD);
        }
    }

    public void setCargaEaD(int cargaEaD) {
        cargaTotal+=cargaEaD;
        this.cargaEaD = cargaEaD;
    }

    public String getCargaExtensao() {
        if(cargaExtensao == 0){
            return "-";
        } else {
            return String.format("%d",cargaExtensao);
        }
    }

    public void setCargaExtensao(int cargaExtensao) {
        cargaTotal+=cargaExtensao;
        this.cargaExtensao = cargaExtensao;
    }

    public String getEstruturaCurricular() {return estruturaCurricular;}

    public void setEstruturaCurricular(String estruturaCurricular) {this.estruturaCurricular = estruturaCurricular;}

    public String getObrigatoriedade() {return obrigatoriedade;}

    public void setObrigatoriedade(String obrigatoriedade) {this.obrigatoriedade = obrigatoriedade;}

    public String getRegimeDeOferta() {return regimeDeOferta;}

    public void setRegimeDeOferta(String regimeDeOferta) {this.regimeDeOferta = regimeDeOferta;}

    public Professor getProfessor() {return professor;}

    public void setProfessor(Professor professor) {this.professor = professor;}

    public int getCargaTotal(){
        return cargaEaD + cargaTeorica + cargaPratica + cargaExtensao;
    }

    public void addPreRequisito(String preRequisito){this.preRequisito.add(preRequisito);}

    public ArrayList<String> getPreRequisito(){return preRequisito;}

    public void addCoRequisito(String coRequisito){
        this.coRequisito.add(coRequisito);
    }

    public ArrayList<String> getCoRequisito(){
        return coRequisito;
    }

    public void addEquivalencias(String equivalencias){
        this.equivalencias.add(equivalencias);
    }

    public ArrayList<String> getEquivalencias(){
        return equivalencias;
    }

    public boolean isCompleto(){
        return (codigo != null && cargaTotal>0 && estruturaCurricular!= null && regimeDeOferta != null && obrigatoriedade != null);
    }

    @Override
    public String toString() {
        return String.format("%s %20s", codigo, nome);
    }
}