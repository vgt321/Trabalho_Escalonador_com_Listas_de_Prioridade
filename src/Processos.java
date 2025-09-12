// Classe Processos: representa um processo do sistema


public class Processos {
    // Atributos (informações do processo)
    private int id;                   // Identificação única
    private String nome;              // Nome do processo
    private int prioridade;           // Prioridade (1 = alta, 2 = média, 3 = baixa)
    private int ciclosNecessarios;    // Quantidade de ciclos que precisa
    private String recursosNecessarios; // Recurso usado (ex: Impressora, Disco, Rede)

    // Construtor: cria um processo já com todos os atributos
    public Processos(int id, String nome, int prioridade, int ciclosNecessarios, String recursosNecessarios) {
        this.id = id;
        this.nome = nome;
        setPrioridade(prioridade); // usa o set para validar
        this.ciclosNecessarios = ciclosNecessarios;
        this.recursosNecessarios = recursosNecessarios;
    }

    // Métodos GET: usados para acessar os valores
    public int getid() {
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

    // Métodos SET: usados para alterar os valores
    public void setid(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Valida se a prioridade está entre 1 e 3
    public void setPrioridade(int prioridade) {
        if (prioridade < 1 || prioridade > 3) {
            System.out.println("Prioridade inválida! Definindo como 1");
            this.prioridade = 1; // valor padrão
        } else {
            this.prioridade = prioridade;
        }
    }

    public void setCiclosNecessarios(int ciclosNecessarios) {
        this.ciclosNecessarios = ciclosNecessarios;
    }

    public void setRecursosNecessarios(String recursosNecessarios) {
        this.recursosNecessarios = recursosNecessarios;
    }

    // toString: mostra as informações do processo em formato de texto
    @Override
    public String toString() {
        String texto = "Processos [";
        texto = texto + "ID = " + id;
        texto = texto + ", Nome = " + nome;
        texto = texto + ", Prioridade = " + prioridade;
        texto = texto + ", Ciclos Necessarios = " + ciclosNecessarios;
        texto = texto + ", Recursos Necessarios = " + recursosNecessarios;
        texto = texto + "]";
        return texto;
    }
}
