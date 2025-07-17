package Controller;

import Model.Model;
import View.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;


public class InterfaceMenuController implements Initializable {

    //fundos
    @FXML
    public Pane fundoInvisivel;

    @FXML
    private Button botaoLogout;

    @FXML
    private Button botaoAddDisciplina;

    @FXML
    private Button botaoAddPED;

    @FXML
    private Button botaoExibirDisciplina;

    @FXML
    private Button botaoExibirPED;

    private Model model;


    public void setModel(Model model) {
        this.model=model;
    }

    public void initialize(URL location, ResourceBundle resources) {
        fundoInvisivel.requestFocus();
    }

    //metodos
    public void aoClicarLogout(){
        Parent arquivoJanela = new InterfaceLogin(model).getRoot();
        Stage JanelaAtual = (Stage) botaoLogout.getScene().getWindow();
        JanelaAtual.setScene(new Scene(arquivoJanela));
        JanelaAtual.setTitle("Projeto PEDs");
        JanelaAtual.centerOnScreen();
    }
    public void aoCliclarAddDisciplina(){
        try{
            Parent arquivoJanela = new InterfaceMenuDisciplina(model).getRoot();
            Stage JanelaAtual = (Stage) botaoAddDisciplina.getScene().getWindow();
            JanelaAtual.setScene(new Scene(arquivoJanela));
            JanelaAtual.setTitle("Cadastro Disciplina");
            JanelaAtual.centerOnScreen();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void aoClicarAddPED() {
        try {
            Parent arquivoJanela = new InterfaceMenuPED(model).getRoot();
            Stage JanelaAtual = (Stage) botaoAddPED.getScene().getWindow();
            JanelaAtual.setScene(new Scene(arquivoJanela));
            JanelaAtual.setTitle("Cadastro PED");
            JanelaAtual.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void aoClicarExibirPED(){
        try {
            Parent arquivoJanela = new InterfaceExibirPEDs(model).getRoot();
            Stage JanelaAtual = (Stage) botaoExibirDisciplina.getScene().getWindow();
            JanelaAtual.setScene(new Scene(arquivoJanela));
            JanelaAtual.setTitle("Exibir PEDs");
            JanelaAtual.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void aoClicarExibirDisciplina(){
        try {
            Parent arquivoJanela = new InterfaceExibirDisciplina(model).getRoot();
            Stage JanelaAtual = (Stage) botaoExibirDisciplina.getScene().getWindow();
            JanelaAtual.setScene(new Scene(arquivoJanela));
            JanelaAtual.setTitle("Exibir Disciplina");
            JanelaAtual.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //metodo deus
    @FXML
    private void metodoDeus(ActionEvent evento){
        Object fonte = evento.getSource();

        if(fonte instanceof Button){
            Button botao = (Button) fonte;
            String id = botao.getId();

            switch (id){
                case "botaoLogout":
                    aoClicarLogout();
                    break;
                case "botaoAddDisciplina":
                    aoCliclarAddDisciplina();
                    break;
                case "botaoAddPED":
                    aoClicarAddPED();
                    break;
                case "botaoExibirPED":
                    aoClicarExibirPED();
                    break;
                case "botaoExibirDisciplina":
                    aoClicarExibirDisciplina();
                    break;
            }
        }
    }
}