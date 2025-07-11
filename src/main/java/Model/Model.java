package Model;

import java.sql.*;
//Gathored
public class Model{
    private  Connection conectar;
    private Disciplina disciplina;

    //construtor
    public Model(){
        seConectarUsuario();
        seConectarDisciplina();
        criarTabelaUsuario();
        criarTabelaDisciplina();
        this.disciplina = new Disciplina();
    }

    //sets CargaHoraria Disciplina
    public void setCargaTeorica(int valor) {
        if (disciplina != null) {
            disciplina.setCargaTeorica(valor);
        }
    }

    public void setCargaPratica(int valor) {
        if (disciplina != null) {
            disciplina.setCargaPratica(valor);
        }
    }

    public void setCargaExtensao(int valor) {
        if (disciplina != null) {
            disciplina.setCargaExtensao(valor);
        }
    }

    public void setCargaEaD(int valor) {
        if (disciplina != null) {
            disciplina.setCargaEaD(valor);
        }
    }

    public int getCargaTotal() {
        if (disciplina != null) {
            return disciplina.getCargaTotal();
        }
        return 0;
    }

    //metodos DB


    //DB Usuário
    public  void seConectarUsuario(){
        try{
            if(conectar == null || conectar.isClosed()){
                conectar = DriverManager.getConnection("jdbc:sqlite:bancoUsuarios.db");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void criarTabelaUsuario(){
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
    public void SalvarUsuario(Professor professor){
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
    public void ListarUsuarios(){
        String sql = "SELECT * FROM usuarios";

        try (Statement state = conectar.createStatement(); ResultSet resultado = state.executeQuery(sql)){
            while(resultado.next()){
                System.out.println("ID: " + resultado.getInt("id") + ", Nome: " + resultado.getString("nome") + ", login: " + resultado.getString("login") + ", senha: " + resultado.getString("senha"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean LoginExiste(Professor professor){
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
    public Professor LoginValido(Professor professor){
        String busca = "SELECT nome, login, senha FROM usuarios WHERE login = ?";

        try{
            PreparedStatement preparar = conectar.prepareStatement(busca);
            preparar.setString(1, professor.getLogin());
            ResultSet resultado = preparar.executeQuery();

            if(resultado.next()){
                String senha = resultado.getString("senha");//pega a senha do BANCO
                String nomeProfessor = resultado.getString("nome");

                if(senha.equals(professor.getSenha())){
                    return new Professor(nomeProfessor, professor.getLogin(), senha); //retorna um profesor com Nome para exibir mais tarde em alguma view o professor logado atualmente
                }
            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int CalcularEstatistica(){
        return 0;
    }


    //Verificacao Disciplina
    public boolean verificarDisciplina(
            String nome,
            String codigo,
            int cargaTeorica,
            int cargaPratica,
            int cargaEaD,
            int cargaExtensao,
            Professor professor,
            String estruturaCurricular,
            String obrigatoriedade,
            String preRequisitos,
            String coRequisito,
            String regimeDeOferta,
            String equivalencias
            ){
        if(
            !nome.isBlank()
            && !codigo.isBlank()
            && cargaTeorica != 0
            && cargaPratica !=0
            && cargaEaD != 0
            && cargaExtensao !=0
            && !estruturaCurricular.isBlank()
            && !obrigatoriedade.isBlank()
            && !preRequisitos.isBlank()
            && !coRequisito.isBlank()
            && !regimeDeOferta.isBlank()
            && !equivalencias.isBlank()
        ){
            Disciplina disciplina = new Disciplina();
             disciplina.setCodigo(codigo);
             disciplina.setCargaTeorica(cargaTeorica);
             disciplina.setCargaPratica(cargaPratica);
             disciplina.setCargaEaD(cargaEaD);
             disciplina.setCargaExtensao(cargaExtensao);
             disciplina.setEstruturaCurricular(estruturaCurricular);
             disciplina.setObrigatoriedade(obrigatoriedade);
             disciplina.setPreRequisitos(preRequisitos);
             disciplina.setCoRequisito(coRequisito);
             disciplina.setRegimeDeOferta(regimeDeOferta);
             disciplina.setEquivalencias(equivalencias);
             disciplina.setProfessor(professor);
             SalvarDisciplina(disciplina);
             return true;
        } else {
            return false;
        }
    }
    //DB Disciplina
    public void seConectarDisciplina(){
        try{
            if(conectar == null || conectar.isClosed()){
                conectar = DriverManager.getConnection("jdbc:sqlite:bancoDisciplina.db");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void criarTabelaDisciplina(){
        String sql = """
                CREATE TABLE IF NOT EXISTS disciplina (
                    id INTEGER PRIMARY KEY AUTOINCREMENT, 
                    nome TEXT NOT NULL,
                    codigo TEXT NOT NULL,
                    cargaTeorica TEXT NOT NULL,
                    cargaPratica TEXT NOT NULL,
                    cargaEaD TEXT NOT NULL,
                    cargaExtensao TEXT NOT NULL,
                    cargaTotal INTEGER,
                    professor TEXT NOT NULL,
                    estruturaCurricular TEXT NOT NULL,
                    obrigatoriedade TEXT NOT NULL,
                    preRequisito TEXT NOT NULL,
                    coRequisito TEXT NOT NULL,
                    regimeDeOferta TEXT NOT NULL,
                    equivalencias TEXT NOT NULL
                )
                """;
        try (Statement state = conectar.createStatement()){
            state.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void SalvarDisciplina(Disciplina disciplina){
        String inserir = """
                INSERT INTO disciplina(
                nome,
                codigo,
                cargaTeorica,
                cargaPratica,
                cargaEaD,
                cargaExtensao,
                cargaTotal,
                professor,
                estruturaCurricular,
                obrigatoriedade,
                preRequisito,
                coRequisito,
                regimeDeOferta,
                equivalencias
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";
        try(PreparedStatement ps = conectar.prepareStatement(inserir)) {
            ps.setString(1, disciplina.getNome());
            ps.setString(2, disciplina.getCodigo());
            ps.setString(3, disciplina.getCargaTeorica());
            ps.setString(4, disciplina.getCargaPratica());
            ps.setString(5, disciplina.getCargaEaD());
            ps.setString(6, disciplina.getCargaExtensao());
            ps.setInt(7, disciplina.getCargaTotal());
            ps.setString(8, disciplina.getProfessor().getLogin());
            ps.setString(9, disciplina.getCodigo());
            ps.setString(10, disciplina.getEstruturaCurricular());
            ps.setString(11, disciplina.getObrigatoriedade());
            ps.setString(12, disciplina.getPreRequisito());
            ps.setString(13, disciplina.getCoRequisito());
            ps.setString(14, disciplina.getRegimeDeOferta());
            ps.setString(15, disciplina.getEquivalencias());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean DisciplinaExiste(Disciplina disciplina){
        String busca = "SELECT codigo FROM disciplina WHERE codigo = ?";

        try{
            PreparedStatement preparar = conectar.prepareStatement(busca);
            preparar.setString(1, disciplina.getCodigo());
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
}

