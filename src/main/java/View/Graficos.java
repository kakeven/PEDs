package View;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Graficos {
    public class Grafico extends Application {
        @Override
        public void start(Stage primaryStage) {
            PieChart grafico = new PieChart();
            grafico.getData().add(new PieChart.Data("Chocolate", 400));
            grafico.getData().add(new PieChart.Data("Baunilha", 300));
            grafico.getData().add(new PieChart.Data("Morango", 300));

            StackPane root = new StackPane(grafico);
            Scene scene = new Scene(root, 400, 300);

            primaryStage.setTitle("Gráfico de Pizza - JavaFX");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }
}
