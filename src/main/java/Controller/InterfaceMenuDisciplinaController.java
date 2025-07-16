package Controller;

import Model.Model;
import View.InterfaceMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
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
    private TextField textoHorasTotais;

    @FXML
    private TextField estruturaCurricular;

    @FXML
    private ChoiceBox<String> regimeDeOferta;

    @FXML
    private TextField codigoDisciplina;

    @FXML
    private TextArea preRequisitos;

    @FXML
    private TextArea coRequisitos;

    @FXML
    private TextArea equivalencias;

    @FXML
    private TextField nomeDisciplina;

    @FXML
    private Label labelHorasTotal;

    @FXML
    private Label labelMensagemErroCampos;

    @FXML
    private Label labelMensagemAddComSucesso;

    @FXML
    private Label labelMensagemDisciplinaJaExiste;

    //botoes
    @FXML
    private Button botaoVoltar;

    @FXML
    private Button botaoAdicionar;

    @FXML
    private Button botaoLimpar;

    //variaveis gerais
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
        labelMensagemAddComSucesso.setVisible(false);
        labelMensagemDisciplinaJaExiste.setVisible(false);

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
        regimeDeOferta.setValue("Semestral");
    }

    //metodos gerais
    public void aoClicarVoltar(){
        Parent ArquivoJavela = new InterfaceMenu(model).getRoot();
        Stage JanelaAtual = (Stage) botaoVoltar.getScene().getWindow();
        JanelaAtual.setScene(new Scene(ArquivoJavela));
        JanelaAtual.setTitle("Menu");
        JanelaAtual.centerOnScreen();
    }
    private void atualizarCargaTotal(){
        textoHorasTotais.setText(String.valueOf(model.getCargaTotal()));
    }
    public void aoClicarAdicionar(){
        if(model.verificarDisciplina(nomeDisciplina.getText(), codigoDisciplina.getText(), spinnerCargaTeorica.getValue(), spinnerCargaPratica.getValue(), spinnerCargaEaD.getValue(), spinnerCargaExtensao.getValue(), estruturaCurricular.getText(), preRequisitos.getText(), coRequisitos.getText(), regimeDeOferta.getValue(), equivalencias.getText())){
            labelMensagemAddComSucesso.setVisible(true);
            labelMensagemErroCampos.setVisible(false);
            labelMensagemDisciplinaJaExiste.setVisible(false);

            PauseTransition timer = new PauseTransition(Duration.seconds(2));
            timer.setOnFinished(e -> {
                Parent ArquivoJavela = new InterfaceMenu(model).getRoot();
                Stage JanelaAtual = (Stage) botaoAdicionar.getScene().getWindow();
                JanelaAtual.setScene(new Scene(ArquivoJavela));
                JanelaAtual.setTitle("Menu");
                JanelaAtual.centerOnScreen();
            });
            timer.play();
        }else{
            if(model.pesquisaDisciplinaPorCodigo(codigoDisciplina.getText())){
                labelMensagemDisciplinaJaExiste.setVisible(true);
                labelMensagemAddComSucesso.setVisible(false);
                labelMensagemErroCampos.setVisible(false);
            }else{
                labelMensagemErroCampos.setVisible(true);
                labelMensagemAddComSucesso.setVisible(false);
                labelMensagemDisciplinaJaExiste.setVisible(false);
            }
        }
    }
    public void aoClicarLimpar(){
        nomeDisciplina.setText("");
        codigoDisciplina.setText("");
        spinnerCargaExtensao.getValueFactory().setValue(0);
        spinnerCargaTeorica.getValueFactory().setValue(0);
        spinnerCargaPratica.getValueFactory().setValue(0);
        spinnerCargaEaD.getValueFactory().setValue(0);
        estruturaCurricular.setText("");
        preRequisitos.setText("");
        coRequisitos.setText("");
        equivalencias.setText("");
        regimeDeOferta.setValue("Semestral");
    }

    //metodo deus
    @FXML
    private void metodoDeus(ActionEvent evento){
        Object fonte = evento.getSource();

        if(fonte instanceof Button){
            Button botao = (Button) fonte;
            String id = botao.getId();

            switch (id){
                case "botaoVoltar":
                    aoClicarVoltar();
                    break;
                case "botaoAdicionar":
                    aoClicarAdicionar();
                    break;
                case "botaoLimpar":
                    aoClicarLimpar();
                    break;
            }
        }
    }
}
