package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class Interface_menuDisciplina {
    public static Parent Interface_cadastroDisciplinaChamada() throws IOException {
        return FXMLLoader.load(Interface_menu.class.getResource("/View/Interface_menuDisciplina.fxml"));
    }
}
//awdawdawdawd