package Controller;

import Model.Model;
import View.InterfaceCadastro;
import View.InterfaceMenu;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.*;
import java.util.stream.Collectors;

import javafx.application.Platform;

public class InterfaceExibirPEDsController implements Initializable{

    private Model model;

    @FXML
    private Button botaoVoltar;

    @FXML
    private Button botaoPDF;

    //table view
    @FXML
    private TableView<ObservableList<String>> tabelaPED;

    @FXML
    private TableColumn<ObservableList<String>, String> colunaID;

    @FXML
    private TableColumn<ObservableList<String>, String> colunaSemestre;

    @FXML
    private TableColumn<ObservableList<String>, String> colunaCurso;

    @FXML
    private TableColumn<ObservableList<String>, String> colunaDisciplina;

    //variaveis gerais
    String ID;

    public void setModel(Model model) {
        this.model = model;

        colunaID.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
        colunaSemestre.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
        colunaCurso.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(2)));
        colunaDisciplina.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(3)));
        colunaID.setStyle("-fx-alignment: CENTER");
        colunaSemestre.setStyle("-fx-alignment: CENTER");

        Platform.runLater(this::carregarDadosTabela);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabelaPED.getSelectionModel().selectedItemProperty().addListener((obs, valorAntigo, valorNovo) -> {
            if(valorNovo != null){
                ID = valorNovo.get(0);
            }
        });
    }

    public void carregarDadosTabela(){
        if(model != null){
            ArrayList<ArrayList<String>> pedsDoModelFormatados = model.getPedsParaTableView();

            ObservableList<ObservableList<String>> dadosTableView = FXCollections.observableArrayList();

            for(ArrayList<String> row : pedsDoModelFormatados){
                dadosTableView.add(FXCollections.observableArrayList(row));
            }
            tabelaPED.setItems(dadosTableView);
        }
    }

    public void aoClicarVoltar(){
        try {
            Parent arquivoJanela = new InterfaceMenu(model).getRoot();
            Stage JanelaAtual = (Stage) botaoVoltar.getScene().getWindow();
            JanelaAtual.setScene(new Scene(arquivoJanela));
            JanelaAtual.setTitle("Menu");
            JanelaAtual.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void aoClicarExportaPDF(){
        model.setPedAtual(model.pesquisaPEDPorID(Integer.valueOf(ID)));
        model.gerarDocx();
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
                case "botaoDocx":
                    aoClicarExportaPDF();
            }
        }
    }
}
