package Controller;

import View.InterfaceMenuDisciplina;
import javafx.fxml.FXML;
import View.InterfaceLogin;
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

    //metodos
    public void aoClicarLogout(){
        Parent arquivoJanela = new InterfaceLogin().getRoot();
        Stage JanelaAtual = (Stage) botao_Logout.getScene().getWindow();
        JanelaAtual.setScene(new Scene(arquivoJanela));
        JanelaAtual.setTitle("Projeto PEDs");
    }
    public void aoCliclarAddDisciplina(){
        Parent arquivoJanela = new InterfaceMenuDisciplina().getRoot();
        Stage JanelaAtual = (Stage) botao_addDisciplina.getScene().getWindow();
        JanelaAtual.setScene(new Scene(arquivoJanela));
        JanelaAtual.setTitle("Cadastro Disciplina");
    }
}
