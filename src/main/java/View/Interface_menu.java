package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class Interface_menu {
    public static Parent Interface_menuChamada() throws IOException {
        return FXMLLoader.load(Interface_menu.class.getResource("/View/interface_menu.fxml"));
    }
}
//awdawdawdawd