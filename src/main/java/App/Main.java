package App;

import Model.Model;
import View.Interface_login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //inicializa o banco de dados
        Model.seConectar();
        Model.criarTabela();

        Interface_login login = new Interface_login(); //inicializa o objeto da view, no caso tela de login
        Parent loginChamada = login.Interface_loginChamada();

        primaryStage.setTitle("Minha Aplicação JavaFX");
        primaryStage.setScene(new Scene(loginChamada, 600, 400)); //edita tamanho só
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
