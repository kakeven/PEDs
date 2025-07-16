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
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

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
    private ChoiceBox<String> choiceObrigatoriedade;

    @FXML
    private ComboBox<String> comboCurso;

    //textField
    @FXML
    private TextField nomeProfessor;

    @FXML
    private TextField textoAulas;

    @FXML
    private TextField nomeUnidade;

    @FXML
    private TextField textoHorasRestantes;

    @FXML
    private TextField textoSemestre;

    @FXML
    private Button botaoAddAula;

    @FXML
    private Button botaoVoltar;

    @FXML
    private Button botaoSalvarPED;

    @FXML
    private Button botaoLimparPED;

    @FXML
    private Spinner<Integer> spinnerHoraAula;

    //date picker(datas)
    @FXML
    private DatePicker seletorDatas;

    //listview
    @FXML
    private ListView<String> listaDeVisualizacao;


    //criação Model(instancia)
    private Model model;

    //variaveis gerais
    private ObservableList<String> listaDeAula = FXCollections.observableArrayList();
    int horasRestante = 0;

    //variaveis para data
    private LocalDate[] dataSelecionada = new LocalDate[1];
    private boolean primeiraSelecaoData = false;
    private Set<LocalDate> datasSelecionadas = new HashSet<>();


    public void setModel(Model model) {
        //alguns choice ficam no setModel pq se nao ele fica null
        this.model=model;
        choiceDisciplina.getItems().clear();
        choiceDisciplina.getItems().addAll(model.arrayDisciplinas());
        choiceDisciplina.setValue("Nenhuma Disciplina Selecionada");
        nomeProfessor.setText(model.getProfessorAtual().getNome());

        //listerner do choiceBox(pegar carga total da disciplina selecionada)
        choiceDisciplina.getSelectionModel().selectedItemProperty().addListener((obs, valorAntigo, valorNovo) -> {
            if (valorNovo != null && !valorNovo.toString().equals("Nenhuma Disciplina Selecionada")) {
                horasRestante = model.getCargaTotal(valorNovo);
                textoHorasRestantes.setText(String.valueOf(horasRestante));
            } else {
                textoHorasRestantes.setText("0");
            }
        });
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
        comboCurso.setValue("Nenhuma Curso Selecionado");
        comboCurso.setOnAction(event -> {
            String txtDigitado = comboCurso.getEditor().getText();

            if(txtDigitado != null && !txtDigitado.trim().isBlank()){
                if(!comboCurso.getItems().contains(txtDigitado)){
                    comboCurso.getItems().add(txtDigitado);
                }
                comboCurso.setValue(txtDigitado);
            }
        });//futuramente se der certo add persistencia

        choiceObrigatoriedade.getItems().addAll("Obrigatória", "Optativa");
        choiceObrigatoriedade.setValue("Obrigatória");

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

        if(horasRestante == 0){ //garantir q ele receba so a primeira vez
            horasRestante = cargaHorariaDisciplina;
        }

        Boolean aulaCriada = model.criarAula(dataFormatada, nomeAula, horaAula, cargaHorariaDisciplina, dataNormal);

        if(aulaCriada){
            listaDeAula.setAll(model.getAulasLista());
            horasRestante = horasRestante - spinnerHoraAula.getValue();
            textoHorasRestantes.setText(String.valueOf(horasRestante));

            //PINTAR AS DATAS A PARTIR Q ADD A AULA(se for a primeira bloqueia a parte de tras e pinta de vermelho)
            if(!primeiraSelecaoData){
                dataSelecionada[0] = dataLocal;
                primeiraSelecaoData = true;
            }
            datasSelecionadas.add(dataLocal);

            Platform.runLater(() -> {
                seletorDatas.setDayCellFactory(dp -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if(item.isBefore(dataSelecionada[0])){
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");//bloqueiaa, pintando de vermelho
                        } else if(datasSelecionadas.contains(item)){
                            setStyle("-fx-background-color: #90ee90;");//pinta de verde tudo selecionado(q ta dentro do array)
                        } else {
                            setStyle("");
                        }
                    }
                });
            });
        }
    }
    public void aoClicarVoltar(){
        Parent arquivoJanela = new InterfaceMenu(model).getRoot();
        Stage JanelaAtual = (Stage) botaoVoltar.getScene().getWindow();
        JanelaAtual.setScene(new Scene(arquivoJanela));
        JanelaAtual.setTitle("Projeto PEDs");
        JanelaAtual.centerOnScreen();
        primeiraSelecaoData = false;
    }
    public void aoClicarSalvar(){
        model.verificarPed(nomeUnidade.getText(),choiceDisciplina.getValue(), comboCurso.getValue(), textoSemestre.getText(), justificativaEditor.getHtmlText(), ementaEditor.getHtmlText(), objetivosEditor.getHtmlText(), metodologiaEditor.getHtmlText(), atividadesDoDiscenteEditor.getHtmlText(), sistemaDeAvaliacaoEditor.getHtmlText(), bibliografiaEditor.getHtmlText(), choiceObrigatoriedade.getValue());
    }
}