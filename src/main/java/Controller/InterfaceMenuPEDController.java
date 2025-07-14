package Controller;

import Model.Model;
import View.InterfaceMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.application.Platform;

public class InterfaceMenuPEDController implements Initializable {

    @FXML
    private HTMLEditor justificativaEditor = new HTMLEditor();

    @FXML
    private HTMLEditor objetivosEditor = new HTMLEditor();

    @FXML
    private HTMLEditor sistemaDeAvaliacaoEditor = new HTMLEditor();

    @FXML
    private HTMLEditor bibliografiaEditor = new HTMLEditor();

    @FXML
    private HTMLEditor ementaEditor = new HTMLEditor();

    @FXML
    private HTMLEditor metodologiaEditor = new HTMLEditor();

    @FXML
    private HTMLEditor atividadesDoDiscenteEditor = new HTMLEditor();

    //choices/combo
    @FXML
    private ChoiceBox choiceDisciplina;

    @FXML
    private ComboBox comboCurso;

    //textField
    @FXML
    private TextField nomeProfessor;

    @FXML
    private TextField textoAulas;

    @FXML
    private Button botaoAddAula;

    @FXML
    private Button botaoVoltar;

    @FXML
    private Spinner<Integer> spinnerHoraAula;

    //date picker(datas)
    @FXML
    private DatePicker seletorDatas;

    //listview
    @FXML
    private ListView<String> listaDeVisualizacao;

    //variaveis gerais
    private ObservableList<String> listaDeAula = FXCollections.observableArrayList();

    private Model model;

    public void setModel(Model model) {
        this.model=model;
        choiceDisciplina.getItems().clear();
        choiceDisciplina.getItems().addAll(model.arrayDisciplinas());
        choiceDisciplina.setValue("Nenhuma Disciplina Selecionada");
        nomeProfessor.setText(model.getProfessorAtual().getNome());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //tira alguns botoes da toolbar(q permitira "executar" comandos e outras paradinhas)(deixa mais rapido tb)
        Platform.runLater(() -> {
            //tirar copiar
            justificativaEditor.lookupAll(".html-editor-copy").forEach(n -> {
                n.setVisible(false);
                n.setManaged(false);

            });
            //tirar colar
            justificativaEditor.lookupAll(".html-editor-paste").forEach(n -> {
                n.setVisible(false);
                n.setManaged(false);
            });


            //tirar copiar
            objetivosEditor.lookupAll(".html-editor-copy").forEach(n -> {
                n.setVisible(false);
                n.setManaged(false);
            });
            //tirar colar
            objetivosEditor.lookupAll(".html-editor-paste").forEach(n -> {
                n.setVisible(false);
                n.setManaged(false);
            });


            //tirar copiar
            sistemaDeAvaliacaoEditor.lookupAll(".html-editor-copy").forEach(n -> {
                n.setVisible(false);
                n.setManaged(false);
            });
            //tirar colar
            sistemaDeAvaliacaoEditor.lookupAll(".html-editor-paste").forEach(n -> {
                n.setVisible(false);
                n.setManaged(false);
            });


            //tirar copiar
            bibliografiaEditor.lookupAll(".html-editor-copy").forEach(n -> {
                n.setVisible(false);
                n.setManaged(false);
            });
            //tirar colar
            bibliografiaEditor.lookupAll(".html-editor-paste").forEach(n -> {
                n.setVisible(false);
                n.setManaged(false);
            });


            //tirar copiar
            ementaEditor.lookupAll(".html-editor-copy").forEach(n -> {
                n.setVisible(false);
                n.setManaged(false);
            });
            //tirar colar
            ementaEditor.lookupAll(".html-editor-paste").forEach(n -> {
                n.setVisible(false);
                n.setManaged(false);
            });


            //tirar copiar
            metodologiaEditor.lookupAll(".html-editor-copy").forEach(n -> {
                n.setVisible(false);
                n.setManaged(false);
            });
            //tirar colar
            metodologiaEditor.lookupAll(".html-editor-paste").forEach(n -> {
                n.setVisible(false);
                n.setManaged(false);
            });


            //tirar copiar
            atividadesDoDiscenteEditor.lookupAll(".html-editor-copy").forEach(n -> {
                n.setVisible(false);
                n.setManaged(false);
            });
            //tirar colar
            atividadesDoDiscenteEditor.lookupAll(".html-editor-paste").forEach(n -> {
                n.setVisible(false);
                n.setManaged(false);
            });
        });

        spinnerHoraAula.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 8, 0, 2));

        //combo box(permite o usuario ou selecionar algo que ja existe ou adicionar)
        comboCurso.getItems().addAll("Engenharia de Software", "Ciência da Computação", "Engenharia Mecânica", "Engenharia Civíl", "Engenharia de Produção");
        comboCurso.setOnAction(event -> {
            String txtDigitado = comboCurso.getEditor().getText();

            if(txtDigitado != null && !txtDigitado.trim().isBlank()){
                if(!comboCurso.getItems().contains(txtDigitado)){
                    comboCurso.getItems().add(txtDigitado);
                }
                comboCurso.setValue(txtDigitado);
            }
        });//futuramente se der certo add persistencia

        //listview
        listaDeVisualizacao.setItems(listaDeAula);
    }

    public void aoClicarAddAula(){
        //pegar data formatada
        LocalDate dataLocal = seletorDatas.getValue();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd 'do' MMMM 'de' YYYY");
        String dataFormatada = dataLocal.format(formatador);

        //pegar data normal(padrao brasil, isso vai servir na hora de portar o PED para PDF)
        String dataNormal =  seletorDatas.getValue().format(DateTimeFormatter.ofPattern("dd/MM"));

        //outras variaveis
        String nomeAula = textoAulas.getText();
        int horaAula = spinnerHoraAula.getValue();
        int cargaHorariaDisciplina = model.getCargaTotal(choiceDisciplina.getValue());

        Boolean aulaCriada = model.criarAula(dataFormatada, nomeAula, horaAula, cargaHorariaDisciplina, dataNormal);

        if(aulaCriada){
            listaDeAula.setAll(model.getAulasLista());
        }
    }
    public void aoClicarVoltar(){
        Parent arquivoJanela = new InterfaceMenu(model).getRoot();
        Stage JanelaAtual = (Stage) botaoVoltar.getScene().getWindow();
        JanelaAtual.setScene(new Scene(arquivoJanela));
        JanelaAtual.setTitle("Projeto PEDs");
        JanelaAtual.centerOnScreen();
    }
}