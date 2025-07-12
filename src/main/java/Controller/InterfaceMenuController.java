package Controller;

import Model.Model;
import View.InterfaceMenuDisciplina;
import javafx.fxml.FXML;
import View.InterfaceLogin;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
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

    private Model model;

    public void initialize(URL location, ResourceBundle resources) {
        fundoInvisivel.requestFocus();
    }

    public void setModel(Model model) {
        this.model=model;
    }

    //metodos
    public void aoClicarLogout(){
        Parent arquivoJanela = new InterfaceLogin(model).getRoot();
        Stage JanelaAtual = (Stage) botaoLogout.getScene().getWindow();
        JanelaAtual.setScene(new Scene(arquivoJanela));
        JanelaAtual.setTitle("Projeto PEDs");
    }
    public void aoCliclarAddDisciplina(){
        try{
            Parent arquivoJanela = new InterfaceMenuDisciplina(model).getRoot();
            Stage JanelaAtual = (Stage) botaoAddDisciplina.getScene().getWindow();
            JanelaAtual.setScene(new Scene(arquivoJanela));
            JanelaAtual.setTitle("Cadastro Disciplina");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void aoClicarAddPED(){
        try{
            Parent arquivoJanela = new InterfaceMenuPED(model).getRoot();
            Stage JanelaAtual = (Stage) botao_addDisciplina.getScene().getWindow();
            JanelaAtual.setScene(new Scene(arquivoJanela));
            JanelaAtual.setTitle("Cadastro PED");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}