package View;


import Controller.InterfaceMenuController;
import Model.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class InterfaceMenu {
    private Parent root;
    private InterfaceMenuController controller;



    public InterfaceMenu(Model model) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/interfaceMenu.fxml"));
            root = loader.load();
            controller = loader.getController(); // pega o que o FXML criou
            controller.setModel(model);
        } catch (IOException e) {
            System.err.println("Erro ao carregar InterfaceMenu.fxml: " + e.getMessage());
        }
    }

    public Parent getRoot() {
        return root;
    }

    public InterfaceMenuController getController() {
        return controller;
    }
}

