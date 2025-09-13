// Classe ListaCircular: representa uma lista circular simples


public class ListaCircular {
    // Classe interna No: representa um nó da lista
    private class No {
        Object dado;   // valor guardado
        No proximo;    // referência para o próximo nó

        public No(Object dado) {
            this.dado = dado;
            this.proximo = null;
        }
    }

    private No tail;     // ponteiro para o último nó (cauda)
    private int tamanho; // quantidade de elementos

    // Construtor: começa com a lista vazia
    public ListaCircular() {
        this.tail = null;
        this.tamanho = 0;
    }

    // Método para inserir um elemento no fim da lista
    public void inserirNoFim(Object elemento) {
        No novo = new No(elemento);

        if (tail == null) { // lista vazia
            tail = novo;
            tail.proximo = tail; // aponta para si mesmo
        } else {
            novo.proximo = tail.proximo; // novo aponta para a cabeça
            tail.proximo = novo;         // antigo último aponta para o novo
            tail = novo;                 // novo vira o último
        }
        tamanho++;
    }

    // Método para remover o primeiro elemento (cabeça)
    public Object removerDoInicio() {
        if (tail == null) {
            System.out.println("Lista vazia, nada para remover.");
            return null;
        }

        No head = tail.proximo; // primeiro nó
        Object removido = head.dado;

        if (tail == head) { // só um elemento
            tail = null;
        } else {
            tail.proximo = head.proximo; // pula o antigo primeiro
        }
        tamanho--;
        return removido;
    }

    // Método para remover o último elemento (cauda)
    public Object removerDoFim() {
        if (tail == null) {
            System.out.println("Lista vazia, nada para remover.");
            return null;
        }

        Object removido = tail.dado;

        if (tail.proximo == tail) { // só um elemento
            tail = null;
        } else {
            No atual = tail.proximo; // começa na cabeça

            // percorre até o nó anterior ao último
            while (atual.proximo != tail) {
                atual = atual.proximo;
            }
            atual.proximo = tail.proximo; // anterior aponta para a cabeça
            tail = atual;                 // atual vira o novo último
        }
        tamanho--;
        return removido;
    }

    // Método para exibir todos os elementos da lista
    public void exibir() {
        if (tail == null) {
            System.out.println("A lista está vazia.");
            return;
        }

        No atual = tail.proximo; // começa na cabeça
        do {
            System.out.println(atual.dado);
            atual = atual.proximo;
        } while (atual != tail.proximo); // para quando der a volta
    }

    // Método que verifica se a lista está vazia
    public boolean estaVazia() {
        return tail == null;
    }

    // Método que retorna a quantidade de elementos
    public int tamanho() {
        return tamanho;
    }
}