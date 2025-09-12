// A sua classe: Scheduler.java
public class Scheduler {
    // Declaração das filas usando a classe ListaCircular
    private ListaCircular listaAlta;
    private ListaCircular listaMedia;
    private ListaCircular listaBaixa;
    private ListaCircular listaBloqueados;
    
    // Seu contador para a regra de anti-inanição
    private int contadorCiclosAlta;
    
    // Construtor do Scheduler
    public Scheduler() {
        this.listaAlta = new ListaCircular();
        this.listaMedia = new ListaCircular();
        this.listaBaixa = new ListaCircular();
        this.listaBloqueados = new ListaCircular();
        this.contadorCiclosAlta = 0;
    }

    // Método para adicionar um processo à fila correta
    public void adicionarProcesso(Processos processo) {
        if (processo.getPrioridade() == 1) { // Prioridade Alta
            listaAlta.inserirNoFim(processo);
        } else if (processo.getPrioridade() == 2) { // Prioridade Média
            listaMedia.inserirNoFim(processo);
        } else { // Prioridade Baixa
            listaBaixa.inserirNoFim(processo);
        }
    }
    
    // Retorna true se ainda houver processos em qualquer fila (incluindo bloqueados)
    public boolean temProcessos() {
        return !(listaAlta.estaVazia() &&
                 listaMedia.estaVazia() &&
                 listaBaixa.estaVazia() &&
                 listaBloqueados.estaVazia());
    }


    // Este método simula um único ciclo de execução da CPU.
    public void executarCicloDeCPU() {
        // Imprime um cabeçalho para marcar o início de um novo ciclo no log.
        System.out.println("--- NOVO CICLO ---");

        // 1. Lógica de Desbloqueio
        // Verifica se a fila de processos bloqueados não está vazia.
        if (!listaBloqueados.estaVazia()) {
            // Remove o primeiro processo da fila de bloqueados.
            Processos pDesbloqueado = (Processos) listaBloqueados.removerDoInicio();
            
            // Adiciona o processo de volta à sua fila de prioridade original.
            adicionarProcesso(pDesbloqueado);
            
            // Imprime uma mensagem de log para registrar o evento.
            System.out.println("-> DESBLOQUEIO: Processo " + pDesbloqueado.getId() + " foi desbloqueado e voltou para sua fila.");
        }

        // 2. Lógica de Escalonamento e Anti-Inanição
        // Declara uma variável para guardar o processo que será executado neste ciclo.
        Processos processoAtual = null;

        // Checa a regra de anti-inanição primeiro. A condição é:
        // O contador de ciclos de alta prioridade atingiu 5 ou mais?
        if (contadorCiclosAlta >= 5) {
            // Se sim, verifica se a fila de média prioridade não está vazia.
            if (!listaMedia.estaVazia()) {
                // Se houver processos, remove o primeiro da fila de média.
                processoAtual = (Processos) listaMedia.removerDoInicio();
                // Reseta o contador de anti-inanição para 0.
                contadorCiclosAlta = 0;
                // Registra o log do evento.
                System.out.println("-> ANTI-INANIÇÃO: Executando processo de média prioridade.");
            // Se a fila de média estiver vazia, verifica a de baixa prioridade.
            } else if (!listaBaixa.estaVazia()) {
                // Se houver processos, remove o primeiro da fila de baixa.
                processoAtual = (Processos) listaBaixa.removerDoInicio();
                // Reseta o contador de anti-inanição para 0.
                contadorCiclosAlta = 0;
                // Registra o log.
                System.out.println("-> ANTI-INANIÇÃO: Executando processo de baixa prioridade.");
            }
        }
        
        // Se a regra de anti-inanição não foi ativada ou não encontrou um processo para executar,
        // a busca segue a ordem de prioridade normal.
        if (processoAtual == null) {
            // Verifica se a fila de alta prioridade não está vazia.
            if (!listaAlta.estaVazia()) {
                // Se houver processos, remove o primeiro da fila de alta.
                processoAtual = (Processos) listaAlta.removerDoInicio();
                // Incrementa o contador de ciclos de alta prioridade.
                contadorCiclosAlta++;
            // Se a fila de alta estiver vazia, verifica a de média.
            } else if (!listaMedia.estaVazia()) {
                // Remove o processo da fila de média.
                processoAtual = (Processos) listaMedia.removerDoInicio();
            // Se as filas de alta e média estiverem vazias, verifica a de baixa.
            } else if (!listaBaixa.estaVazia()) {
                // Remove o processo da fila de baixa.
                processoAtual = (Processos) listaBaixa.removerDoInicio();
            }
        }

        // 3. Simulação da Execução
        // Verifica se um processo foi selecionado para ser executado.
        if (processoAtual != null) {
            // **Ponto de Correção:** Verifica o recurso ANTES de executar o processo.
            if (processoAtual.getRecursosNecessarios() != null &&
                processoAtual.getRecursosNecessarios().equalsIgnoreCase("DISCO")) {
                
                // Move o processo para a fila de bloqueados.
                listaBloqueados.inserirNoFim(processoAtual);
                
                // Registra o log do bloqueio.
                System.out.println("-> BLOQUEIO: Processo " + processoAtual.getId() + " precisa de DISCO e foi para a fila de bloqueados.");
            
            } else {
                // Se não precisa de recurso, simula a execução
                System.out.println("-> EXECUTANDO: Processo " + processoAtual.getId() + " (Prioridade " + processoAtual.getPrioridade() + ")");
                
                // Diminui o contador de ciclos necessários.
                processoAtual.setCiclosNecessarios(processoAtual.getCiclosNecessarios() - 1);
                
                // Verifica se o processo terminou.
                if (processoAtual.getCiclosNecessarios() <= 0) {
                    // Registra o log do término do processo.
                    System.out.println("-> TÉRMINO: Processo " + processoAtual.getId() + " concluiu sua execução.");
                } else {
                    // Se não terminou, o processo volta para sua fila original.
                    adicionarProcesso(processoAtual);
                    System.out.println("-> REINSERIDO: Processo " + processoAtual.getId() + " ainda precisa de " + processoAtual.getCiclosNecessarios() + " ciclos. Voltou para sua fila.");
                }
            }
        } else {
            // Se não havia nenhum processo em nenhuma fila para ser executado.
            System.out.println("-> OCIOSO: Nenhuma processo nas filas. CPU ociosa.");
        }

        // 4. Exibir o estado atual das filas
        System.out.println("\n--- ESTADO ATUAL DAS FILAS ---");
        System.out.print("Fila Alta: "); listaAlta.exibir();
        System.out.print("Fila Média: "); listaMedia.exibir();
        System.out.print("Fila Baixa: "); listaBaixa.exibir();
        System.out.print("Fila Bloqueados: "); listaBloqueados.exibir();
        System.out.println("------------------------------\n");
    }
}