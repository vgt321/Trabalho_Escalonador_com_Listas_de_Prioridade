/**
 * Classe que representa um processo no sistema
 */
class Processo {
    private String id;
    private int prioridade;
    private int ciclosNecessarios;
    private String recursosNecessarios;
    private int ciclosOriginais;
    
    public Processo(String id, int prioridade, int ciclosNecessarios, String recursosNecessarios) {
        this.id = id;
        this.prioridade = prioridade;
        this.ciclosNecessarios = ciclosNecessarios;
        this.ciclosOriginais = ciclosNecessarios;
        this.recursosNecessarios = recursosNecessarios;
    }
    
    public String getId() { return id; }
    public int getPrioridade() { return prioridade; }
    public int getCiclosNecessarios() { return ciclosNecessarios; }
    public void setCiclosNecessarios(int ciclos) { this.ciclosNecessarios = ciclos; }
    public String getRecursosNecessarios() { return recursosNecessarios; }
    public int getCiclosOriginais() { return ciclosOriginais; }
    
    public String toString() {
        return "Processo[" + id + ", P" + prioridade + ", " + ciclosNecessarios + "/" + ciclosOriginais + " ciclos]";
    }
}

/**
 * Nó para implementação de fila com lista ligada
 */
class No {
    Processo processo;
    No proximo;
    
    public No(Processo processo) {
        this.processo = processo;
        this.proximo = null;
    }
}

/**
 * Implementação própria de fila usando lista ligada
 */
class FilaCustomizada {
    private No inicio;
    private No fim;
    private int tamanho;
    
    public FilaCustomizada() {
        this.inicio = null;
        this.fim = null;
        this.tamanho = 0;
    }
    
    // Adiciona elemento no final da fila
    public void adicionar(Processo processo) {
        No novoNo = new No(processo);
        
        if (estaVazia()) {
            inicio = novoNo;
            fim = novoNo;
        } else {
            fim.proximo = novoNo;
            fim = novoNo;
        }
        tamanho++;
    }
    
    // Remove e retorna elemento do início da fila
    public Processo remover() {
        if (estaVazia()) {
            return null;
        }
        
        Processo processo = inicio.processo;
        inicio = inicio.proximo;
        
        if (inicio == null) {
            fim = null;
        }
        
        tamanho--;
        return processo;
    }
    
    // Verifica se a fila está vazia
    public boolean estaVazia() {
        return inicio == null;
    }
    
    // Retorna o tamanho da fila
    public int getTamanho() {
        return tamanho;
    }
    
    // Retorna representação em string da fila
    public String toString() {
        if (estaVazia()) {
            return "[ vazia ]";
        }
        
        String resultado = "[ ";
        No atual = inicio;
        while (atual != null) {
            resultado += atual.processo.getId() + " ";
            atual = atual.proximo;
        }
        resultado += "]";
        return resultado;
    }
}

/**
 * Scheduler implementado sem usar estruturas prontas
 */
public class Scheduler {
    // Constantes
    private static final int PRIORIDADE_ALTA = 1;
    private static final int PRIORIDADE_MEDIA = 2;
    private static final int PRIORIDADE_BAIXA = 3;
    private static final int LIMITE_ANTI_INANICAO = 5;
    
    // Filas implementadas do zero
    private FilaCustomizada filaAlta = new FilaCustomizada();
    private FilaCustomizada filaMedia   = new FilaCustomizada();
    private FilaCustomizada filaBaixa   = new FilaCustomizada();
    private FilaCustomizada filaBloqueados = new FilaCustomizada();
    
    // Controles do sistema
    private int contadorCiclosAlta;
    private int cicloAtual;
    private int processosExecutados;
    private int processosFinalizados;
    private int ciclosOciosos;
    
    public Scheduler() {
        this.filaAlta = new FilaCustomizada();
        this.filaMedia = new FilaCustomizada();
        this.filaBaixa = new FilaCustomizada();
        this.filaBloqueados = new FilaCustomizada();
        this.contadorCiclosAlta = 0;
        this.cicloAtual = 0;
        this.processosExecutados = 0;
        this.processosFinalizados = 0;
        this.ciclosOciosos = 0;
    }
    
    /**
     * Adiciona processo à fila correta baseado na prioridade
     */
    public void adicionarProcesso(Processo processo) {
        if (processo == null) {
            System.err.println("ERRO: Processo nulo não pode ser adicionado");
            return;
        }
        
        int prioridade = processo.getPrioridade();
        
        if (prioridade == PRIORIDADE_ALTA) {
            filaAlta.adicionar(processo);
        } else if (prioridade == PRIORIDADE_MEDIA) {
            filaMedia.adicionar(processo);
        } else if (prioridade == PRIORIDADE_BAIXA) {
            filaBaixa.adicionar(processo);
        } else {
            System.err.println("AVISO: Prioridade inválida para processo " + processo.getId() + ". Colocando em baixa prioridade.");
            filaBaixa.adicionar(processo);
        }
    }
    
    /**
     * Executa um ciclo completo da CPU
     */
    public void executarCicloDeCPU() {
        cicloAtual++;
        System.out.println("\n=== CICLO " + cicloAtual + " ===");
        
        // 1. Processar desbloqueios
        processarDesbloqueios();
        
        // 2. Selecionar próximo processo
        Processo processoSelecionado = selecionarProximoProcesso();
        
        // 3. Executar processo
        executarProcesso(processoSelecionado);
        
        // 4. Mostrar estado atual
        exibirEstadoSistema();
        
        System.out.println("================");
    }
    
