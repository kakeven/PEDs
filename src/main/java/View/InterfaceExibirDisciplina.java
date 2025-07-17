package View;

import Controller.InterfaceExibirDisciplinaController;

import Model.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class InterfaceExibirDisciplina {
    private Parent root;
    private InterfaceExibirDisciplinaController controller;

    public InterfaceExibirDisciplina(Model model) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/interfaceExibirDisciplina.fxml"));
            root = loader.load(); //carrega a interface do FXML
            controller = loader.getController(); //pega a interface e cria o controller automatico, por causa do javaFX
            controller.setModel(model);  // passa o model pro controller,== , passar os dados atualizados
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //testee
    public Parent getRoot() {
        return root;
    }

    public InterfaceExibirDisciplinaController getController() {
        return controller;
    }


}