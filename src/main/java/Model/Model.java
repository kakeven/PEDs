package Model;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Model{
    private static Connection conectar;

    //metodos
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
    public static void salvarUsuario(Usuario usuario){
        String inserir = "INSERT INTO usuarios(nome, login, senha) VALUES (?, ?, ?)";

        try(PreparedStatement ps = conectar.prepareStatement(inserir)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getLogin());
            ps.setString(3, usuario.getSenha());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void listarUsuarios(){
        String sql = "SELECT * FROM usuarios";

        try (Statement state = conectar.createStatement(); ResultSet resultado = state.executeQuery(sql)){
            while(resultado.next()){
                System.out.println("ID: " + resultado.getInt("id") + ", Nome: " + resultado.getString("nome") + ", login: " + resultado.getString("login") + ", senha: " + resultado.getString("senha"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}