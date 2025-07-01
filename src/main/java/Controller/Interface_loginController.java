package Controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
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
        // deixar vazio — quem implementa é o responsável pela lógica
    }
}