    /**
     * Processa desbloqueios de processos
     */
    private void processarDesbloqueios() {
        if (!filaBloqueados.estaVazia()) {
            Processo processoDesbloqueado = filaBloqueados.remover();
            adicionarProcesso(processoDesbloqueado);
            System.out.println("-> DESBLOQUEIO: Processo " + processoDesbloqueado.getId() + 
                             " voltou para fila de prioridade " + processoDesbloqueado.getPrioridade());
        }
    }
    
    /**
     * Seleciona próximo processo considerando anti-inanição
     */
    private Processo selecionarProximoProcesso() {
        Processo processo = null;
        
        // Verifica anti-inanição
        if (contadorCiclosAlta >= LIMITE_ANTI_INANICAO) {
            processo = aplicarAntiInanicao();
            if (processo != null) {
                contadorCiclosAlta = 0;
                return processo;
            }
        }
        
        // Escalonamento normal por prioridade
        if (!filaAlta.estaVazia()) {
            processo = filaAlta.remover();
            contadorCiclosAlta++;
        } else if (!filaMedia.estaVazia()) {
            processo = filaMedia.remover();
        } else if (!filaBaixa.estaVazia()) {
            processo = filaBaixa.remover();
        }
        
        return processo;
    }
    
    /**
     * Aplica lógica de anti-inanição
     */
    private Processo aplicarAntiInanicao() {
        if (!filaMedia.estaVazia()) {
            System.out.println("-> ANTI-INANICAO: Executando processo de média prioridade");
            return filaMedia.remover();
        } else if (!filaBaixa.estaVazia()) {
            System.out.println("-> ANTI-INANICAO: Executando processo de baixa prioridade");
            return filaBaixa.remover();
        }
        return null;
    }
    
    /**
     * Executa o processo selecionado
     */
    private void executarProcesso(Processo processo) {
        if (processo == null) {
            System.out.println("-> CPU OCIOSA: Nenhum processo disponível");
            ciclosOciosos++;
            return;
        }
        
        processosExecutados++;
        System.out.println("-> EXECUTANDO: Processo " + processo.getId() + 
                         " (Prioridade " + processo.getPrioridade() + 
                         ", Ciclos restantes: " + processo.getCiclosNecessarios() + ")");
        
        // Simula execução
        processo.setCiclosNecessarios(processo.getCiclosNecessarios() - 1);
        
        // Determina o que fazer com o processo
        tratarProcessoAposExecucao(processo);
    }
    
    /**
     * Trata processo após execução
     */
    private void tratarProcessoAposExecucao(Processo processo) {
        if (processo.getCiclosNecessarios() <= 0) {
            // Processo finalizado
            processosFinalizados++;
            System.out.println("-> FINALIZADO: Processo " + processo.getId() + " concluído");
            
        } else if (precisaRecursoDisco(processo)) {
            // Processo bloqueado por recurso
            filaBloqueados.adicionar(processo);
            System.out.println("-> BLOQUEADO: Processo " + processo.getId() + 
                             " aguardando recurso " + processo.getRecursosNecessarios());
            
        } else {
            // Processo volta para fila
            adicionarProcesso(processo);
            System.out.println("-> REQUEUE: Processo " + processo.getId() + 
                             " voltou para fila (restam " + processo.getCiclosNecessarios() + " ciclos)");
        }
    }
    
    /**
     * Verifica se processo precisa do recurso disco
     */
    private boolean precisaRecursoDisco(Processo processo) {
        String recurso = processo.getRecursosNecessarios();
        return recurso != null && recurso.equalsIgnoreCase("DISCO");
    }
    
    /**
     * Exibe estado atual do sistema
     */
    private void exibirEstadoSistema() {
        System.out.println("\n--- ESTADO DAS FILAS ---");
        System.out.println("Fila Alta (" + filaAlta.getTamanho() + "): " + filaAlta.toString());
        System.out.println("Fila Media (" + filaMedia.getTamanho() + "): " + filaMedia.toString());
        System.out.println("Fila Baixa (" + filaBaixa.getTamanho() + "): " + filaBaixa.toString());
        System.out.println("Fila Bloqueados (" + filaBloqueados.getTamanho() + "): " + filaBloqueados.toString());
        System.out.println("Anti-inanição: " + contadorCiclosAlta + "/" + LIMITE_ANTI_INANICAO + " ciclos");
        System.out.println("------------------------");
    }
    
    /**
     * Retorna total de processos pendentes
     */
    public int getTotalProcessosPendentes() {
        return filaAlta.getTamanho() + filaMedia.getTamanho() + 
               filaBaixa.getTamanho() + filaBloqueados.getTamanho();
    }
    
    /**
     * Verifica se sistema está ocioso
     */
    public boolean sistemaOcioso() {
        return getTotalProcessosPendentes() == 0;
    }
    
    /**
     * Exibe estatísticas finais
     */
    public void exibirEstatisticas() {
        System.out.println("\n=== ESTATISTICAS DO SISTEMA ===");
        System.out.println("Ciclos executados: " + cicloAtual);
        System.out.println("Processos executados: " + processosExecutados);
        System.out.println("Processos finalizados: " + processosFinalizados);
        System.out.println("Ciclos ociosos: " + ciclosOciosos);
        
        if (cicloAtual > 0) {
            double percentualOcioso = (ciclosOciosos * 100.0) / cicloAtual;
            System.out.println("Taxa de ociosidade: " + String.format("%.1f", percentualOcioso) + "%");
        }
        
        System.out.println("Processos pendentes: " + getTotalProcessosPendentes());
        System.out.println("===============================");
    }
    
    /**
     * Executa ciclos até não haver mais processos
     */
    public void executarAteCompletar() {
        System.out.println("Iniciando execução completa do scheduler...");
        
        while (!sistemaOcioso()) {
            executarCicloDeCPU();
            
            // Pequena pausa para melhor visualização
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        System.out.println("\nTodos os processos foram executados!");
        exibirEstatisticas();
    }
}