package Controller;

import Model.Model;
import Model.Professor;
import View.Interface_menu;
import View.Interface_cadastro;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
//testannndo 123

public class Interface_loginController implements Initializable {
    @FXML
    public Pane fundoInvisivel;
    public void initialize(URL location, ResourceBundle resources) {
        fundoInvisivel.requestFocus();
    }

    @FXML
    private Button botaoLogin;

    @FXML
    private Hyperlink link_Cadastro;


    //variaveis de campos
    @FXML
    private TextField campoLogin;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private Label lblMensagemErro;

    @FXML
    private void aoClicarNaoTemCadastro(){//muda de tela
        try {
            Parent arquivoJanela = Interface_cadastro.Interface_cadastroChamada();
            Stage JanelaAtual = (Stage) link_Cadastro.getScene().getWindow();
            JanelaAtual.setScene(new Scene(arquivoJanela));
            JanelaAtual.setTitle("Cadastro");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void aoClicarEntrar() {
        try{
            String loginParaMandar = campoLogin.getText();
            String senhaParaMandar = campoSenha.getText();

            Professor professor = new Professor(loginParaMandar, senhaParaMandar);
            if(Model.LoginValido(professor)){
                Parent arquivoJanela = Interface_menu.Interface_menuChamada();
                Stage JanelaAtual = (Stage) botaoLogin.getScene().getWindow();
                JanelaAtual.setScene(new Scene(arquivoJanela));
                JanelaAtual.setTitle("Menu");
            }else{
                lblMensagemErro.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}