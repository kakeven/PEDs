package Model;


public class Aula {
    private String descricao;
    private int cargaHoraria;
    private String dataFormatada;
    private String dataNormal;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getDataFormatada() {
        return dataFormatada;
    }
    public void setDataNormal(String dataNormal) {
        this.dataNormal = dataNormal;
    }
    public String getDataNormal(){
        return dataNormal;
    }
    public void setDataFormatada(String dataFormatada) {
        this.dataFormatada = dataFormatada;
    }

    public String toString(){
        return String.format("%s %s %d", dataFormatada, descricao, cargaHoraria);
    }
}