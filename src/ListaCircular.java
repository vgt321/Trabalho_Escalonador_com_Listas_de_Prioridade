public class ListaCircular {
    private class No {
        Object dado;
        No proximo;

        No(Object dado) {
            this.dado = dado;
            this.proximo = null;
        }
    }

    private No primeiro;
    private No ultimo;
    private int tamanho;

    public ListaCircular() {
        this.primeiro = null;
        this.ultimo = null;
        this.tamanho = 0;
    }

    // Verifica se a lista está vazia
    public boolean estaVazia() {
        return primeiro == null;
    }

    // Insere um novo elemento no fim da lista (fila)
    public void inserirNoFim(Object dado) {
        No novoNo = new No(dado);
        if (estaVazia()) {
            primeiro = novoNo;
            ultimo = novoNo;
            novoNo.proximo = primeiro; // Aponta para si mesmo
        } else {
            ultimo.proximo = novoNo;
            ultimo = novoNo;
            ultimo.proximo = primeiro; // Aponta de volta para o primeiro
        }
        tamanho++;
    }

    // Remove o elemento do início da lista (fila)
    public Object removerDoInicio() {
        if (estaVazia()) {
            return null;
        }
        Object dadoRemovido = primeiro.dado;
        if (primeiro == ultimo) { // Apenas um elemento na lista
            primeiro = null;
            ultimo = null;
        } else {
            primeiro = primeiro.proximo;
            ultimo.proximo = primeiro; // A última referência aponta para o novo primeiro
        }
        tamanho--;
        return dadoRemovido;
    }

    // Exibe o conteúdo da lista para depuração
    public void exibir() {
        if (estaVazia()) {
            System.out.println("Vazia");
            return;
        }
        No atual = primeiro;
        do {
            Processos p = (Processos) atual.dado;
            System.out.print("(" + p.getId() + ", " + p.getNome() + ", P" + p.getPrioridade() + ") -> ");
            atual = atual.proximo;
        } while (atual != primeiro);
        System.out.println("(início)");
    }
}