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

    public void setModel(Model model) {
        this.model = model;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colunaID.setCellValueFactory(param -> {
            model.arrayPEDs();
            int indiceLinha = tabelaPED.getItems().indexOf(param.getValue());

            ArrayList<Integer> itensColunaID = model.getArrayIdPEDs();

            // Converte Integer para String
            List<String> itensString = itensColunaID.stream()
                    .map(String::valueOf)
                    .collect(Collectors.toList());

            String ID = String.join("\n", itensString);

            return new SimpleStringProperty(ID);
        });
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
            }
        }
    }
}
