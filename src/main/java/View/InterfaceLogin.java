package View;

import Controller.InterfaceLoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import Model.Model;
import java.io.IOException;

public class InterfaceLogin {
    private Parent root;
    private InterfaceLoginController controller;
    private Model model;

    public InterfaceLogin() {
        try {
            controller = new InterfaceLoginController();
            model = new Model();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/interfaceLogin.fxml"));
            root = loader.load();
            controller = loader.getController(); // pega o que o FXML criou
        } catch (IOException e) {
            System.err.println("Erro ao carregar InterfaceLogin.fxml: " + e.getMessage());
        }
    }

    public Parent getRoot() {
        return root;
    }

    public InterfaceLoginController getController() {
        return controller;
    }
}
