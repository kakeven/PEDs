package View;

import Controller.InterfaceLoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import Model.Model;
import java.io.IOException;

public class InterfaceLogin {
    private Parent root;
    private InterfaceLoginController controller;


    public InterfaceLogin(Model model) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/interfaceLogin.fxml"));
            root = loader.load();
            controller = loader.getController(); // pega o que o FXML criou
            controller.setModel(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parent getRoot() {
        return root;
    }

    public InterfaceLoginController getController() {
        return controller;
    }
}
