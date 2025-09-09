    public class Processos {
        private int id;
        private String nome;
        private int prioridade;
        private int ciclosNecessarios;
        private String recursosNecessarios;

    public Processos (int id, String nome, int prioridade, int ciclosNecessarios, String recursosNecessarios) {
        this.id = id;
        this.nome = nome;
        setPrioridade(prioridade);
        this.ciclosNecessarios = ciclosNecessarios;
        this.recursosNecessarios = recursosNecessarios;
    }

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

    public void setid(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrioridade(int prioridade) {
        if (prioridade < 1 || prioridade > 3){
            System.out.println("Prioridade Invalida! Definindo como 1");
            this.prioridade = 1;
        }
        else{
            this.prioridade = prioridade;
        }

    }

    public void setCiclosNecessarios(int ciclosNecessarios) {
        this.ciclosNecessarios = ciclosNecessarios;
    }

    public void setRecursosNecessarios(String recursosNecessarios) {
        this.recursosNecessarios = recursosNecessarios;
    }

    @Override
    public String toString() {
        String texto ="Processos [";
        texto = texto + "ID = " + id;
        texto = texto + ", Nome = " + nome;
        texto = texto + ", Prioridade = " + prioridade;
        texto = texto + ", Ciclos Necessarios = " + ciclosNecessarios;
        texto = texto + ", Recursos Necessarios = " + recursosNecessarios;
        texto = texto + "]";
        return texto;
    }



