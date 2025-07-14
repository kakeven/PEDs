package Controller;

import Model.Model;
import Model.Professor;
import View.InterfaceMenu;
import View.InterfaceCadastro;
import View.InterfaceLogin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.text.View;
import java.net.URL;
import java.util.ResourceBundle;

public class InterfaceLoginController implements Initializable {
    @FXML
    public Pane fundoInvisivel;
    public void initialize(URL location, ResourceBundle resources) {
        lblMensagemErro.setVisible(false);
        fundoInvisivel.requestFocus();
    }

    @FXML
    private Button botaoLogin;

    @FXML
    private Hyperlink linkCadastro;

    //variaveis de campos
    @FXML
    private TextField campoLogin;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private Label lblMensagemErro;

    //variaveis
    public static String professorAtual;

    private Model model;
    public void setModel(Model model){
        this.model=model;
    }

    //metodos
    @FXML
    private void aoClicarNaoTemCadastro(){//muda de tela
        try {
            Parent arquivoJanela = new InterfaceCadastro(model).getRoot();
            Stage JanelaAtual = (Stage) linkCadastro.getScene().getWindow();
            JanelaAtual.setScene(new Scene(arquivoJanela));
            JanelaAtual.setTitle("Cadastro");
            JanelaAtual.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void aoClicarEntrar() {
        try{
            String loginParaMandar = campoLogin.getText();
            String senhaParaMandar = campoSenha.getText();

            Professor professor = new Professor(loginParaMandar, senhaParaMandar);
            Professor professorValidado = model.LoginValido(professor); //talvez dê um nullpointer, n sei ainda

            if(professorValidado != null){
                professorAtual = professorValidado.getNome();
                Parent arquivoJanela = new InterfaceMenu(model).getRoot();
                Stage JanelaAtual = (Stage) botaoLogin.getScene().getWindow();
                JanelaAtual.setScene(new Scene(arquivoJanela));
                JanelaAtual.setTitle("Menu");
                JanelaAtual.centerOnScreen();
            }else{
                lblMensagemErro.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}