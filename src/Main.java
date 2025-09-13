import java.util.Random; // Importa a classe Random para gerar números aleatórios.

public class Main { // Declara a classe principal do programa.
    // Configurações do sistema
    private static final int PROCESSOS_DINAMICOS = 5000000; // Define a constante para a quantidade de processos dinâmicos.
    private static final int CICLOS_SIMULACAO = 50000; // Define a constante para o número de ciclos da simulação.
    private static final boolean EXIBIR_DETALHES = false; // Define uma flag para ativar/desativar a exibição de logs detalhados.
    
    public static void main(String[] args) { // O ponto de entrada do programa.
        long tempoInicio = System.currentTimeMillis(); // Captura o tempo inicial para calcular o tempo total de execução.
        
        System.out.println("=== SIMULADOR DE SCHEDULER ==="); // Imprime o cabeçalho do simulador.
        System.out.println("Configuração:"); // Imprime o título da seção de configurações.
        System.out.println("   - Processos dinâmicos: " + formatarNumero(PROCESSOS_DINAMICOS)); // Exibe o número de processos dinâmicos formatado.
        System.out.println("   - Ciclos de simulação: " + formatarNumero(CICLOS_SIMULACAO)); // Exibe o número de ciclos da simulação formatado.
        System.out.println("   - Modo detalhado: " + (EXIBIR_DETALHES ? "SIM" : "NÃO")); // Exibe se o modo detalhado está ativado.
        System.out.println(); // Imprime uma linha em branco para espaçamento.
        
        // 1. Criar scheduler
        Scheduler scheduler = new Scheduler(); // Cria uma nova instância da classe Scheduler.
        
        // 2. Adicionar processos de exemplo
        adicionarProcessosExemplo(scheduler); // Chama o método para adicionar os processos de exemplo.
        
        // 3. Adicionar processos dinâmicos
        adicionarProcessosDinamicos(scheduler); // Chama o método para adicionar os processos gerados dinamicamente.
        
        // 4. Executar simulação
        executarSimulacao(scheduler); // Chama o método para iniciar a simulação principal.
        
        // 5. Relatório final
        long tempoTotal = System.currentTimeMillis() - tempoInicio; // Calcula o tempo total de execução do programa.
        exibirRelatorioFinal(scheduler, tempoTotal); // Chama o método para exibir o relatório final.
    }
    
    /**
     * Adiciona os 5 processos de exemplo iniciais
     */
    private static void adicionarProcessosExemplo(Scheduler scheduler) { // Declara um método para adicionar processos de exemplo.
        System.out.println("Adicionando processos de exemplo..."); // Imprime uma mensagem de status.
        
        // Criação manual dos 5 processos exemplo
        Processo p1 = new Processo("P1", 1, 6, ""); // Cria uma nova instância de Processo.
        Processo p2 = new Processo("P2", 2, 8, ""); // Cria um segundo processo de exemplo.
        Processo p3 = new Processo("P3", 3, 3, ""); // Cria um terceiro processo de exemplo.
        Processo p4 = new Processo("P4", 1, 5, "DISCO"); // Cria um quarto processo de exemplo com recurso.
        Processo p5 = new Processo("P5", 2, 7, ""); // Cria um quinto processo de exemplo.
        
        // Adiciona ao scheduler
        scheduler.adicionarProcesso(p1); // Adiciona o processo p1 ao scheduler.
        scheduler.adicionarProcesso(p2); // Adiciona o processo p2.
        scheduler.adicionarProcesso(p3); // Adiciona o processo p3.
        scheduler.adicionarProcesso(p4); // Adiciona o processo p4.
        scheduler.adicionarProcesso(p5); // Adiciona o processo p5.
        
        System.out.println("✓ 5 processos de exemplo adicionados"); // Imprime uma mensagem de confirmação.
        System.out.println(); // Imprime uma linha em branco.
    }
    
