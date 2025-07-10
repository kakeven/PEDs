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
    public void initialize(URL location, ResourceBundle resources) {
        fundoInvisivel.requestFocus();
    }


    //botoes
    @FXML
    private Button botao_Logout;
    @FXML
    private Button botao_addDisciplina;

    private Model model;
    public void setModel(Model model) {
        this.model=model;
    }

    //metodos
    public void aoClicarLogout(){
        Parent arquivoJanela = new InterfaceLogin(model).getRoot();
        Stage JanelaAtual = (Stage) botao_Logout.getScene().getWindow();
        JanelaAtual.setScene(new Scene(arquivoJanela));
        JanelaAtual.setTitle("Projeto PEDs");
    }
    public void aoCliclarAddDisciplina(){
        try{
            Parent arquivoJanela = FXMLLoader.load(getClass().getResource("/View/interfaceMenuPED.fxml"));//new InterfaceMenuDisciplina(model).getRoot()
            Stage JanelaAtual = (Stage) botao_addDisciplina.getScene().getWindow();
            JanelaAtual.setScene(new Scene(arquivoJanela));
            JanelaAtual.setTitle("Cadastro Disciplina");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
