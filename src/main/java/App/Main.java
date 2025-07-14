package App;

import Model.Model;
import View.InterfaceLogin;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();
        InterfaceLogin login = new InterfaceLogin(model);
        Parent loginChamada = login.getRoot();

        int largura = 600;
        int altura = 400;

        primaryStage.setTitle("Projeto PEDs");
        primaryStage.setScene(new Scene(loginChamada, largura, altura));

        // Centralizar a janela
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double posX = (bounds.getWidth() - largura) / 2.1;
        double posY = (bounds.getHeight() - altura) / 2.5;
        primaryStage.setX(posX);
        primaryStage.setY(posY);

        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
