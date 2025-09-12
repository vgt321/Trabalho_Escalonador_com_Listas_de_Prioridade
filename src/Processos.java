public class Processos {
    private int id;
    private String nome;
    private int prioridade;
    private int ciclosNecessarios;
    private String recursosNecessarios;

    // Construtor
    public Processos(int id, String nome, int prioridade, int ciclosNecessarios, String recursosNecessarios) {
        this.id = id;
        this.nome = nome;
        this.prioridade = prioridade;
        this.ciclosNecessarios = ciclosNecessarios;
        this.recursosNecessarios = recursosNecessarios;
    }

    // Métodos Get
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public int getCiclosNecessarios() {
        return ciclosNecessarios;
    }

    public String getRecursosNecessarios() {
        return recursosNecessarios;
    }

    // Método Set para decrementar os ciclos
    public void setCiclosNecessarios(int ciclosNecessarios) {
        this.ciclosNecessarios = ciclosNecessarios;
    }
}