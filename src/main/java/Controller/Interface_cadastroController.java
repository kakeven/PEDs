package Controller;

import com.sun.jdi.StringReference;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class Interface_cadastroController {

    @FXML
    public Pane fundoInvisivel;
    public void initialize(URL location, ResourceBundle resources) {
        fundoInvisivel.requestFocus();
    }

    @FXML
    private TextField campoLogin;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private TextField campoNome;

    @FXML
    private Button botaoCadastro;

    @FXML
    private Hyperlink link_Login;


    //variaveis dos campos
    @FXML
    private String nome;

    @FXML
    private String login;

    @FXML
    private String senha;


    public void aoClicarJaTemLogin(){//muda de tela
        try{
            Parent arquivoJanela = FXMLLoader.load(getClass().getResource("/View/Interface_login.fxml"));
            Stage janelaAtual = (Stage) link_Login.getScene().getWindow();
            janelaAtual.setScene(new Scene(arquivoJanela));
            janelaAtual.setTitle("Login");
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
