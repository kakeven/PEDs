package Controller;

import Model.Model;
import Model.Professor;
import View.InterfaceMenu;
import View.InterfaceCadastro;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class InterfaceLoginController implements Initializable {
    @FXML
    public Pane fundoInvisivel;
    public void initialize(URL location, ResourceBundle resources) {
        fundoInvisivel.requestFocus();
    }

    @FXML
    private Button botaoLogin;

    @FXML
    private Hyperlink link_Cadastro;

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
            Stage JanelaAtual = (Stage) link_Cadastro.getScene().getWindow();
            JanelaAtual.setScene(new Scene(arquivoJanela));
            JanelaAtual.setTitle("Cadastro");
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
            Professor professorValidado = Model.LoginValido(professor); //talvez dê um nullpointer, n sei ainda

            if(professorValidado != null){
                professorAtual = professorValidado.getNome();
                Parent arquivoJanela = new InterfaceMenu(model).getRoot();
                Stage JanelaAtual = (Stage) botaoLogin.getScene().getWindow();
                JanelaAtual.setScene(new Scene(arquivoJanela));
                JanelaAtual.setTitle("Menu");
            }else{
                lblMensagemErro.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}