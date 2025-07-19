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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.application.Platform;
//testee
public class InterfaceExibirDisciplinaController {
    private Model model;

    @FXML
    private TableView<ObservableList<String>> tabelaDisciplina;

    @FXML
    private Button botaoVoltar;

    @FXML
    private TableColumn<ObservableList<String>, String> colunaNome;

    @FXML
    private TableColumn<ObservableList<String>, String> colunaCodigo;

    public void setModel(Model model) {
        this.model = model;
        colunaNome.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
        colunaCodigo.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));

        Platform.runLater(this::carregarDadosTabela);
    }
    public void carregarDadosTabela(){
        if(model != null){
            ArrayList<ArrayList<String>> disciplinasDoModelFormatadas = model.getDisciplinasParaTableView();

            ObservableList<ObservableList<String>> dadosTableView = FXCollections.observableArrayList();

            for(ArrayList<String> row : disciplinasDoModelFormatadas){
                dadosTableView.add(FXCollections.observableArrayList(row));
            }
            tabelaDisciplina.setItems(dadosTableView);
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
