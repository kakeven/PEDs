package Controller;

import Model.Model;
import Model.Usuario;
import View.Interface_menuDisciplina;
import com.sun.jdi.StringReference;
import javafx.fxml.FXML;
import View.Interface_login;
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

public class Interface_menuController implements Initializable {

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
        try{
            Parent arquivoJanela = Interface_login.Interface_loginChamada();
            Stage JanelaAtual = (Stage) botao_Logout.getScene().getWindow();
            JanelaAtual.setScene(new Scene(arquivoJanela));
            JanelaAtual.setTitle("Projeto PEDs");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void aoCliclarAddDisciplina(){
        try{
            Parent arquivoJanela = Interface_menuDisciplina.Interface_cadastroDisciplinaChamada();
            Stage JanelaAtual = (Stage) botao_addDisciplina.getScene().getWindow();
            JanelaAtual.setScene(new Scene(arquivoJanela));
            JanelaAtual.setTitle("Cadastro Disciplina");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
