package View;

import Controller.InterfaceExibirPEDsController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import Model.Model;
import java.io.IOException;

public class InterfaceExibirPEDs {
    private Parent root;
    private InterfaceExibirPEDsController controller;


    public InterfaceExibirPEDs(Model model) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/interfaceExibirPEDs.fxml"));
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

    public InterfaceExibirPEDsController getController() {
        return controller;
    }
}