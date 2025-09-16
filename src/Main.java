import java.io.BufferedReader; // Importa classe leitor
import java.io.FileReader; // Importa classe leitor de arquivo
import java.io.IOException; // Importa classe de exceção

public class Main {
    
    // Define a constante para a simulação
    private static final int CICLOS_SIMULACAO = 50000;
    // Define a flag para exibir detalhes
    private static final boolean EXIBIR_DETALHES = false;

    public static void main(String[] args) {
        // Captura o tempo inicial
        long tempoInicio = System.currentTimeMillis();

        // Imprime o cabeçalho do simulador
        System.out.println("=== SIMULADOR DE SCHEDULER ===");
        
        // Exibe a configuração atual
        System.out.println("Configuração:");
        System.out.println("   - Fonte de dados: Arquivo 'processos.csv'");
        System.out.println("   - Ciclos de simulação: " + CICLOS_SIMULACAO);
        System.out.println("   - Modo detalhado: " + (EXIBIR_DETALHES ? "SIM" : "NÃO"));
        System.out.println();

        // 1. Cria a instância do Scheduler
        Scheduler scheduler = new Scheduler();

        // 2. Lê os processos do arquivo
        int[] stats = lerProcessosDoArquivo(scheduler, "processos.csv");
        // Extrai as estatísticas do array
        int totalProcessos = stats[0];
        int totalAlta = stats[1];
        int totalMedia = stats[2];
        int totalBaixa = stats[3];
        int totalComDisco = stats[4];
        
        // 3. Executa a simulação
        executarSimulacao(scheduler);

        // 4. Gera o relatório final
        long tempoTotal = System.currentTimeMillis() - tempoInicio;
        // Exibe o relatório final completo
        exibirRelatorioFinal(scheduler, tempoTotal, totalProcessos, totalAlta, totalMedia, totalBaixa, totalComDisco);
    }
    
    /**
     * Método para ler arquivo e retornar estatísticas.
     * @return Um array de inteiros com estatísticas.
     */
    private static int[] lerProcessosDoArquivo(Scheduler scheduler, String nomeArquivo) {
        // Imprime mensagem de status
        System.out.println("Lendo processos do arquivo " + nomeArquivo + "...");

        // Inicializa os contadores
        int totalLidos = 0;
        int totalAlta = 0, totalMedia = 0, totalBaixa = 0, totalComDisco = 0;

        // Abre o arquivo e lida com exceções
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            // Pula a linha do cabeçalho
            br.readLine();

            // Loop para ler cada linha
            while ((linha = br.readLine()) != null) {
                // Divide a linha por vírgula
                String[] dados = linha.split(",");
                // Verifica se a linha tem dados válidos
                if (dados.length >= 3) {
                    // Limpa e atribui os dados
                    String id = dados[0].trim();
                    int prioridade = Integer.parseInt(dados[1].trim());
                    int ciclos = Integer.parseInt(dados[2].trim());
                    String recurso = dados.length > 3 ? dados[3].trim() : "";
                    
                    // Atualiza os contadores
                    totalLidos++;
                    if (prioridade == 1) totalAlta++;
                    else if (prioridade == 2) totalMedia++;
                    else totalBaixa++;
                    
                    // Verifica se o processo precisa de disco
                    if (recurso.equals("DISCO")) {
                        totalComDisco++;
                    }

                    // Cria um novo Processo
                    Processo novoProcesso = new Processo(id, prioridade, ciclos, recurso);
                    // Adiciona o Processo ao scheduler
                    scheduler.adicionarProcesso(novoProcesso);
                }
            }
            // Confirma a leitura
            System.out.println("✓ Processos carregados com sucesso!");

        } catch (IOException e) {
            // Lida com erros de leitura de arquivo
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            System.err.println("Certifique-se de que o arquivo " + nomeArquivo + " existe e está no diretório correto.");
        }
        
