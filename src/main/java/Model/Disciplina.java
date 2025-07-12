
package Model;

public class Disciplina {
    private String nome;
    private String codigo;
    private int cargaTeorica;
    private int cargaPratica;
    private int cargaEaD;
    private int cargaExtensao;
    private int cargaTotal;
    private String estruturaCurricular;
    private String preRequisito;
    private String coRequisito;
    private String regimeDeOferta;
    private String equivalencias;

    public Disciplina(){
        super();

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
            return "---";
        } else {
            return String.format("%dh",cargaTeorica);
        }
    }

    public void setCargaTeorica(int cargaTeorica) {
        this.cargaTeorica = cargaTeorica;
    }

    public String getCargaPratica() {
        if(cargaPratica == 0){
            return "---";
        } else {
            return String.format("%dh",cargaPratica);
        }
    }

    public void setCargaPratica(int cargaPratica) {
        this.cargaPratica = cargaPratica;
    }

    public String getCargaEaD() {
        if(cargaEaD == 0){
            return "---";
        } else {
            return String.format("%dh",cargaEaD);
        }
    }

    public void setCargaEaD(int cargaEaD) {
        this.cargaEaD = cargaEaD;
    }

    public String getCargaExtensao() {
        if(cargaExtensao == 0){
            return "---";
        } else {
            return String.format("%d",cargaExtensao);
        }
    }

    public void setCargaExtensao(int cargaExtensao) {
        this.cargaExtensao = cargaExtensao;
    }

    public String getEstruturaCurricular() {return estruturaCurricular;}

    public void setEstruturaCurricular(String estruturaCurricular) {this.estruturaCurricular = estruturaCurricular;}

    public String getRegimeDeOferta() {return regimeDeOferta;}

    public void setRegimeDeOferta(String regimeDeOferta) {this.regimeDeOferta = regimeDeOferta;}

    public int getCargaTotal(){
        return cargaEaD + cargaTeorica + cargaPratica + cargaExtensao;
    }

    public void setPreRequisitos(String preRequisito){
        this.preRequisito = preRequisito;
        if(preRequisito.isBlank()){
            this.preRequisito = "---";
        }
    }

    public String getPreRequisito(){return preRequisito;}

    public void setCoRequisito(String coRequisito){

        this.coRequisito= coRequisito;
        if(coRequisito.isBlank()){
            this.coRequisito = "---";
        }
    }

    public String getCoRequisito(){
        return coRequisito;
    }

    public void setEquivalencias(String equivalencias){
        this.equivalencias= equivalencias;
        if(equivalencias.isBlank()){
            this.equivalencias = "---";
        }
    }

    public String getEquivalencias(){
        return equivalencias;
    }

    public boolean isCompleto(){
        return (codigo != null && cargaTotal>0 && estruturaCurricular!= null && regimeDeOferta != null);
    }

    @Override
    public String toString() {
        return String.format("%s %20s", codigo, nome);
    }
}