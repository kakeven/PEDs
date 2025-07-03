package Controller;

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
    private void aoClicarEntrar() {
        // deixar vazio
    }

    @FXML
    private Hyperlink botaoIrCadastro;

    @FXML
    private void aoClicarNaoTemCadastro() {
        try {
            Parent arquivoJanela = FXMLLoader.load(getClass().getResource("/View/Interface_cadastro.fxml"));
            Stage JanelaAtual = (Stage) botaoIrCadastro.getScene().getWindow();
            JanelaAtual.setScene(new Scene(arquivoJanela));
            JanelaAtual.setTitle("Cadastro");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}