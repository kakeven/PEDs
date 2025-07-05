package Controller;

import Model.Model;
import Model.Usuario;
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
    private TextField login;

    @FXML
    private PasswordField senha;


    @FXML
    private void aoClicarNaoTemCadastro(){//muda de tela
        try {
            Parent arquivoJanela = FXMLLoader.load(getClass().getResource("/View/Interface_Cadastro.fxml"));
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
            String loginParaMandar = login.getText();
            String senhaParaMandar = senha.getText();

            Usuario usuario = new Usuario(loginParaMandar, senhaParaMandar);
            if(Model.LoginValido(usuario)){
                Parent arquivoJanela = FXMLLoader.load(getClass().getResource("/View/Interface_Menu.fxml"));
                Stage JanelaAtual = (Stage) botaoLogin.getScene().getWindow();
                JanelaAtual.setScene(new Scene(arquivoJanela));
                JanelaAtual.setTitle("Menu");
            }else{
                System.out.println("Usuario e/ou senha incorretos");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}