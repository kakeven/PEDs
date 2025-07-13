package Model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.sql.*;
import java.util.ArrayList;


public class Model{
    private Connection conectar;
    private Connection conectarUsuario;
    private Connection conectarPED;
    private Connection conectarDisciplina;
    private Disciplina disciplina;
    private Gson gson;
    private Professor professorAtual;
    private ArrayList<Aula> aulasTemp;

    //construtor
    public Model(){
        this.gson = new Gson();
        seConectarUsuario();
        seConectarDisciplina();
        seConectarPED();
        criarTabelaUsuario();
        criarTabelaDisciplina();
        criarTabelaPED();
        this.disciplina = new Disciplina();
        ArrayList<Aula> aulasTemp = new ArrayList<>();
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
            if(conectarUsuario == null || conectarUsuario.isClosed()){
                conectarUsuario = DriverManager.getConnection("jdbc:sqlite:bancoUsuarios.db");
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
        try (Statement state = conectarUsuario.createStatement()){
            state.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void SalvarUsuario(Professor professor){
        String inserir = "INSERT INTO usuarios(nome, login, senha) VALUES (?, ?, ?)";
        try(PreparedStatement ps = conectarUsuario.prepareStatement(inserir)) {
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

        try (Statement state = conectarUsuario.createStatement(); ResultSet resultado = state.executeQuery(sql)){
            while(resultado.next()){
                System.out.println("ID: " + resultado.getInt("id") + ", Nome: " + resultado.getString("nome") + ", login: " + resultado.getString("login") + ", senha: " + resultado.getString("senha"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean LoginExiste(Professor professor){//serve para nao cadastrar dois usuarios com mesmo login
        String busca = "SELECT login FROM usuarios WHERE login = ?";

        try{
            PreparedStatement preparar = conectarUsuario.prepareStatement(busca);
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
            PreparedStatement preparar = conectarUsuario.prepareStatement(busca);
            preparar.setString(1, professor.getLogin());
            ResultSet resultado = preparar.executeQuery();

            if(resultado.next()){
                String senha = resultado.getString("senha");//pega a senha do BANCO
                String nomeProfessor = resultado.getString("nome");

                if(senha.equals(professor.getSenha())){
                    return professorAtual = new Professor(nomeProfessor, professor.getLogin(), senha); //retorna um profesor com Nome para exibir mais tarde em alguma view o professor logado atualmente
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
    public void setProfessorAtual(Professor professor){
        this.professorAtual = professor;
    }
    public Professor getProfessorAtual() {
        return professorAtual;
    }
    public Professor pesquisaProfessorPorID(int id){
        String busca = "SELECT id FROM professor WHERE id = ?";

        try{
            PreparedStatement preparar = conectarUsuario.prepareStatement(busca);
            preparar.setInt(0, id);
            ResultSet rs = preparar.executeQuery();

            if(rs.next()){
                Professor professor= new Professor();
                professor.setLogin(rs.getString("login"));
                professor.setNome(rs.getString("nome"));
                professor.setSenha(rs.getString("senha"));
                return professor;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Verificacao Disciplina
    public boolean verificarDisciplina(
            String nome,
            String codigo,
            int cargaTeorica,
            int cargaPratica,
            int cargaEaD,
            int cargaExtensao,
            String estruturaCurricular,
            String preRequisitos,
            String coRequisito,
            String regimeDeOferta,
            String equivalencias
        ){
        if(
                !nome.isBlank()  && nome != null
                        && !codigo.isBlank() && codigo != null
                        && !estruturaCurricular.isBlank() && estruturaCurricular != null
                        && !regimeDeOferta.isBlank() && regimeDeOferta != null
        ){
            Disciplina disciplina = new Disciplina();
            disciplina.setNome(nome);
            disciplina.setCodigo(codigo);
            disciplina.setCargaTeorica(cargaTeorica);
            disciplina.setCargaPratica(cargaPratica);
            disciplina.setCargaEaD(cargaEaD);
            disciplina.setCargaExtensao(cargaExtensao);
            disciplina.setEstruturaCurricular(estruturaCurricular);
            disciplina.setPreRequisitos(preRequisitos);
            disciplina.setCoRequisito(coRequisito);
            disciplina.setRegimeDeOferta(regimeDeOferta);
            disciplina.setEquivalencias(equivalencias);
            if(DisciplinaExiste(disciplina) || disciplina.getCargaTotal()==0){
                disciplina = null;
                System.gc();
                return false;
            }
            SalvarDisciplina(disciplina);
            return true;
        } else {
            return false;
        }
    }
    //DB Disciplina
    public void seConectarDisciplina(){
        try{
            if(conectarDisciplina == null || conectarDisciplina.isClosed()){
                conectarDisciplina = DriverManager.getConnection("jdbc:sqlite:bancoDisciplina.db");
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
                    estruturaCurricular TEXT NOT NULL,
                    preRequisito TEXT NOT NULL,
                    coRequisito TEXT NOT NULL,
                    regimeDeOferta TEXT NOT NULL,
                    equivalencias TEXT NOT NULL
                )
                """;
        try (Statement state = conectarDisciplina.createStatement()){
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
                estruturaCurricular,
                preRequisito,
                coRequisito,
                regimeDeOferta,
                equivalencias
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";//
        try(PreparedStatement ps = conectarDisciplina.prepareStatement(inserir)) {
            ps.setString(1, disciplina.getNome());
            ps.setString(2, disciplina.getCodigo());
            ps.setString(3, disciplina.getCargaTeorica());
            ps.setString(4, disciplina.getCargaPratica());
            ps.setString(5, disciplina.getCargaEaD());
            ps.setString(6, disciplina.getCargaExtensao());
            ps.setInt(7, disciplina.getCargaTotal());
            ps.setString(8, disciplina.getEstruturaCurricular());
            ps.setString(9, disciplina.getPreRequisito());
            ps.setString(10, disciplina.getCoRequisito());
            ps.setString(11, disciplina.getRegimeDeOferta());
            ps.setString(12, disciplina.getEquivalencias());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean DisciplinaExiste(Disciplina disciplina){
        String busca = "SELECT codigo FROM disciplina WHERE codigo = ?";

        try{
            PreparedStatement preparar = conectarDisciplina.prepareStatement(busca);
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

    public int idDisciplina(Disciplina disciplina){
        String busca = "SELECT codigo FROM disciplina WHERE codigo = ?";

        try{
            PreparedStatement preparar = conectarDisciplina.prepareStatement(busca);
            preparar.setString(1, disciplina.getCodigo());
            ResultSet resultado = preparar.executeQuery();

            if(resultado.next()){
                return resultado.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean pesquisaDisciplinaPorCodigo(String codigo){
        String busca = "SELECT codigo FROM disciplina WHERE codigo = ?";

        try{
            PreparedStatement preparar = conectarDisciplina.prepareStatement(busca);
            preparar.setString(1, codigo);
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

    public Disciplina pesquisaDisciplinaPorID(int id){
        String busca = "SELECT id FROM disciplina WHERE id = ?";

        try{
            PreparedStatement preparar = conectarDisciplina.prepareStatement(busca);
            preparar.setInt(0, id);
            ResultSet rs = preparar.executeQuery();

            if(rs.next()){
                Disciplina disciplina = new Disciplina();
                disciplina.setNome(rs.getString("nome"));
                disciplina.setCodigo(rs.getString("codigo"));
                disciplina.setCargaTeorica(rs.getInt("cargaTeorica"));
                disciplina.setCargaPratica(rs.getInt("cargaPratica"));
                disciplina.setCargaEaD(rs.getInt("cargaEaD"));
                disciplina.setCargaExtensao(rs.getInt("cargaExtensao"));
                disciplina.setEstruturaCurricular(rs.getString("estruturaCurricular"));
                disciplina.setPreRequisitos(rs.getString("preRequisito"));
                disciplina.setCoRequisito(rs.getString("coRequisito"));
                disciplina.setRegimeDeOferta(rs.getString("regimeDeOferta"));
                disciplina.setEquivalencias(rs.getString("equivalencias"));
                return disciplina;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Verificacao PED
    public boolean verificarPed(
            String unidade,
            Professor professor,
            Disciplina disciplina,
            String curso,
            String semestre,
            String justificativa,
            String ementa,
            String objetivos,
            ArrayList<Aula> aulas,
            String metodologia,
            String atividadesDiscentes,
            String sistemaDeAvaliacao,
            String bibliografia,
            String obrigatoriedade
    ){
        if(
                !unidade.isBlank()
                        && !curso.isBlank()
                        && !semestre.isBlank()
                        && !justificativa.isBlank()
                        && !ementa.isBlank()
                        && !objetivos.isBlank()
                        && !metodologia.isBlank()
                        && !atividadesDiscentes.isBlank()
                        && !sistemaDeAvaliacao.isBlank()
                        && !bibliografia.isBlank()
                        && !obrigatoriedade.isBlank()
        ){
            PED ped = new PED();
            ped.setUnidade(unidade);
            ped.setDisciplina(disciplina);
            ped.setCurso(curso);
            ped.setSemestre(semestre);
            ped.setJustificativa(justificativa);
            ped.setEmenta(ementa);
            ped.setObjetivos(objetivos);
            ped.setMetodologia(metodologia);
            ped.setAtividadesDiscentes(atividadesDiscentes);
            ped.setSistemaDeAvaliacao(sistemaDeAvaliacao);
            ped.setBibliografia(bibliografia);
            ped.setProfessor(professor);
            ped.setObrigatoriedade(obrigatoriedade);
            ped.setAulas(aulas);
            if(PEDExiste(ped)){
                ped = null;
                System.gc();
                return false;
            }
            SalvarPED(ped);
            return true;
        } else {
            return false;
        }
    }
    //DB PED
    public void seConectarPED(){
        try{
            if(conectarPED == null || conectarPED.isClosed()){
                conectarPED = DriverManager.getConnection("jdbc:sqlite:bancoPED.db");
            }
            try (Statement stmt = conectarPED.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void criarTabelaPED(){
        String sql = """
                CREATE TABLE IF NOT EXISTS PED (
                    id INTEGER PRIMARY KEY AUTOINCREMENT, 
                    unidade TEXT NOT NULL,
                    id_professor INTEGER,
                    id_disciplina INTEGER,
                    curso TEXT NOT NULL,
                    semestre TEXT NOT NULL,
                    justificativa TEXT NOT NULL,
                    ementa TEXT NOT NULL,
                    objetivos TEXT NOT NULL,
                    metodologia TEXT NOT NULL,
                    atividadesDiscentes TEXT NOT NULL,
                    sistemaDeAvaliacao TEXT NOT NULL,
                    bibliografia TEXT NOT NULL,
                    obrigatoriedade TEXT NOT NULL,
                    aulas TEXT NOT NULL,
                    FOREIGN KEY (id_professor) REFERENCES professor(id)
                    FOREIGN KEY (id_disciplina) REFERENCES disciplina(id)
                    ON DELETE CASCADE
                    ON DELETE CASCADE
                )
                """;
        try (Statement state = conectarPED.createStatement()){
            state.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void SalvarPED(PED ped){
        String inserir = """
                INSERT INTO PED(
                unidade,
                professor,
                id_disciplina,
                curso,
                semestre,
                justificativa,
                ementa,
                objetivos,
                metodologia,
                atividadesDiscentes,
                sistemaDeAvaliacao,
                bibliografia,
                obrigatoriedade,
                aulas
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";
        try(PreparedStatement ps = conectarPED.prepareStatement(inserir)) {

            String aulasJson = null;
            if (ped.getAulas() != null && !ped.getAulas().isEmpty()) {
                aulasJson = gson.toJson(ped.getAulas());
            }

            ps.setString(1, ped.getUnidade());
            ps.setString(2, ped.getProfessor().getLogin());
            ps.setString(3, ped.getDisciplina().getCodigo());
            ps.setString(4, ped.getCurso());
            ps.setString(5, ped.getSemestre());
            ps.setString(6, ped.getJustificativa());
            ps.setString(7, ped.getEmenta());
            ps.setString(8, ped.getObjetivos());
            ps.setString(11,ped.getMetodologia());
            ps.setString(12,ped.getAtividadesDiscentes());
            ps.setString(13,ped.getSistemaDeAvaliacao());
            ps.setString(14,ped.getBibliografia());
            ps.setString(15, ped.getObrigatoriedade());
            ps.setString(16, aulasJson);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean PEDExiste(PED ped){
        String busca = "SELECT codigo, semestre FROM PED WHERE codigo = ? AND semestre = ?";

        try{
            PreparedStatement preparar = conectarPED.prepareStatement(busca);
            preparar.setString(3, ped.getDisciplina().getCodigo());
            preparar.setString(5, ped.getSemestre());
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
    public int idPED(PED ped){
        String busca = "SELECT codigo, semestre FROM PED WHERE codigo = ? AND semestre = ?";

        try{
            PreparedStatement preparar = conectarPED.prepareStatement(busca);
            preparar.setString(3, ped.getDisciplina().getCodigo());
            preparar.setString(5, ped.getSemestre());
            ResultSet resultado = preparar.executeQuery();

            if(resultado.next()){
                return resultado.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ArrayList<Disciplina> arrayDisciplinas(){
        ArrayList<Disciplina> disciplinas = new ArrayList<>();

        String selectSql = "SELECT " +
                "nome, codigo, cargaTeorica, cargaPratica, cargaEaD, " +
                "cargaExtensao, estruturaCurricular, preRequisito, coRequisito, " +
                "regimeDeOferta, equivalencias " +
                "FROM disciplina";

        try (PreparedStatement ps = conectarDisciplina.prepareStatement(selectSql);
             ResultSet rs = ps.executeQuery()) { //Executa a consulta e obtém o resultado

            while (rs.next()) {
                Disciplina disciplina = new Disciplina();

                disciplina.setNome(rs.getString("nome"));
                disciplina.setCodigo(rs.getString("codigo"));
                disciplina.setCargaTeorica(rs.getInt("cargaTeorica"));
                disciplina.setCargaPratica(rs.getInt("cargaPratica"));
                disciplina.setCargaEaD(rs.getInt("cargaEaD"));
                disciplina.setCargaExtensao(rs.getInt("cargaExtensao"));
                disciplina.setEstruturaCurricular(rs.getString("estruturaCurricular"));
                disciplina.setPreRequisitos(rs.getString("preRequisito"));
                disciplina.setCoRequisito(rs.getString("coRequisito"));
                disciplina.setRegimeDeOferta(rs.getString("regimeDeOferta"));
                disciplina.setEquivalencias(rs.getString("equivalencias"));

                disciplinas.add(disciplina);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return disciplinas;
    }

    public ArrayList<Aula> criarAula(String data, String descricao, int carga, int cargaTotal){
        Aula aula = new Aula();
        aula.setData(data);
        aula.setDescricao(descricao);
        aula.setCargaHoraria(carga);
        aulasTemp.add(aula);
        if(!addAula(aulasTemp, cargaTotal)){
            aulasTemp.remove(aula);
        }
        ArrayList<Aula> aulasRetorno =  aulasTemp;
        if(cargaHorariaCompleta(aulasRetorno, cargaTotal)){
            aulasTemp.clear();
        }
        return aulasRetorno;
    }

    public boolean addAula(ArrayList<Aula> aulas, int cargaHoraria){
        Aula aulaAdicionar = aulas.get(aulas.size()-1);
        for(Aula aula : aulas){
            cargaHoraria-=aula.getCargaHoraria();
            if(aula.getData().equals(aulaAdicionar.getData()) || cargaHoraria >= 0){
                return false;
            }
        }
        return true;
    }

    public boolean cargaHorariaCompleta(ArrayList<Aula> aulas, int cargaHoraria){
        for(Aula aula : aulas){
            cargaHoraria -= aula.getCargaHoraria();
        }
        return(!(cargaHoraria>0));
    }



    public ArrayList<PED> arrayPEDs(){
        ArrayList<PED> peds = new ArrayList<>();

        String selectSql = "SELECT " +
                "unidade, id_professor, id_disciplina, curso, semestre, justificativa, ementa, objetivos, metodologia,"+
                "atividadesDiscentes, sistemaDeAvaliacao, bibliografia, obrigatoriedade, aulas"+
                "FROM PED";

        try (PreparedStatement ps = conectarPED.prepareStatement(selectSql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PED ped = new PED();

                ped.setUnidade(rs.getString("unidade"));
                ped.setProfessor(pesquisaProfessorPorID(rs.getInt("id_professor")));
                ped.setDisciplina(pesquisaDisciplinaPorID(rs.getInt("id_disciplina")));
                ped.setCurso(rs.getString("curso"));
                ped.setSemestre(rs.getString("semestre"));
                ped.setJustificativa(rs.getString("justificativa"));
                ped.setEmenta(rs.getString("ementa"));
                ped.setObjetivos(rs.getString("objetivos"));
                ped.setMetodologia(rs.getString("metodologia"));
                ped.setAtividadesDiscentes(rs.getString("atividadesDiscentes"));
                ped.setSistemaDeAvaliacao(rs.getString("sistemaDeAvaliacao"));
                ped.setBibliografia(rs.getString("bibliografia"));
                ped.setObrigatoriedade(rs.getString("obrigatoriedade"));

                Type listType = new TypeToken<ArrayList<Aula>>(){}.getType();
                ArrayList<Aula> aulas = gson.fromJson(rs.getString("aulasJson"), listType);
                ped.setAulas(aulas);

                peds.add(ped);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peds;
    }
}