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
        Parent Cadastro = FXMLLoader.load(getClass().getResource("/View/Interface_cadastro.fxml"));
        Parent Login = FXMLLoader.load(getClass().getResource("/View/Interface_login.fxml"));
        // Configura a janela
        primaryStage.setTitle("Minha Aplicação JavaFX");
        primaryStage.setScene(new Scene(Cadastro, 600, 400)); // tamanho opcional

        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
