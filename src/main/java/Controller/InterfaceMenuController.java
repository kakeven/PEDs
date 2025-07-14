package Controller;

import Model.Model;
import View.InterfaceMenuDisciplina;
import View.InterfaceMenuPED;
import javafx.fxml.FXML;
import View.InterfaceLogin;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
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

    private Model model;

    @FXML
    private Button botaoAddPED;


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

}