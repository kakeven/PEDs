package Controller;

import Model.Model;
import Model.Professor;
import View.InterfaceLogin;
import View.InterfaceMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

import static Controller.InterfaceLoginController.professorAtual;


public class InterfaceCadastroController implements Initializable {

    private Model model;

    @FXML
    public Pane fundoInvisivel;

    public void initialize(URL location, ResourceBundle resources) {
        lblMensagemPreencher.setVisible(false);
        lblUsuarioJaExiste.setVisible(false);
        lblSenha8.setVisible(false);
        fundoInvisivel.requestFocus();
    }

    @FXML
    private Button botaoCadastro;

    @FXML
    private Hyperlink linkLogin;

    //variaveis dos campos
    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoLogin;

    @FXML
    private TextField campoSenha;

    @FXML
    private Label lblMensagemPreencher;

    @FXML
    private Label lblSenha8;

    @FXML
    private Label lblUsuarioJaExiste;

    public void setModel(Model model) {
        this.model = model;
    }


    //metodos
    public void aoClicarJaTemLogin() {
        try {
            InterfaceLogin telaLogin = new InterfaceLogin(model);
            Parent arquivoJanela = telaLogin.getRoot();
            Stage janelaAtual = (Stage) linkLogin.getScene().getWindow();
            janelaAtual.setScene(new Scene(arquivoJanela));
            janelaAtual.setTitle("Login");
            janelaAtual.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void aoClicarCadastrar(){
        String nomeParaMandar = "";
        String loginParaMandar = "";
        String senhaParaMandar = "";

        if(campoSenha.getText().length() >= 8 && (!campoNome.getText().isBlank() && !campoLogin.getText().isBlank() && !campoSenha.getText().isBlank())){
            nomeParaMandar = campoNome.getText();
            loginParaMandar = campoLogin.getText();
            senhaParaMandar = campoSenha.getText();

            //cria o usuario para adicionar no DB(se ja n existir no banco)

            if(model.verificarUsuario(nomeParaMandar, loginParaMandar, senhaParaMandar)){
                try{
                    Parent arquivoJanela = new InterfaceMenu(model).getRoot();
                    Stage janelaAtual = (Stage) botaoCadastro.getScene().getWindow();
                    janelaAtual.setScene(new Scene(arquivoJanela));
                    janelaAtual.setTitle("Menu");
                    janelaAtual.centerOnScreen();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                lblUsuarioJaExiste.setVisible(true);
                lblSenha8.setVisible(false);
            }
        }else if(campoNome.getText().isBlank() || campoLogin.getText().isBlank() || campoSenha.getText().isBlank()){
            lblSenha8.setVisible(false);
            lblMensagemPreencher.setVisible(true);
        }else{
            lblMensagemPreencher.setVisible(false);
            lblSenha8.setVisible(true);
        }
    }
    @FXML
    private void metodoDeus(ActionEvent evento){
        Object fonte = evento.getSource();

        if(fonte instanceof Hyperlink){
            Hyperlink link = (Hyperlink) fonte;
            String id = link.getId();

            switch (id){
                case "linkLogin":
                    aoClicarJaTemLogin();
            }
        }else{
            Button botao = (Button) fonte;
            String id = botao.getId();

            switch (id){
                case "botaoCadastro":
                    aoClicarCadastrar();
            }
        }
    }
}