package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class Interface_menu {
    public Parent Interface_menuChamada() throws IOException {
        return FXMLLoader.load(getClass().getResource("/View/Interface_Menu.fxml"));
    }
}
//awdawdawdawd