        // Retorna o array de estatísticas
        return new int[]{totalLidos, totalAlta, totalMedia, totalBaixa, totalComDisco};
    }
    
    /**
     * Método para executar a simulação.
     */
    private static void executarSimulacao(Scheduler scheduler) {
        // Imprime mensagem de início
        System.out.println("\nIniciando simulação...");
        
        if (!EXIBIR_DETALHES) {
            // Imprime aviso sobre o modo silencioso
            System.out.println("(Modo silencioso ativado para melhor performance)");
        }
        
        // Captura o tempo inicial da simulação
        long tempoInicio = System.currentTimeMillis();
        // Inicializa o contador de ciclos
        int ciclosExecutados = 0;
        
        // Loop principal da simulação
        while (ciclosExecutados < CICLOS_SIMULACAO && !scheduler.sistemaOcioso()) {
            // Incrementa o contador de ciclos
            ciclosExecutados++;
            // Executa um ciclo da CPU
            scheduler.executarCicloDeCPU();
        
            // Exibe o progresso a cada 5000 ciclos
            if (ciclosExecutados % 5000 == 0) {
                long tempoDecorrido = System.currentTimeMillis() - tempoInicio;
                int processosPendentes = scheduler.getTotalProcessosPendentes();
                double ciclosPorSegundo = ciclosExecutados / (tempoDecorrido / 1000.0);
                
                // Imprime a linha de progresso
                System.out.println("   Ciclo " + formatarNumero(ciclosExecutados) +
                                   " - Pendentes: " + formatarNumero(processosPendentes) +
                                   " - Velocidade: " + String.format("%.1f", ciclosPorSegundo) + " ciclos/s");
            }
        }
        
        // Calcula o tempo total da simulação
        long tempoSimulacao = System.currentTimeMillis() - tempoInicio;
        System.out.println();
        // Imprime a confirmação de finalização
        System.out.println("✓ Simulação finalizada!");
        System.out.println("   Ciclos executados: " + formatarNumero(ciclosExecutados));
        System.out.println("   Tempo de simulação: " + String.format("%.2f", tempoSimulacao / 1000.0) + "s");
    
        // Exibe o status final da simulação
        if (scheduler.sistemaOcioso()) {
            System.out.println("   Status: Todos os processos foram executados");
        } else {
            System.out.println("   Status: Simulação limitada por ciclos máximos");
        }
        System.out.println();
    }
    
    /**
     * Método para exibir o relatório completo.
     */
    private static void exibirRelatorioFinal(Scheduler scheduler, long tempoTotalMs, int totalProcessos, int totalAlta, int totalMedia, int totalBaixa, int totalComDisco) {
        // Imprime o cabeçalho do relatório
        System.out.println("=== RELATÓRIO FINAL ===");
        
        // Exibe estatísticas do scheduler
        scheduler.exibirEstatisticas();
        
        // Imprime as estatísticas de criação
        System.out.println("ESTATÍSTICAS DO ARQUIVO:");
        System.out.println("   Total de processos lidos: " + formatarNumero(totalProcessos));
        System.out.println("   Prioridade alta: " + formatarNumero(totalAlta));
        System.out.println("   Prioridade média: " + formatarNumero(totalMedia));
        System.out.println("   Prioridade baixa: " + formatarNumero(totalBaixa));
        System.out.println("   Com recurso DISCO: " + formatarNumero(totalComDisco));
        
        // Imprime a performance geral
        System.out.println("PERFORMANCE GERAL:");
        System.out.println("   Tempo total: " + String.format("%.2f", tempoTotalMs / 1000.0) + "s");
        
        // Obtém e calcula o uso de memória
        Runtime runtime = Runtime.getRuntime();
        long memoriaUsada = runtime.totalMemory() - runtime.freeMemory();
        long memoriaMB = memoriaUsada / (1024 * 1024);
        System.out.println("   Memória utilizada: " + memoriaMB + " MB");
        
        // Imprime a linha final do relatório
        System.out.println("========================");
        System.out.println("Simulação concluída com sucesso!");
    }
    
    /**
     * Método para formatar números grandes.
     */
    private static String formatarNumero(int numero) {
        // Converte o número para string
        String numeroStr = String.valueOf(numero);
        String resultado = "";
        int contador = 0;
        
        // Itera sobre a string de trás para frente
        for (int i = numeroStr.length() - 1; i >= 0; i--) {
            // Adiciona ponto a cada 3 dígitos
            if (contador > 0 && contador % 3 == 0) {
                resultado = "." + resultado;
            }
            // Concatena o dígito ao resultado
            resultado = numeroStr.charAt(i) + resultado;
            contador++;
        }
        // Retorna a string formatada
        return resultado;
    }
}