    /**
     * Adiciona os processos dinâmicos gerados aleatoriamente
     */
    private static void adicionarProcessosDinamicos(Scheduler scheduler) { // Declara o método para adicionar processos dinâmicos.
        System.out.println("Adicionando " + formatarNumero(PROCESSOS_DINAMICOS) + " processos dinamicamente..."); // Exibe uma mensagem de status.
        
        Random random = new Random(); // Cria uma instância do gerador de números aleatórios.
        long tempoInicio = System.currentTimeMillis(); // Captura o tempo de início da geração.
        
        // Contadores para estatísticas
        int totalAlta = 0, totalMedia = 0, totalBaixa = 0, totalComDisco = 0; // Inicializa contadores para as estatísticas.
        
        // Loop principal de geração
        for (int i = 6; i <= PROCESSOS_DINAMICOS + 5; i++) { // Inicia um loop para criar o número definido de processos.
            // Gera características aleatórias
            int prioridade = random.nextInt(3) + 1; // Gera uma prioridade aleatória entre 1 e 3.
            int ciclos = random.nextInt(50) + 1; // Gera um número de ciclos aleatório entre 1 e 50.
            String recurso = ""; // Inicializa a variável de recurso.
            
            // A cada 50000 processos, um precisa de DISCO
            if (i % 50000 == 0) { // Verifica se é a vez de adicionar um recurso.
                recurso = "DISCO"; // Atribui o recurso DISCO.
                totalComDisco++; // Incrementa o contador de processos com DISCO.
            }
            
            // Conta por prioridade
            if (prioridade == 1) totalAlta++; // Incrementa o contador de prioridade alta.
            else if (prioridade == 2) totalMedia++; // Incrementa o contador de prioridade média.
            else totalBaixa++; // Incrementa o contador de prioridade baixa.
            
            // Cria e adiciona processo
            String id = "P" + i; // Cria um ID de string para o processo.
            Processo novoProcesso = new Processo(id, prioridade, ciclos, recurso); // Cria uma nova instância de Processo.
            scheduler.adicionarProcesso(novoProcesso); // Adiciona o novo processo ao scheduler.
            
            // Mostra progresso a cada 1 milhão
            if (i % 1000000 == 0) { // Verifica se é hora de exibir o progresso.
                long tempoDecorrido = System.currentTimeMillis() - tempoInicio; // Calcula o tempo decorrido.
                double porcentagem = ((i - 5) * 100.0) / PROCESSOS_DINAMICOS; // Calcula a porcentagem concluída.
                System.out.println("   Progresso: " + formatarNumero(i - 5) + "/" + // Exibe o número de processos criados formatado.
                                   formatarNumero(PROCESSOS_DINAMICOS) + " (" + // Exibe o total de processos formatado.
                                   String.format("%.1f", porcentagem) + "%) - " + // Exibe a porcentagem com uma casa decimal.
                                   "Tempo: " + (tempoDecorrido / 1000) + "s"); // Exibe o tempo decorrido em segundos.
            }
        }
        
        long tempoGeracao = System.currentTimeMillis() - tempoInicio; // Calcula o tempo total de geração.
        
        System.out.println("✓ Geração concluída em " + (tempoGeracao / 1000) + " segundos"); // Imprime o tempo total de geração.
        System.out.println("   Distribuição:"); // Imprime o título da distribuição.
        System.out.println("     Alta (1): " + formatarNumero(totalAlta) + " processos"); // Exibe o total de processos de alta prioridade.
        System.out.println("     Média (2): " + formatarNumero(totalMedia) + " processos"); // Exibe o total de processos de média prioridade.
        System.out.println("     Baixa (3): " + formatarNumero(totalBaixa) + " processos"); // Exibe o total de processos de baixa prioridade.
        System.out.println("     Com DISCO: " + formatarNumero(totalComDisco) + " processos"); // Exibe o total de processos que pedem DISCO.
        System.out.println(); // Imprime uma linha em branco.
    }
    
