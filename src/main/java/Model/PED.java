package Model;


import java.util.ArrayList;

public class PED {
    private String unidade;
    private Professor professor;
    private Disciplina disciplina;
    private String curso;
    private String semestre;
    private String justificativa;
    private String ementa;
    private String objetivos;
    private ArrayList<Aula> aulas;
    private int cargaHoraria;
    private String metodologia;
    private String atividadesDiscentes;
    private String sistemaDeAvaliacao;
    private String bibliografia;
    private String obrigatoriedade;

    public PED(){
        ArrayList<Aula> aulas = new ArrayList<>();
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getObrigatoriedade() {
        return obrigatoriedade;
    }

    public void setObrigatoriedade(String obrigatoriedade) {
        this.obrigatoriedade = obrigatoriedade;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public String getEmenta() {
        return ementa;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public String getMetodologia() {
        return metodologia;
    }

    public void setMetodologia(String metodologia) {
        this.metodologia = metodologia;
    }

    public String getSistemaDeAvaliacao() {
        return sistemaDeAvaliacao;
    }

    public void setSistemaDeAvaliacao(String sistemaDeAvaliacao) {
        this.sistemaDeAvaliacao = sistemaDeAvaliacao;
    }

    public String getAtividadesDiscentes() {
        return atividadesDiscentes;
    }

    public void setAtividadesDiscentes(String atividadesDiscentes) {
        this.atividadesDiscentes = atividadesDiscentes;
    }

    public String getBibliografia() {
        return bibliografia;
    }

    public void setBibliografia(String bibliografia) {
        this.bibliografia = bibliografia;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
        cargaHoraria = disciplina.getCargaTotal();
    }

    public void addAula(Aula aula){
        if((cargaHoraria-aula.getCargaHoraria()) >= 0){
            cargaHoraria-= aula.getCargaHoraria();;
            aulas.add(aula);
        }

    }
    public ArrayList<Aula> getAula(){
        return aulas;
    }

    @Override
    public String toString() {
        return String.format("PED de %s - %s", disciplina, semestre);
    }
}