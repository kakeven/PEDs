package Model;

import java.sql.*;

public class Model{
    private static Connection conectar;

    //metodos DB
    public static void seConectar(){
        try{
            if(conectar == null || conectar.isClosed()){
                conectar = DriverManager.getConnection("jdbc:sqlite:bancoUsuarios.db");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void criarTabela(){
        String sql = """
                CREATE TABLE IF NOT EXISTS usuarios (
                    id INTEGER PRIMARY KEY AUTOINCREMENT, 
                    nome TEXT NOT NULL,
                    login TEXT NOT NULL,
                    senha TEXT NOT NULL
                )
                """;
        try (Statement state = conectar.createStatement()){
            state.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void SalvarUsuario(Professor professor){
        String inserir = "INSERT INTO usuarios(nome, login, senha) VALUES (?, ?, ?)";
        try(PreparedStatement ps = conectar.prepareStatement(inserir)) {
            ps.setString(1, professor.getNome());
            ps.setString(2, professor.getLogin());
            ps.setString(3, professor.getSenha());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void ListarUsuarios(){
        String sql = "SELECT * FROM usuarios";

        try (Statement state = conectar.createStatement(); ResultSet resultado = state.executeQuery(sql)){
            while(resultado.next()){
                System.out.println("ID: " + resultado.getInt("id") + ", Nome: " + resultado.getString("nome") + ", login: " + resultado.getString("login") + ", senha: " + resultado.getString("senha"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean LoginExiste(Professor professor){
        String busca = "SELECT login, senha FROM usuarios WHERE login = ?";

        try{
            PreparedStatement preparar = conectar.prepareStatement(busca);
            preparar.setString(1, professor.getLogin());
            ResultSet resultado = preparar.executeQuery();

            if(resultado.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean LoginValido(Professor professor){
        String busca = "SELECT login, senha FROM usuarios WHERE login = ?";

        try{
            PreparedStatement preparar = conectar.prepareStatement(busca);
            preparar.setString(1, professor.getLogin());
            ResultSet resultado = preparar.executeQuery();

            if(resultado.next()){
                String senha = resultado.getString("senha");//pega a senha do BANCO
                String login = resultado.getString("login");//pega a login do BANCO
                return senha.equals(professor.getSenha()) && login.equals(professor.getLogin()); //compara cm a sneha e o login q digitou
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}