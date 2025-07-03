package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private static Model instancia;
    private List<Usuario> usuarios;
    private List<Disciplina> disciplinas;
    private List<PED> peds;
    private Usuario usuarioLogado;

    private static final String ARQUIVO_USUARIOS = "usuarios.dat";
    private static final String ARQUIVO_DISCIPLINAS = "disciplinas.dat";
    private static final String ARQUIVO_PEDS = "peds.dat";

    private Model() {
        usuarios = carregar(ARQUIVO_USUARIOS);
        disciplinas = carregar(ARQUIVO_DISCIPLINAS);
        peds = carregar(ARQUIVO_PEDS);
    }

    public static Model getInstancia() {
        if (instancia == null) instancia = new Model();
        return instancia;
    }

    // Serviços dos Usuários
    public boolean cadastrarUsuario(String nome, String senha) {
        for (Usuario u : usuarios) if (u.getNome().equals(nome)) return false;
        usuarios.add(new Usuario(nome, senha));
        salvar(usuarios, ARQUIVO_USUARIOS);
        return true;
    }

    public boolean autenticarUsuario(String nome, String senha) {
        for (Usuario u : usuarios) {
            if (u.getNome().equals(nome) && u.getSenha().equals(senha)) {
                usuarioLogado = u;
                return true;
            }
        }
        return false;
    }

    public Usuario getUsuarioLogado() { return usuarioLogado; }

    // Serviços das disciplinas
    public void adicionarDisciplina(String nome) {
        disciplinas.add(new Disciplina(nome));
        salvar(disciplinas, ARQUIVO_DISCIPLINAS);
    }

    public List<Disciplina> listarDisciplinas() { return disciplinas; }

    //Serviços das PEDs
    public void adicionarPED(String titulo, String descricao) {
        peds.add(new PED(titulo, descricao));
        salvar(peds, ARQUIVO_PEDS);
    }

    public List<PED> listarPEDs() { return peds; }

    // persirstir dados
    @SuppressWarnings("unchecked")
    private <T> List<T> carregar(String arquivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<T>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private <T> void salvar(List<T> lista, String arquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados no arquivo: " + arquivo);
        }
    }

    // Classes internas
    public static class Usuario implements Serializable {
        private String nome, senha;
        public Usuario(String nome, String senha) { this.nome = nome; this.senha = senha; }
        public String getNome() { return nome; }
        public String getSenha() { return senha; }
    }

    public static class Disciplina implements Serializable {
        private String nome;
        public Disciplina(String nome) { this.nome = nome; }
        public String getNome() { return nome; }
    }

    public static class PED implements Serializable {
        private String titulo, descricao;
        public PED(String titulo, String descricao) { this.titulo = titulo; this.descricao = descricao; }
        public String getTitulo() { return titulo; }
        public String getDescricao() { return descricao; }
    }
}