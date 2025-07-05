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
    private TextField campoUsuario;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private Button botaoLogin;

    @FXML
    private Hyperlink link_Cadastro;

    @FXML
    private void aoClicarNaoTemCadastro(){//muda de tela
        try {
            Parent arquivoJanela = FXMLLoader.load(getClass().getResource("/View/Interface_cadastro.fxml"));
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
            String loginParaMandar = campoUsuario.getText();
            String senhaParaMandar = campoSenha.getText();

            Usuario usuario = new Usuario(loginParaMandar, senhaParaMandar);
            if(Model.LoginExiste(usuario)){
                //MUDAR PARA O CAMINHO DO MENU Parent arquivoJanela = FXMLLoader.load(getClass().getResource("/View/Interface_cadastro.fxml"));
                //Stage JanelaAtual = (Stage) botaoIrCadastro.getScene().getWindow();
                //JanelaAtual.setScene(new Scene(arquivoJanela));
                //JanelaAtual.setTitle("Menu");
            }else{
                System.out.println("Usuario e/ou senha incorretos");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}