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

    @FXML
    private void aoClicarNaoTemCadastro(){//muda de tela
        try {
            Parent arquivoJanela = InterfaceCadastro.InterfaceCadastroChamada();
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
            if(Model.LoginValido(professor)){
                Parent arquivoJanela = new InterfaceMenu().getRoot();
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
//    public void funcaoBotoes(String nomeBotao){
//        if(nomeBotao.equals()){
//            //
//        }else if(nomeBotao.equals("Nao tem login? cadastre-se")){
//            //
//        }
//    } ola neto
}