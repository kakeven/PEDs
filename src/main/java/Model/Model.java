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

    private static final String ARQUIVO_USUARIOS = "usuarios.txt";
    private static final String ARQUIVO_DISCIPLINAS = "disciplinas.txt";
    private static final String ARQUIVO_PEDS = "peds.txt";

    private Model() {
        usuarios = carregarUsuarios();
        disciplinas = carregarDisciplinas();
        peds = carregarPEDs();
    }

    public static Model getInstancia() {
        if (instancia == null) instancia = new Model();
        return instancia;
    }

    // serviços dos usuários
    public boolean cadastrarUsuario(String nome, String login, String senha) {
        for (Usuario u : usuarios) {
            if (u.getLogin().equals(login)) return false; // login já existe
        }
        usuarios.add(new Usuario(nome, login, senha));
        salvarUsuarios();
        return true;
    }

    public boolean autenticarUsuario(String login, String senha) {
        for (Usuario u : usuarios) {
            if (u.getLogin().equals(login) && u.getSenha().equals(senha)) {
                usuarioLogado = u;
                return true;
            }
        }
        return false;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    // serviço das disciplinas
    public void adicionarDisciplina(String nome) {
        disciplinas.add(new Disciplina(nome));
        salvarDisciplinas();
    }

    public List<Disciplina> listarDisciplinas() {
        return disciplinas;
    }

    // serviço das PEDs
    public void adicionarPED(String titulo, String descricao) {
        peds.add(new PED(titulo, descricao));
        salvarPEDs();
    }

    public List<PED> listarPEDs() {
        return peds;
    }

    // persistência dos dados
    private List<Usuario> carregarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_USUARIOS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 3) lista.add(new Usuario(partes[0], partes[1], partes[2]));
            }
        } catch (IOException e) { }
        return lista;
    }

    private void salvarUsuarios() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_USUARIOS))) {
            for (Usuario u : usuarios) {
                pw.println(u.getNome() + ";" + u.getLogin() + ";" + u.getSenha());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários.");
        }
    }

    private List<Disciplina> carregarDisciplinas() {
        List<Disciplina> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_DISCIPLINAS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                lista.add(new Disciplina(linha));
            }
        } catch (IOException e) { }
        return lista;
    }

    private void salvarDisciplinas() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_DISCIPLINAS))) {
            for (Disciplina d : disciplinas) pw.println(d.getNome());
        } catch (IOException e) {
            System.out.println("Erro ao salvar disciplinas.");
        }
    }

    private List<PED> carregarPEDs() {
        List<PED> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_PEDS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 2) lista.add(new PED(partes[0], partes[1]));
            }
        } catch (IOException e) { }
        return lista;
    }

    private void salvarPEDs() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_PEDS))) {
            for (PED p : peds) pw.println(p.getTitulo() + ";" + p.getDescricao());
        } catch (IOException e) {
            System.out.println("Erro ao salvar PEDs.");
        }
    }

    // Classes internas
    public static class Usuario {
        private String nome, login, senha;
        public Usuario(String nome, String login, String senha) {
            this.nome = nome;
            this.login = login;
            this.senha = senha;
        }
        public String getNome() {
            return nome;
        }
        public String getLogin() {
            return login;
        }
        public String getSenha() {
            return senha;
        }
    }

    public static class Disciplina {
        private String nome;
        public Disciplina(String nome) {
            this.nome = nome;
        }
        public String getNome() {
            return nome;
        }
    }

    public static class PED {
        private String titulo, descricao;
        public String getTitulo() {
            return titulo;
        }
        public String getDescricao() {
            return descricao;
        }

        public PED(String titulo, String descricao) {
            this.titulo = titulo;
            this.descricao = descricao;
        }
    }
}