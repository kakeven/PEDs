package App;

import Model.Model;
import View.InterfaceLogin;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();
        InterfaceLogin login = new InterfaceLogin(model); //inicializa o objeto da view, no caso tela de login
        Parent loginChamada = login.getRoot();

        primaryStage.setTitle("Projeto PEDs");
        primaryStage.setScene(new Scene(loginChamada, 600, 400)); //edita tamanho só
        primaryStage.show();
        primaryStage.setResizable(false);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
