package View;

import Controller.InterfaceMenuDisciplinaController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class InterfaceMenuDisciplina {
    private Parent root;
    private InterfaceMenuDisciplinaController controller;

    public InterfaceMenuDisciplina() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/interfaceMenuDisciplina.fxml"));
            root = loader.load();
            controller = loader.getController(); // pega o que o FXML criou
        } catch (IOException e) {
            System.err.println("Erro ao carregar InterfaceLogin.fxml: " + e.getMessage());
        }
    }

    public Parent getRoot() {
        return root;
    }

    public InterfaceMenuDisciplinaController getController() {
        return controller;
    }
}
