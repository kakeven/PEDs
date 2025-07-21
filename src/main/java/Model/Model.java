package Model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import org.apache.poi.xwpf.usermodel.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.sql.*;
import java.util.*;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;
import java.util.List;
import java.io.File;

import org.apache.xmlbeans.XmlCursor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.BodyType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.BodyType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.util.function.Supplier;


public class Model {
    private Connection conectar;
    private Connection conectarUsuario;
    private Disciplina disciplina;
    private Gson gson;
    private Professor professorAtual = new Professor();
    private PED pedAtual = new PED();
    private ArrayList<Aula> aulasTemp;
    private ArrayList<PED> arrayPEDsTemp;

    //construtor
    public Model() {
        this.gson = new Gson();
        seConectarUsuario();
        criarTabelaUsuario();
        criarTabelaDisciplina();
        criarTabelaPED();
        this.disciplina = new Disciplina();
        this.aulasTemp = new ArrayList<>();
        //this
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

    public int getCargaTotal(Object disciplina) {
        if (disciplina != null && disciplina instanceof Disciplina disciplina1) {
            return disciplina1.getCargaTotal();
        }
        return 0;
    }

    //metodos DB
    //DB Usuário
    public boolean verificarUsuario(String nome, String login, String senha) {
        if (senha.length() >= 8 && (!nome.isBlank() && !login.isBlank() && !senha.isBlank()) && !LoginExiste(login)) {
            Professor professor = new Professor(nome, login, senha);
            SalvarUsuario(professor);
            return true;
        }
        return false;
    }

    public void seConectarUsuario() {
        try {
            if (conectarUsuario == null || conectarUsuario.isClosed()) {
                conectarUsuario = DriverManager.getConnection("jdbc:sqlite:bancoUsuarios.db");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void criarTabelaUsuario() {
        String sql = """
                CREATE TABLE IF NOT EXISTS usuarios (
                    id INTEGER PRIMARY KEY AUTOINCREMENT, 
                    nome TEXT NOT NULL,
                    login TEXT NOT NULL,
                    senha TEXT NOT NULL
                )
                """;
        try (Statement state = conectarUsuario.createStatement()) {
            state.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void SalvarUsuario(Professor professor) {
        String inserir = "INSERT INTO usuarios(nome, login, senha) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conectarUsuario.prepareStatement(inserir)) {
            ps.setString(1, professor.getNome());
            ps.setString(2, professor.getLogin());
            ps.setString(3, professor.getSenha());
            ps.executeUpdate();
            professorAtual.setNome(professor.getNome());
            professorAtual.setLogin(professor.getLogin());
            professorAtual.setSenha(professor.getSenha());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean LoginExiste(String login) {//serve para nao cadastrar dois usuarios com mesmo login
        String busca = "SELECT login FROM usuarios WHERE login = ?";

        try {
            PreparedStatement preparar = conectarUsuario.prepareStatement(busca);
            preparar.setString(1, login);
            ResultSet resultado = preparar.executeQuery();

            if (resultado.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean LoginValido(String login, String senha) {
        String busca = "SELECT nome, login, senha FROM usuarios WHERE login = ?";

        try {
            PreparedStatement preparar = conectarUsuario.prepareStatement(busca);
            preparar.setString(1, login);
            ResultSet resultado = preparar.executeQuery();

            if (resultado.next()) {
                String senhaDB = resultado.getString("senha");//pega a senha do BANCO
                String nomeProfessor = resultado.getString("nome");

                if (senhaDB.equals(senha)) {
                    professorAtual = new Professor(nomeProfessor, login, senha);
                    return true;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int CalcularEstatistica() {
        return 0;
    }

    public void setProfessorAtual(Professor professor) {
        this.professorAtual = professor;
    }

    public String getProfessorAtual() {
        return professorAtual.getNome();
    }

    public Professor pesquisaProfessorPorID(int id) {
        String busca = "SELECT login, nome, senha FROM usuarios WHERE id = ?";

        try {
            PreparedStatement preparar = conectarUsuario.prepareStatement(busca);
            preparar.setInt(1, id);
            ResultSet rs = preparar.executeQuery();

            if (rs.next()) {
                Professor professor = new Professor();
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

    public int idProfessor(Professor professor) {
        String busca = "SELECT id FROM usuarios WHERE login = ?";

        try {
            PreparedStatement preparar = conectarUsuario.prepareStatement(busca);
            preparar.setString(1, professor.getLogin());
            ResultSet resultado = preparar.executeQuery();
            if (resultado.next()) {
                return resultado.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            String estruturaCurricular,
            String preRequisitos,
            String coRequisito,
            String regimeDeOferta,
            String equivalencias
    ) {
        if (
                !nome.isBlank() && nome != null
                        && !codigo.isBlank() && codigo != null
                        && !estruturaCurricular.isBlank() && estruturaCurricular != null
                        && !regimeDeOferta.isBlank() && regimeDeOferta != null
        ) {
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
            if (DisciplinaExiste(disciplina) || disciplina.getCargaTotal() == 0) {
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
    public void criarTabelaDisciplina() {
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
        try (Statement state = conectarUsuario.createStatement()) {
            state.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void SalvarDisciplina(Disciplina disciplina) {
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
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";
        try (PreparedStatement ps = conectarUsuario.prepareStatement(inserir)) {
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

    public boolean DisciplinaExiste(Disciplina disciplina) {
        String busca = "SELECT codigo FROM disciplina WHERE codigo = ?";

        try {
            PreparedStatement preparar = conectarUsuario.prepareStatement(busca);
            preparar.setString(1, disciplina.getCodigo());
            ResultSet resultado = preparar.executeQuery();

            if (resultado.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int idDisciplina(Disciplina disciplina) {
        String busca = "SELECT id FROM disciplina WHERE codigo = ?";

        try {
            PreparedStatement preparar = conectarUsuario.prepareStatement(busca);
            preparar.setString(1, disciplina.getCodigo());
            ResultSet resultado = preparar.executeQuery();

            if (resultado.next()) {
                return resultado.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean pesquisaDisciplinaPorCodigo(String codigo) {
        String busca = "SELECT codigo FROM disciplina WHERE codigo = ?";

        try {
            PreparedStatement preparar = conectarUsuario.prepareStatement(busca);
            preparar.setString(1, codigo);
            ResultSet resultado = preparar.executeQuery();

            if (resultado.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Disciplina pesquisaDisciplinaPorID(int id) {
        String busca = """
                SELECT nome, codigo, cargaTeorica, cargaPratica, cargaEaD,
                cargaExtensao, estruturaCurricular, preRequisito,coRequisito,
                regimeDeOferta, equivalencias 
                FROM disciplina WHERE id = ?""";

        try {
            PreparedStatement preparar = conectarUsuario.prepareStatement(busca);
            preparar.setInt(1, id);
            ResultSet rs = preparar.executeQuery();

            if (rs.next()) {
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
            Object disciplinaObj,
            String curso,
            String semestre,
            String justificativa,
            String ementa,
            String objetivos,
            String metodologia,
            String atividadesDiscentes,
            String sistemaDeAvaliacao,
            String bibliografia,
            String obrigatoriedade
    ) {
        if (
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
            //  && (cargaHorariaCompleta(aulasTemp, disciplina.getCargaTotal()))
        ) {
            Disciplina disciplinaAdd = new Disciplina();
            if (disciplinaObj != null && disciplinaObj instanceof Disciplina disciplinatst) {
                disciplinaAdd = disciplinatst;
            }
            if ((!cargaHorariaCompleta(aulasTemp, disciplinaAdd.getCargaTotal()))) {
                return false;
            }

            PED ped = new PED();
            ped.setUnidade(unidade);
            ped.setDisciplina(disciplinaAdd);
            ped.setCurso(curso);
            ped.setSemestre(semestre);
            ped.setJustificativa(justificativa);
            ped.setEmenta(ementa);
            ped.setObjetivos(objetivos);
            ped.setMetodologia(metodologia);
            ped.setAtividadesDiscentes(atividadesDiscentes);
            ped.setSistemaDeAvaliacao(sistemaDeAvaliacao);
            ped.setBibliografia(bibliografia);
            ped.setProfessor(professorAtual);
            ped.setObrigatoriedade(obrigatoriedade);
            ped.setAulas(aulasTemp);
            //aulasTemp.clear();
            if (PEDExiste(ped)) {
                ped = null;
                System.gc();
                return false;
            }
            SalvarPED(ped);
            pedAtual = ped;
            return true;
        } else {
            return false;
        }
    }

    //DB PED
    public void criarTabelaPED() {
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
                    aulas,
                    FOREIGN KEY (id_professor) REFERENCES professor(id)
                    FOREIGN KEY (id_disciplina) REFERENCES disciplina(id)
                    ON DELETE CASCADE
                    ON DELETE CASCADE
                )
                """;
        try (Statement state = conectarUsuario.createStatement()) {
            state.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void SalvarPED(PED ped) {
        String inserir = """
                INSERT INTO PED(
                unidade,
                id_professor,
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
        try (PreparedStatement ps = conectarUsuario.prepareStatement(inserir)) {

            String aulasJson = null;
            if (ped.getAulas() != null && !ped.getAulas().isEmpty()) {
                aulasJson = gson.toJson(ped.getAulas());
            }
            aulasTemp.clear();

            ps.setString(1, ped.getUnidade());
            ps.setInt(2, (idProfessor(ped.getProfessor())));
            ps.setInt(3, idDisciplina(ped.getDisciplina()));
            ps.setString(4, ped.getCurso());
            ps.setString(5, ped.getSemestre());
            ps.setString(6, ped.getJustificativa());
            ps.setString(7, ped.getEmenta());
            ps.setString(8, ped.getObjetivos());
            ps.setString(9, ped.getMetodologia());
            ps.setString(10, ped.getAtividadesDiscentes());
            ps.setString(11, ped.getSistemaDeAvaliacao());
            ps.setString(12, ped.getBibliografia());
            ps.setString(13, ped.getObrigatoriedade());
            ps.setString(14, aulasJson);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean PEDExiste(PED ped) {
        String busca = "SELECT curso, semestre FROM PED WHERE curso = ? AND semestre = ?";

        try {
            PreparedStatement preparar = conectarUsuario.prepareStatement(busca);
            preparar.setString(1, ped.getCurso());
            preparar.setString(2, ped.getSemestre());
            ResultSet resultado = preparar.executeQuery();

            if (resultado.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int idPED(PED ped) {
        String busca = "SELECT id FROM PED WHERE id_disciplina = ? AND semestre = ?";

        try {
            PreparedStatement preparar = conectarUsuario.prepareStatement(busca);
            preparar.setInt(1, idDisciplina(ped.getDisciplina()));
            preparar.setString(2, ped.getSemestre());
            ResultSet resultado = preparar.executeQuery();
            if (resultado.next()) {
                //System.out.println(resultado.getInt("id"));
                return resultado.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public PED pesquisaPEDPorID(int id){

        String busca = """
                SELECT id, unidade, id_professor, id_disciplina, curso, semestre, justificativa, ementa, objetivos, metodologia,
                atividadesDiscentes, sistemaDeAvaliacao, bibliografia, obrigatoriedade, aulas 
                FROM PED WHERE id = ?""";


        try {
            PreparedStatement preparar = conectarUsuario.prepareStatement(busca);
            preparar.setInt(1, id);
            ResultSet rs = preparar.executeQuery();

            if (rs.next()) {
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

                Type listType = new TypeToken<ArrayList<Aula>>() {
                }.getType();
                String aulasJson = rs.getString("aulas");
                if (aulasJson != null && !aulasJson.isEmpty()) {
                    ArrayList<Aula> aulas = gson.fromJson(aulasJson, listType);
                    ped.setAulas(aulas);
                } else {
                    ped.setAulas(new ArrayList<>());
                }

                return ped;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public ArrayList<Disciplina> arrayDisciplinas() {
        ArrayList<Disciplina> disciplinas = new ArrayList<>();

        String selectSql = "SELECT " +
                "nome, codigo, cargaTeorica, cargaPratica, cargaEaD, " +
                "cargaExtensao, estruturaCurricular, preRequisito, coRequisito, " +
                "regimeDeOferta, equivalencias " +
                "FROM disciplina";

        try (PreparedStatement ps = conectarUsuario.prepareStatement(selectSql);
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

    public boolean criarAula(String data, String descricao, int carga, int cargaTotal, String dataNormal) {
        if (data.isBlank() || descricao.isBlank()) {
            return false;
        }
        Aula aula = new Aula();
        aula.setDataFormatada(data);
        aula.setDescricao(descricao);
        aula.setDataNormal(dataNormal);
        aula.setCargaHoraria(carga);
        aulasTemp.add(aula);
        if (!addAula(aulasTemp, cargaTotal)) {
            aulasTemp.remove(aula);
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<String> getArraySemestrePEDs() {
        ArrayList<String> semestres = new ArrayList<>();
        for (PED ped : arrayPEDsTemp) {
            semestres.add(ped.getSemestre());
        }
        return semestres;
    }

    public ArrayList<String> getArrayCursoPEDs() {
        ArrayList<String> curso = new ArrayList<>();
        for (PED ped : arrayPEDsTemp) {
            curso.add(ped.getCurso());
        }
        return curso;
    }

    public ArrayList<String> getArrayDisciplinaPEDs() {
        ArrayList<String> disciplina = new ArrayList<>();
        for (PED ped : arrayPEDsTemp) {
            disciplina.add(ped.getDisciplina().getNome());
        }
        return disciplina;
    }

    public ArrayList<Integer> getArrayIdPEDs() {
        ArrayList<Integer> id = new ArrayList<>();
        for (PED ped : arrayPEDsTemp) {
            id.add(this.idPED(ped));
        }
        return id;
    }

    public void resetArrayPEDsTemp() {
        arrayPEDsTemp.clear();
    }

    public boolean addAula(ArrayList<Aula> aulas, int cargaHoraria) {
        Aula aulaAdicionar = aulas.get(aulas.size() - 1);
        for (Aula aula : aulas) {
            cargaHoraria -= aula.getCargaHoraria();
            if (aulaAdicionar != aula && (aula.getDataFormatada().equals(aulaAdicionar.getDataFormatada())) || cargaHoraria < 0) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Aula> getAulasTemp() {
        return aulasTemp;
    }

    public void resetAulasTemp() {
        aulasTemp.clear();
    }

    public ArrayList<String> getAulasLista() {
        ArrayList<String> listaAulas = new ArrayList<>();
        aulasTemp = ordenarAulas(aulasTemp);
        for (Aula aulas : aulasTemp) {
            listaAulas.add(aulas.getDescricao() + " em " + aulas.getDataFormatada() + " Com " + aulas.getCargaHoraria() + " horas de duração");
        }
        return listaAulas;
    }

    public ArrayList<Aula> ordenarAulas(ArrayList<Aula> aulas){
        ArrayList<Aula> aulasReturn = new ArrayList<>();
        aulasReturn.add(aulas.get(0));
        aulas.sort(Comparator.comparingInt(aula -> (Integer.parseInt(aula.getDataNormal().substring(0,2))+100*Integer.parseInt(aula.getDataNormal().substring(3,5))+10000*Integer.parseInt(aula.getDataNormal().substring(6)))));
       // System.out.println(aulas);
        return aulas;
    }

    public boolean cargaHorariaCompleta(ArrayList<Aula> aulas, int cargaHoraria) {
        for (Aula aula : aulas) {
            cargaHoraria -= aula.getCargaHoraria();
        }
        return (!(cargaHoraria > 0));
    }

    public ArrayList<PED> arrayPEDs() {
        ArrayList<PED> peds = new ArrayList<>();

        int idDoProfessorAtual = 0;
        if (professorAtual != null) {
            idDoProfessorAtual = idProfessor(professorAtual);
        } else {
            System.err.println("Nenhum professor logado. Não é possível carregar PEDs.");
            this.arrayPEDsTemp = peds;
            return peds;
        }

        String selectSql = "SELECT " +
                "id, unidade, id_professor, id_disciplina, curso, semestre, justificativa, ementa, objetivos, metodologia," +
                "atividadesDiscentes, sistemaDeAvaliacao, bibliografia, obrigatoriedade, aulas " +
                "FROM PED WHERE id_professor = ?";

        try (PreparedStatement ps = conectarUsuario.prepareStatement(selectSql)) {
            ps.setInt(1, idDoProfessorAtual);
            try (ResultSet rs = ps.executeQuery()) {
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

                    Type listType = new TypeToken<ArrayList<Aula>>() {
                    }.getType();
                    String aulasJson = rs.getString("aulas");
                    if (aulasJson != null && !aulasJson.isEmpty()) {
                        ArrayList<Aula> aulas = gson.fromJson(aulasJson, listType);
                        ped.setAulas(aulas);
                    } else {
                        ped.setAulas(new ArrayList<>());
                    }

                    peds.add(ped);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.arrayPEDsTemp = peds;
        return peds;
    }
    public ArrayList<ArrayList<String>> getPedsParaTableView(){
        ArrayList<ArrayList<String>> pedsParaView = new ArrayList<>();
        ArrayList<PED> pedsDoBanco = arrayPEDs();

        for (PED ped : pedsDoBanco) {
            ArrayList<String> row = new ArrayList<>();
            row.add(String.valueOf(idPED(ped)));
            row.add(ped.getSemestre());
            row.add(ped.getCurso());
            row.add(ped.getDisciplina() != null ? ped.getDisciplina().getNome() : "N/A"); // Nome da Disciplina
            pedsParaView.add(row);
        }
        //System.out.println(pedsParaView);
        return pedsParaView;
    }
    public ArrayList<ArrayList<String>> getDisciplinasParaTableView(){
        ArrayList<ArrayList<String>> disciplinasParaView = new ArrayList<>();
        ArrayList<Disciplina> disciplinasBanco = arrayDisciplinas();

        for (Disciplina disciplina : disciplinasBanco) {
           // System.out.println(disciplina.getCodigo());
            ArrayList<String> row = new ArrayList<>();
            row.add(disciplina.getNome());
            row.add(disciplina.getCodigo());
            disciplinasParaView.add(row);
        }
        //System.out.println(pedsParaView);
        return disciplinasParaView;
    }

    public ArrayList<PED> getPedsCarregados() {
        return this.arrayPEDsTemp;
    }

    public void setPedAtual(PED ped){
        pedAtual = ped;
    }
    public void gerarDocx() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar PED como...");

        FileNameExtensionFilter docxFilter = new FileNameExtensionFilter("Documentos Word (*.docx)", "docx");
        fileChooser.addChoosableFileFilter(docxFilter);
        fileChooser.setFileFilter(docxFilter);

        String suggestedFileName = "PED_" + pedAtual.getDisciplina().getNome() + "_" + pedAtual.getSemestre() + ".docx";
        fileChooser.setSelectedFile(new File(suggestedFileName));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".docx")) {
                fileToSave = new File(filePath + ".docx");
            }

            try (InputStream fis = getClass().getClassLoader().getResourceAsStream("modeloPED.docx");
                 XWPFDocument doc = new XWPFDocument(fis);
                 FileOutputStream fos = new FileOutputStream(fileToSave)) {

                // Processar tabelas
                for (XWPFTable table : doc.getTables()) {
                    if (table.getRows().size() > 0 && table.getRow(0).getTableCells().size() >= 3) {
                        String cellText = table.getRow(0).getCell(0).getText();
                        if (cellText != null && cellText.toLowerCase().contains("data")) {
                            while (table.getNumberOfRows() > 1) {
                                table.removeRow(1);
                            }

                            XWPFTableRow headerRow = table.getRow(0);

                            for(int i = 1; i<=pedAtual.getAulas().size(); i++) {
                                XWPFTableRow row = table.insertNewTableRow(i);

                                for (int j = 0; j < 3; j++) {
                                    XWPFTableCell cell = row.createCell();

                                    CTTcPr tcPr = cell.getCTTc().addNewTcPr();
                                    CTTcBorders borders = tcPr.addNewTcBorders();

                                    borders.addNewTop().setVal(STBorder.NONE);
                                    borders.addNewBottom().setVal(STBorder.NONE);
                                    borders.addNewLeft().setVal(STBorder.NONE);
                                    borders.addNewRight().setVal(STBorder.NONE);

                                }
                            }
                            int rowIndice =1;
                            // Adicionar linhas de dados
                            for (Aula aula : pedAtual.getAulas()) {
                                table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");
                                table.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");
                                table.setTopBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");
                                table.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");
                                table.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");
                                table.setRightBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");

                                XWPFTableRow newRow = table.getRow(rowIndice);


                                newRow.getCell(0).setText(aula.getDataNormal());
                                newRow.getCell(1).setText(aula.getDescricao());
                                if(aula.getCargaHoraria()==0){
                                    newRow.getCell(2).setText("--");
                                }else{
                                    newRow.getCell(2).setText(String.valueOf(aula.getCargaHoraria())+"h");
                                }

                                for (int i = 0; i < 3; i++) {
                                    if (headerRow.getCell(i).getCTTc().getTcPr() != null) {
                                        newRow.getCell(i).getCTTc().setTcPr(headerRow.getCell(i).getCTTc().getTcPr());
                                    }
                                }
                                rowIndice++;

                                for (XWPFTableRow row : table.getRows()) {
                                    for (XWPFTableCell cell : row.getTableCells()) {

                                        CTTcPr tcPr = cell.getCTTc().isSetTcPr() ? cell.getCTTc().getTcPr() : cell.getCTTc().addNewTcPr();

                                        CTTcBorders borders = tcPr.isSetTcBorders() ? tcPr.getTcBorders() : tcPr.addNewTcBorders();

                                        borders.setTop(CTBorder.Factory.newInstance());
                                        borders.getTop().setVal(STBorder.NONE);

                                        borders.setBottom(CTBorder.Factory.newInstance());
                                        borders.getBottom().setVal(STBorder.NONE);

                                        borders.setLeft(CTBorder.Factory.newInstance());
                                        borders.getLeft().setVal(STBorder.NONE);

                                        borders.setRight(CTBorder.Factory.newInstance());
                                        borders.getRight().setVal(STBorder.NONE);

                                    }
                                }

                                for (XWPFTableCell cell : newRow.getTableCells()) {
                                    CTTc ctTc = cell.getCTTc();
                                    CTTcPr tcPr = ctTc.isSetTcPr() ? ctTc.getTcPr() : ctTc.addNewTcPr();
                                    CTShd shd = tcPr.isSetShd() ? tcPr.getShd() : tcPr.addNewShd();

                                    shd.setFill("FFFFFF");
                                    shd.setVal(STShd.CLEAR);
                                    shd.setColor("auto");
                                }

                            }
                            int[] larguras = {2000, 8000, 2000};
                            for (int i = 0; i < 3; i++) {
                                XWPFTableCell cell = headerRow.getCell(i);
                                if (cell != null) {
                                    CTTcPr tcPr = cell.getCTTc().isSetTcPr() ? cell.getCTTc().getTcPr() : cell.getCTTc().addNewTcPr();
                                    CTTblWidth cellWidth = tcPr.isSetTcW() ? tcPr.getTcW() : tcPr.addNewTcW();
                                    cellWidth.setType(STTblWidth.DXA);
                                    cellWidth.setW(BigInteger.valueOf(larguras[i]));
                                }
                            }
                            for (XWPFTableRow row : table.getRows()) {
                                while (row.getTableCells().size() < 3) {
                                    row.createCell();
                                }

                                for (XWPFTableCell cell : row.getTableCells()) {
                                    CTTcPr tcPr = cell.getCTTc().isSetTcPr() ? cell.getCTTc().getTcPr() : cell.getCTTc().addNewTcPr();
                                    CTTcBorders borders = tcPr.isSetTcBorders() ? tcPr.getTcBorders() : tcPr.addNewTcBorders();

                                    CTBorder top = borders.isSetTop() ? borders.getTop() : borders.addNewTop();
                                    CTBorder bottom = borders.isSetBottom() ? borders.getBottom() : borders.addNewBottom();
                                    CTBorder left = borders.isSetLeft() ? borders.getLeft() : borders.addNewLeft();
                                    CTBorder right = borders.isSetRight() ? borders.getRight() : borders.addNewRight();

                                    top.setVal(STBorder.NONE);
                                    bottom.setVal(STBorder.NONE);
                                    left.setVal(STBorder.NONE);
                                    right.setVal(STBorder.NONE);

                                    CTShd shd = tcPr.isSetShd() ? tcPr.getShd() : tcPr.addNewShd();
                                    shd.setFill("FFFFFF");
                                    shd.setVal(STShd.CLEAR);
                                    shd.setColor("auto");
                                }

                                for (XWPFTableCell cell : headerRow.getTableCells()) {
                                    CTTcPr tcPr = cell.getCTTc().isSetTcPr() ? cell.getCTTc().getTcPr() : cell.getCTTc().addNewTcPr();
                                    CTShd shd = tcPr.isSetShd() ? tcPr.getShd() : tcPr.addNewShd();

                                    shd.setFill("D3D3D3");
                                    shd.setVal(STShd.CLEAR);
                                    shd.setColor("auto");
                                }
                            }
                            break;
                        }
                    }
                }


                for (XWPFTable table : doc.getTables()) {
                    for (XWPFTableRow row : table.getRows()) {
                        for (XWPFTableCell cell : row.getTableCells()) {
                            for (XWPFParagraph para : cell.getParagraphs()) {
                                preencherCampoDocSimplesOuHtml(para, doc, cell);
                            }
                        }
                    }
                }

                doc.write(fos);
                JOptionPane.showMessageDialog(null, "Documento preenchido e salvo com sucesso em:\n" + fileToSave.getAbsolutePath(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                fos.close();
                doc.close();
                fis.close();

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao processar ou salvar o documento:\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                System.err.println("Erro ao processar o documento: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operação de salvar cancelada pelo usuário.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void preencherCampoDocSimplesOuHtml(XWPFParagraph para, XWPFDocument doc, XWPFTableCell parentCell) {
        String fullParagraphText = para.getText();
        if (fullParagraphText == null || !fullParagraphText.contains("${")) {
            return;
        }

        Map<String, Supplier<String>> camposMap = new HashMap<>();
        camposMap.put("${semestre}", pedAtual::getSemestre);
        camposMap.put("${unidade}", pedAtual::getUnidade);
        camposMap.put("${curso}", pedAtual::getCurso);
        camposMap.put("${estruturaCurricular}", () -> pedAtual.getDisciplina().getEstruturaCurricular());
        camposMap.put("${nomeDisciplina}", () -> pedAtual.getDisciplina().getNome());
        camposMap.put("${codigoDisciplina}", () -> pedAtual.getDisciplina().getCodigo());
        camposMap.put("${obrigatoriedade}", pedAtual::getObrigatoriedade);
        camposMap.put("${regimeDeOferta}", () -> pedAtual.getDisciplina().getRegimeDeOferta());
        camposMap.put("${cargaTotal}", () -> String.valueOf(pedAtual.getDisciplina().getCargaTotal()) + "h");
        camposMap.put("${cargaTeorica}", () -> String.valueOf(pedAtual.getDisciplina().getCargaTeorica()));
        camposMap.put("${cargaPratica}", () -> String.valueOf(pedAtual.getDisciplina().getCargaPratica()));
        camposMap.put("${cargaEad}", () -> String.valueOf(pedAtual.getDisciplina().getCargaEaD()));
        camposMap.put("${cargaExtensao}", () -> String.valueOf(pedAtual.getDisciplina().getCargaExtensao()));
        camposMap.put("${preRequisitos}", () -> pedAtual.getDisciplina().getPreRequisito());
        camposMap.put("${coRequisitos}", () -> pedAtual.getDisciplina().getCoRequisito());
        camposMap.put("${equivalencias}", () -> pedAtual.getDisciplina().getEquivalencias());
        camposMap.put("${professor}", () -> pedAtual.getProfessor().getNome());
        camposMap.put("${justificativa}", pedAtual::getJustificativa);
        camposMap.put("${ementa}", pedAtual::getEmenta);
        camposMap.put("${objetivos}", pedAtual::getObjetivos);
        camposMap.put("${metodologia}", pedAtual::getMetodologia);
        camposMap.put("${atividadesDiscentes}", pedAtual::getAtividadesDiscentes);
        camposMap.put("${sistemaDeAvaliacao}", pedAtual::getSistemaDeAvaliacao);
        camposMap.put("${bibliografia}", pedAtual::getBibliografia);

        Set<String> camposHtml = new HashSet<>();
        camposHtml.add("${justificativa}");
        camposHtml.add("${ementa}");
        camposHtml.add("${objetivos}");
        camposHtml.add("${metodologia}");
        camposHtml.add("${atividadesDiscentes}");
        camposHtml.add("${sistemaDeAvaliacao}");
        camposHtml.add("${bibliografia}");

        boolean htmlProcessed = false;

        for (Map.Entry<String, Supplier<String>> entry : camposMap.entrySet()) {
            String marcador = entry.getKey();
            if (fullParagraphText.contains(marcador)) {
                if (camposHtml.contains(marcador)) {
                    String htmlContent = entry.getValue().get();

                    if (htmlContent == null || htmlContent.trim().isEmpty()) {
                        while (para.getRuns().size() > 0) {
                            para.removeRun(0);
                        }
                        para.createRun().setText("");
                        htmlProcessed = true;
                        continue;
                    }

                    inserirConteudoHtml(para, htmlContent, doc, parentCell);
                    htmlProcessed = true;
                    break;
                } else {
                    String valor = entry.getValue().get();
                    fullParagraphText = fullParagraphText.replace(marcador, valor);
                }
            }
        }

        if (!htmlProcessed) {
            while (para.getRuns().size() > 0) {
                para.removeRun(0);
            }
            XWPFRun newRun = para.createRun();
            newRun.setText(fullParagraphText);
        }
    }

    private void inserirConteudoHtml(XWPFParagraph targetParagraph, String htmlContent, XWPFDocument doc, XWPFTableCell parentCell) {
        while (targetParagraph.getRuns().size() > 0) {
            targetParagraph.removeRun(0);
        }

        Document htmlDoc = Jsoup.parse(htmlContent);

        Elements blockElements = htmlDoc.body().children();

        if (blockElements.isEmpty() && !htmlDoc.body().text().isEmpty()) {
            processarElementosInline(targetParagraph, htmlDoc.body());
        } else {
            boolean firstBlock = true;
            for (Element element : blockElements) {
                XWPFParagraph currentParagraph;
                if (firstBlock) {
                    currentParagraph = targetParagraph;
                    firstBlock = false;
                } else {
                    if (parentCell != null) {
                        currentParagraph = parentCell.addParagraph();
                    } else {
                        currentParagraph = doc.createParagraph();
                    }
                }
                processarHtmlBlock(currentParagraph, element);
            }
        }
    }

    private static void processarHtmlBlock(XWPFParagraph paragraph, Element element) {
        if (element.tagName().equals("ul") || element.tagName().equals("ol")) {
            for (Element li : element.select("li")) {
                XWPFRun run = paragraph.createRun();
                if (element.tagName().equals("ul")) {
                    run.setText("• " + li.text());
                } else {
                    run.setText(element.children().indexOf(li) + 1 + ". " + li.text());
                }
                run.addBreak();
            }
        } else if (element.tagName().equals("p") || element.tagName().equals("div")) {
            processarElementosInline(paragraph, element);
        } else {
            processarElementosInline(paragraph, element);
        }
    }

    private static void processarElementosInline(XWPFParagraph paragraph, Element element) {
        for (Node node : element.childNodes()) {
            if (node instanceof Element) {
                Element childElement = (Element) node;
                XWPFRun run = paragraph.createRun();

                if (childElement.tagName().equals("b") || childElement.tagName().equals("strong")) {
                    run.setBold(true);
                } else if (childElement.tagName().equals("i") || childElement.tagName().equals("em")) {
                    run.setItalic(true);
                } else if (childElement.tagName().equals("a")) {
                    run.setColor("0000FF");
                    run.setUnderline(UnderlinePatterns.SINGLE);
                }

                if (!childElement.ownText().isEmpty()) {
                    run.setText(childElement.ownText());
                }
                processarElementosInline(paragraph, childElement);

            } else if (node instanceof TextNode) {
                XWPFRun run = paragraph.createRun();
                run.setText(((TextNode) node).text());
            }
        }
    }
}