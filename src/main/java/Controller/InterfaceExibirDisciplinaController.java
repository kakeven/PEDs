package Controller;

import Model.Model;
import View.InterfaceCadastro;
import View.InterfaceMenu;
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
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.application.Platform;

public class InterfaceExibirDisciplinaController {

    public void setModel(Model model) {
        this.model = model;
    }

    private Model model;

    @FXML
    private Button botaoVoltar;

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
