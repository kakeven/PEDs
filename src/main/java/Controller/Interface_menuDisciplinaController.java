package Controller;

import View.Interface_menu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Interface_menuDisciplinaController implements Initializable{

    //variaveis dos campos
    @FXML
    private Spinner<Integer>spinnerCargaTeorica;

    @FXML
    private Spinner<Integer>spinnerCargaPratica;

    @FXML
    private Spinner<Integer>spinnerCargaExtensao;

    @FXML
    private Spinner<Integer>spinnerCargaTotal;

    @FXML
    private Spinner<Integer>spinnerCargaEaD;

    @FXML
    private Button botaoVoltar;

    @FXML
    private Label labelHorasTeoricas;

    @FXML
    private Label labelHorasPraticas;

    @FXML
    private Label labelHorasExtensao;

    @FXML
    private Label labelHorasTotal;

    @FXML
    private Label labelHorasEaD;


    //metodo initialize
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        spinnerCargaTeorica.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 128, 0));
        spinnerCargaPratica.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 128, 0));
        spinnerCargaExtensao.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 128, 0));
        spinnerCargaTotal.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 128, 0));
        spinnerCargaEaD.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 128, 0));

        //listener para mudar "Horas" para "Hora"
        spinnerCargaTeorica.valueProperty().addListener((obs, valorAntigo, valorNovo) -> {
            if (valorNovo == 1) {
                labelHorasTeoricas.setText("Hora");
            } else {
                labelHorasTeoricas.setText("Horas");
            }
        });
        //pratica
        spinnerCargaPratica.valueProperty().addListener((obs, valorAntigo, valorNovo) -> {
            if (valorNovo == 1) {
                labelHorasPraticas.setText("Hora");
            } else {
                labelHorasPraticas.setText("Horas");
            }
        });
        //extensao
        spinnerCargaExtensao.valueProperty().addListener((obs, valorAntigo, valorNovo) -> {
            if (valorNovo == 1) {
                labelHorasExtensao.setText("Hora");
            } else {
                labelHorasExtensao.setText("Horas");
            }
        });
        //ead
        spinnerCargaEaD.valueProperty().addListener((obs, valorAntigo, valorNovo) -> {
            if (valorNovo == 1) {
                labelHorasEaD.setText("Hora");
            } else {
                labelHorasEaD.setText("Horas");
            }
        });
        //total
        spinnerCargaTotal.valueProperty().addListener((obs, valorAntigo, valorNovo) -> {
            if (valorNovo == 1) {
                labelHorasTotal.setText("Hora");
            } else {
                labelHorasTotal.setText("Horas");
            }
        });
    }

    //metodos gerais
    public void aoClicarVoltar(){
        try{
            Parent ArquivoJavela = Interface_menu.Interface_menuChamada();
            Stage JanelaAtual = (Stage) botaoVoltar.getScene().getWindow();
            JanelaAtual.setScene(new Scene(ArquivoJavela));
            JanelaAtual.setTitle("Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
