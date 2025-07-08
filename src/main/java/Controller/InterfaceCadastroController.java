package Controller;

import Model.Model;
import Model.Professor;
import View.InterfaceLogin;
import View.InterfaceMenu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import View.InterfaceLogin;
import static Controller.InterfaceLoginController.professorAtual;


public class InterfaceCadastroController implements Initializable {

    @FXML
    public Pane fundoInvisivel;
    public void initialize(URL location, ResourceBundle resources) {
        fundoInvisivel.requestFocus();
    }

    @FXML
    private Button botaoCadastro;

    @FXML
    private Hyperlink link_Login;

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


    //metodos
    public void aoClicarJaTemLogin() {
        try {
            InterfaceLogin telaLogin = new InterfaceLogin();
            Parent arquivoJanela = telaLogin.getRoot();
            Stage janelaAtual = (Stage) link_Login.getScene().getWindow();
            janelaAtual.setScene(new Scene(arquivoJanela));
            janelaAtual.setTitle("Login");
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
            Professor professorNovo = new Professor(nomeParaMandar, loginParaMandar, senhaParaMandar);

            if(Model.LoginExiste(professorNovo) == false){
                professorAtual = professorNovo.getNome();
                try{
                    Model.SalvarUsuario(professorNovo);//salva o usuario

                    Parent arquivoJanela = new InterfaceMenu().getRoot();
                    Stage janelaAtual = (Stage) botaoCadastro.getScene().getWindow();
                    janelaAtual.setScene(new Scene(arquivoJanela));
                    janelaAtual.setTitle("Menu");
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
}