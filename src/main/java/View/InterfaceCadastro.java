package View;

import Controller.InterfaceCadastroController;
import Controller.InterfaceLoginController;
import Model.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class InterfaceCadastro {
    private Parent root;
    private InterfaceCadastroController controller;

    public InterfaceCadastro(Model model) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/interfaceCadastro.fxml"));
            root = loader.load(); //carrega a interface do FXML
            controller = loader.getController(); //pega a interface e cria o controller automatico, por causa do javaFX
            controller.setModel(model);  // passa o model pro controller,== , passar os dados atualizados
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parent getRoot() {
        return root;
    }

    public InterfaceCadastroController getController() {
        return controller;
    }


}
