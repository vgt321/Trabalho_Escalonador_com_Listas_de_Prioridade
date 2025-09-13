// Código-fonte descompilado de um arquivo .class usando FernFlower decompiler (do Intellij IDEA).
public class Processos { // Declara a classe pública chamada Processos.
    private int id; // Declara uma variável inteira privada para o ID do processo.
    private String nome; // Declara uma variável de texto privada para o nome do processo.
    private int prioridade; // Declara uma variável inteira privada para a prioridade do processo.
    private int ciclosNecessarios; // Declara uma variável inteira privada para os ciclos necessários.
    private String recursosNecessarios; // Declara uma variável de texto privada para os recursos necessários.

    public Processos(int var1, String var2, int var3, int var4, String var5) { // Construtor para criar um novo processo com parâmetros.
        this.id = var1; // Atribui o primeiro parâmetro ao ID.
        this.nome = var2; // Atribui o segundo parâmetro ao nome.
        this.setPrioridade(var3); // Chama o método setPrioridade para validar e atribuir a prioridade.
        this.ciclosNecessarios = var4; // Atribui o quarto parâmetro aos ciclos necessários.
        this.recursosNecessarios = var5; // Atribui o quinto parâmetro aos recursos necessários.
    }

    public int getid() { // Método público para obter o ID do processo.
        return this.id; // Retorna o valor do ID.
    }

    public String getNome() { // Método público para obter o nome do processo.
        return this.nome; // Retorna o valor do nome.
    }

    public int getPrioridade() { // Método público para obter a prioridade.
        return this.prioridade; // Retorna o valor da prioridade.
    }

    public int getCiclosNecessarios() { // Método público para obter os ciclos necessários.
        return this.ciclosNecessarios; // Retorna o valor dos ciclos.
    }

    public String getRecursosNecessarios() { // Método público para obter os recursos necessários.
        return this.recursosNecessarios; // Retorna o valor dos recursos.
    }

    public void setid(int var1) { // Método público para definir um novo ID.
        this.id = var1; // Atribui um novo valor ao ID.
    }

    public void setNome(String var1) { // Método público para definir um novo nome.
        this.nome = var1; // Atribui um novo valor ao nome.
    }

    public void setPrioridade(int var1) { // Método público para definir a prioridade.
        if (var1 >= 1 && var1 <= 3) { // Verifica se a prioridade está entre 1 e 3.
            this.prioridade = var1; // Se for válida, atribui o valor.
        } else { // Se a prioridade for inválida.
            System.out.println("Prioridade inválida! Definindo como 1"); // Imprime uma mensagem de aviso.
            this.prioridade = 1; // Define a prioridade para o valor padrão de 1.
        }
    }

    public void setCiclosNecessarios(int var1) { // Método público para definir os ciclos necessários.
        this.ciclosNecessarios = var1; // Atribui um novo valor aos ciclos.
    }

    public void setRecursosNecessarios(String var1) { // Método público para definir os recursos necessários.
        this.recursosNecessarios = var1; // Atribui um novo valor aos recursos.
    }

    public String toString() { // Método para representar o objeto como uma string.
        String var1 = "Processos ["; // Inicia a string de retorno.
        var1 = var1 + "ID = " + this.id; // Adiciona o ID à string.
        var1 = var1 + ", Nome = " + this.nome; // Adiciona o nome.
        var1 = var1 + ", Prioridade = " + this.prioridade; // Adiciona a prioridade.
        var1 = var1 + ", Ciclos Necessarios = " + this.ciclosNecessarios; // Adiciona os ciclos necessários.
        var1 = var1 + ", Recursos Necessarios = " + this.recursosNecessarios; // Adiciona os recursos necessários.
        var1 = var1 + "]"; // Finaliza a string com um colchete.
        return var1; // Retorna a string finalizada.
    }
}