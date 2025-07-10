package Controller;

import Model.Model;
import View.InterfaceMenu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

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
    private Label labelMensagemErroCampos;

    @FXML
    private TextField textoHorasTotais;

    //choices
    @FXML
    private ChoiceBox estruturaCurricular;

    @FXML
    private ChoiceBox regimeDeOferta;

    //variaveis
    private Model model;

    public void setModel(Model model){
        this.model=model;
    }

    //metodo initialize
    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        //spinners
        spinnerCargaTeorica.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 128, 0,2));
        spinnerCargaPratica.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 128, 0, 2));
        spinnerCargaExtensao.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 128, 0, 2));
        spinnerCargaEaD.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 128, 0, 2));
        textoHorasTotais.setText("0");

        //labels(usar a mensagem erro posteriormente)
        labelMensagemErroCampos.setVisible(false); //defien que a mensagem de erro vai começar nao visivel, so se ocorrer erro(mais pra frente)

        //listeners para ficar pegando mudancas nos spinners
        spinnerCargaTeorica.valueProperty().addListener((obs, valorAtigo, valorNovo) ->{
            model.setCargaTeorica(valorNovo);
            atualizarCargaTotal();
        });
        spinnerCargaPratica.valueProperty().addListener((obs, valorAntigo, valorNovo) -> {
            model.setCargaPratica(valorNovo);
            atualizarCargaTotal();
        });
        spinnerCargaExtensao.valueProperty().addListener((obs, valorAntigo, valorNovo) -> {
            model.setCargaExtensao(valorNovo);
            atualizarCargaTotal();
        });
        spinnerCargaEaD.valueProperty().addListener((obs, valorAntigo, valorNovo) -> {
            model.setCargaEaD(valorNovo);
            atualizarCargaTotal();
        });


        //choices
        regimeDeOferta.getItems().addAll("Semestral", "Anual", "Modular");
        estruturaCurricular.getItems().addAll("2014", "2018");

        regimeDeOferta.setValue("Semestral");
        estruturaCurricular.setValue("2018");

        //atualizarCargaTotal();
    }

    //metodos gerais
    public void aoClicarVoltar(){
        Parent ArquivoJavela = new InterfaceMenu(model).getRoot();
        Stage JanelaAtual = (Stage) botaoVoltar.getScene().getWindow();
        JanelaAtual.setScene(new Scene(ArquivoJavela));
        JanelaAtual.setTitle("Menu");
    }
    private void atualizarCargaTotal(){
        textoHorasTotais.setText(String.valueOf(model.getCargaTotal()));
    }
}
