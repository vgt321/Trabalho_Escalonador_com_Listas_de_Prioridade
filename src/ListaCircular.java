public class ListaCircular {
    private class No {
        Object dado;
        No proximo;

        public No(Object dado) {
            this.dado = dado;
            this.proximo = null;
        }
    }

    private No tail;
    private int tamanho;

    // Construtor: lista começa vazia
    public ListaCircular() {
        this.tail = null;
        this.tamanho = 0;
    }

    public void inserirNoFim(Object elemento) {
        No novo = new No(elemento);

        if (tail == null) {
            tail = novo;
            tail.proximo = tail;
        } else {
            novo.proximo = tail.proximo;
            tail.proximo = novo;
            tail = novo;
        }
        tamanho++;
    }

    public Object removerDoInicio() {
        if (tail == null) {
            System.out.println("Lista vazia, nada para remover.");
            return null;
        }

        No head = tail.proximo; // cabeça é tail.proximo
        Object removido = head.dado;

        if (tail == head) { // só um elemento
            tail = null;
        } else {
            tail.proximo = head.proximo; // tail pula a antiga cabeça
        }
        tamanho--;
        return removido;
    }

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
            // percorre até o nó anterior ao tail
            while (atual.proximo != tail) {
                atual = atual.proximo;
            }
            atual.proximo = tail.proximo;
            tail = atual;
        }
        tamanho--;
        return removido;
    }

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

    public boolean estaVazia() {
        return tail == null;
    }

    public int tamanho() {
        return tamanho;
    }
}
}