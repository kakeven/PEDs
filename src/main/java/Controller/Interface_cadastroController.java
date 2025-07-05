package Controller;

import Model.Model;
import Model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class Interface_cadastroController implements Initializable {

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
    private TextField nome;

    @FXML
    private TextField login;

    @FXML
    private TextField senha;


    //metodos
    public void aoClicarJaTemLogin(){//muda de tela
        try{
            Parent arquivoJanela = FXMLLoader.load(getClass().getResource("/View/Interface_login.fxml"));
            Stage janelaAtual = (Stage) link_Login.getScene().getWindow();
            janelaAtual.setScene(new Scene(arquivoJanela));
            janelaAtual.setTitle("Login");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void aoClicarCadastrar(){
        String nomeParaMandar = "";
        String loginParaMandar = "";
        String senhaParaMandar = "";


        if(senha.getText().length() >= 8 && (!nome.getText().isBlank() && !login.getText().isBlank() && !senha.getText().isBlank())){
            nomeParaMandar = nome.getText();
            loginParaMandar = login.getText();
            senhaParaMandar = senha.getText();

            //cria o usuario para adicionar no DB(se ja n existir no banco)
            Usuario usuarioNovo = new Usuario(nomeParaMandar, loginParaMandar, senhaParaMandar);
            if(Model.LoginExiste(usuarioNovo) == false){
                Model.SalvarUsuario(usuarioNovo);
                Model.ListarUsuarios();
            }else{
                System.out.println("Usuario ja existe");
            }
        }else if(nome.getText().isBlank() || login.getText().isBlank() || senha.getText().isBlank()){
            System.out.println("preencha todos os campos");
        }else{
            System.out.println("adicione mais caracteres na senha");
        }
    }
}
