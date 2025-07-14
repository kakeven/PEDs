package View;




import Controller.InterfaceMenuPEDController;
import Model.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class InterfaceMenuPED {
    private Parent root;
    private InterfaceMenuPEDController controller;
    private Model model;
    
    public InterfaceMenuPED(Model model) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/interfaceMenuPED.fxml"));
            root = loader.load();
            controller = loader.getController(); // pega o que o FXML criou
            controller.setModel(model);
        } catch (IOException _) {

        }
    }

    public Parent getRoot() {
        return root;
    }

    public InterfaceMenuPEDController getController() {
        return controller;
    }
}
