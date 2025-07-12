package Controller;

import Model.Model;
import View.InterfaceMenu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

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

    private Model model;
    public void setModel(Model model) {
        this.model=model;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //tira alguns botoes da toolbar(q permitira "executar" comandos e outras paradinha)(deixa mais rapido, eu acho)
        Platform.runLater(() -> {
            //setar fontes(isso aq NAO TA FUNCIONANDO AINDA, VOU ARRUMAR)
            String fonteInicial = "<!DOCTYPE html><html><head>" + "<style>" + "body { font-family: 'Comic Sans', serif; font-size: 14px; }" + "p { font-family: 'Times New Roman', serif; font-size: 14px; }" + "li { font-family: 'Times New Roman', serif; font-size: 14px; }" + "</style>" + "</head><body><p></p></body></html>";

            justificativaEditor.setHtmlText(fonteInicial);
            objetivosEditor.setHtmlText(fonteInicial);
            sistemaDeAvaliacaoEditor.setHtmlText(fonteInicial);
            bibliografiaEditor.setHtmlText(fonteInicial);
            ementaEditor.setHtmlText(fonteInicial);
            metodologiaEditor.setHtmlText(fonteInicial);
            atividadesDoDiscenteEditor.setHtmlText(fonteInicial);


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
    }


}
