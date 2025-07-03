package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class Interface_cadastro {

    public Interface_cadastro() throws IOException {
        Parent Cadastro = FXMLLoader.load(getClass().getResource("/View/Interface_cadastro.fxml"));
    }

    private String nome;
    private String login;
    private String senha;

}