    /**
     * Executa a simulação principal
     */
    private static void executarSimulacao(Scheduler scheduler) { // Declara o método para executar a simulação.
        System.out.println("Iniciando simulação..."); // Imprime uma mensagem de início.
        
        if (!EXIBIR_DETALHES) { // Verifica se o modo detalhado está desativado.
            System.out.println("(Modo silencioso ativado para melhor performance)"); // Imprime um aviso sobre o modo silencioso.
        }
        
        long tempoInicio = System.currentTimeMillis(); // Captura o tempo de início da simulação.
        int ciclosExecutados = 0; // Inicializa o contador de ciclos executados.
        
        // Loop principal da simulação
        while (ciclosExecutados < CICLOS_SIMULACAO && !scheduler.sistemaOcioso()) { // Continua enquanto não atingir o limite de ciclos ou o sistema não estiver ocioso.
            ciclosExecutados++; // Incrementa o contador de ciclos.
            
            if (EXIBIR_DETALHES) { // Verifica se o modo detalhado está ativado.
                scheduler.executarCicloDeCPU(); // Executa um ciclo da CPU com todos os logs.
            } else {
                scheduler.executarCicloDeCPU(); // Executa um ciclo da CPU.
            }
            
            // Mostra progresso a cada 5000 ciclos
            if (ciclosExecutados % 5000 == 0) { // Verifica se é hora de exibir o progresso.
                long tempoDecorrido = System.currentTimeMillis() - tempoInicio; // Calcula o tempo decorrido.
                int processosPendentes = scheduler.getTotalProcessosPendentes(); // Obtém o número de processos pendentes.
                double ciclosPorSegundo = ciclosExecutados / (tempoDecorrido / 1000.0); // Calcula a velocidade de execução em ciclos por segundo.
                
                System.out.println("   Ciclo " + formatarNumero(ciclosExecutados) + // Exibe o número do ciclo formatado.
                                   " - Pendentes: " + formatarNumero(processosPendentes) + // Exibe o número de processos pendentes formatado.
                                   " - Velocidade: " + String.format("%.1f", ciclosPorSegundo) + " ciclos/s"); // Exibe a velocidade formatada.
            }
        }
        
        long tempoSimulacao = System.currentTimeMillis() - tempoInicio; // Calcula o tempo total da simulação.
        
        System.out.println(); // Imprime uma linha em branco.
        System.out.println("✓ Simulação finalizada!"); // Imprime uma mensagem de finalização.
        System.out.println("   Ciclos executados: " + formatarNumero(ciclosExecutados)); // Exibe o total de ciclos executados.
        System.out.println("   Tempo de simulação: " + String.format("%.2f", tempoSimulacao / 1000.0) + "s"); // Exibe o tempo total da simulação.
        
        if (scheduler.sistemaOcioso()) { // Verifica se o sistema ficou ocioso.
            System.out.println("   Status: Todos os processos foram executados"); // Mensagem de status se todos os processos terminaram.
        } else {
            System.out.println("   Status: Simulação limitada por ciclos máximos"); // Mensagem de status se o limite de ciclos foi atingido.
        }
        System.out.println(); // Imprime uma linha em branco.
    }
    
    /**
     * Exibe relatório final completo
     */
    private static void exibirRelatorioFinal(Scheduler scheduler, long tempoTotalMs) { // Declara o método para exibir o relatório final.
        System.out.println("=== RELATÓRIO FINAL ==="); // Imprime o cabeçalho do relatório.
        
        // Estatísticas do scheduler
        scheduler.exibirEstatisticas(); // Chama o método do scheduler para exibir suas estatísticas internas.
        
        // Performance geral
        System.out.println("PERFORMANCE GERAL:"); // Imprime o título da seção de performance.
        System.out.println("   Tempo total: " + String.format("%.2f", tempoTotalMs / 1000.0) + "s"); // Exibe o tempo total do programa.
        System.out.println("   Processos criados: " + formatarNumero(PROCESSOS_DINAMICOS + 5)); // Exibe o total de processos criados.
        System.out.println("   Taxa de criação: " + // Exibe a taxa de criação de processos.
                           formatarNumero((int)((PROCESSOS_DINAMICOS + 5) / (tempoTotalMs / 1000.0))) + // Calcula e formata a taxa.
                           " processos/s"); // Adiciona a unidade de medida.
        
        // Uso de memória
        Runtime runtime = Runtime.getRuntime(); // Obtém a instância do ambiente de execução do Java.
        long memoriaUsada = runtime.totalMemory() - runtime.freeMemory(); // Calcula a memória usada.
        long memoriaMB = memoriaUsada / (1024 * 1024); // Converte a memória usada para megabytes.
        System.out.println("   Memória utilizada: " + memoriaMB + " MB"); // Exibe a memória utilizada.
        
        System.out.println("========================"); // Imprime a linha de finalização do relatório.
        System.out.println("Simulação concluída com sucesso!"); // Imprime a mensagem final.
    }
    
    /**
     * Formata números grandes com separador de milhares
     */
    private static String formatarNumero(int numero) { // Declara o método para formatar números.
        String numeroStr = String.valueOf(numero); // Converte o número para string.
        String resultado = ""; // Inicializa a string de resultado.
        int contador = 0; // Inicializa o contador para os dígitos.
        
        // Adiciona pontos a cada 3 dígitos (da direita para esquerda)
        for (int i = numeroStr.length() - 1; i >= 0; i--) { // Inicia um loop reverso pela string.
            if (contador > 0 && contador % 3 == 0) { // Verifica se é a vez de adicionar um ponto.
                resultado = "." + resultado; // Adiciona um ponto ao início da string resultado.
            }
            resultado = numeroStr.charAt(i) + resultado; // Adiciona o dígito atual ao início da string resultado.
            contador++; // Incrementa o contador de dígitos.
        }
        
        return resultado; // Retorna a string formatada.
    }

}

