package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carrega o arquivo FXML
        Parent root = FXMLLoader.load(getClass().getResource("/view/Interface_login.fxml"));

        // Configura a janela
        primaryStage.setTitle("Minha Aplicação JavaFX");
        primaryStage.setScene(new Scene(root, 600, 400)); // tamanho opcional
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
