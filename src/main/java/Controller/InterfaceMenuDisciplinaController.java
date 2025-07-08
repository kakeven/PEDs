package Controller;

import View.InterfaceMenu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static Controller.InterfaceLoginController.professorAtual;

public class InterfaceMenuDisciplinaController implements Initializable{

    //variaveis dos campos
    @FXML
    private Spinner<Integer>spinnerCargaTeorica;

    @FXML
    private Spinner<Integer>spinnerCargaPratica;

    @FXML
    private Spinner<Integer>spinnerCargaExtensao;

    @FXML
    private Spinner<Integer>spinnerCargaEaD;

    @FXML
    private Button botaoVoltar;

    @FXML
    private Label labelHorasTotal;

    @FXML
    private TextField textoHorasTotais;

    @FXML
    private TextField nomeProfessorAtual;

    //variaveis


    //metodo initialize
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        spinnerCargaTeorica.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 128, 0,2));
        spinnerCargaPratica.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 128, 0, 2));
        spinnerCargaExtensao.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 128, 0, 2));
        spinnerCargaEaD.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 128, 0, 2));
        textoHorasTotais.setText("0");
        nomeProfessorAtual.setText(professorAtual);

        spinnerCargaTeorica.valueProperty().addListener((obs, valorAtigo, valorNovo) -> atualizarCargaTotal());
        spinnerCargaPratica.valueProperty().addListener((obs, valorAntigo, valorNovo) -> atualizarCargaTotal());
        spinnerCargaExtensao.valueProperty().addListener((obs, valorAntigo, valorNovo) -> atualizarCargaTotal());
        spinnerCargaEaD.valueProperty().addListener((obs, valorAntigo, valorNovo) -> atualizarCargaTotal());
    }

    //metodos gerais
    public void aoClicarVoltar(){
        Parent ArquivoJavela = new InterfaceMenu().getRoot();
        Stage JanelaAtual = (Stage) botaoVoltar.getScene().getWindow();
        JanelaAtual.setScene(new Scene(ArquivoJavela));
        JanelaAtual.setTitle("Menu");

    }
    private void atualizarCargaTotal(){
        int spinnerTeorica = spinnerCargaTeorica.getValue();
        int spinnerPratica = spinnerCargaPratica.getValue();
        int spinnerExtensao = spinnerCargaExtensao.getValue();
        int spinnerEaD = spinnerCargaEaD.getValue();

        int total = spinnerTeorica + spinnerEaD + spinnerExtensao + spinnerPratica;

        textoHorasTotais.setText(String.valueOf(total));
    }

}
