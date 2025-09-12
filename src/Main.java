import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();

        // 1. Carregar processos a partir de um arquivo (ajuste o caminho do arquivo)
        String nomeArquivo = "processos.txt"; 
        carregarProcessosDoArquivo(nomeArquivo, scheduler);

        // 2. Loop principal de execução do escalonador
        int ciclo = 0;
        while (scheduler.temProcessos()) {
            System.out.println("\n===== Ciclo " + (++ciclo) + " =====");
            scheduler.executarCicloDeCPU();
            
            // Pausa opcional para visualização, remova em execução final
            try {
                Thread.sleep(1000); // Pausa de 1 segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("\nTodos os processos foram concluídos.");
    }
    
    // Método para ler processos de um arquivo
    public static void carregarProcessosDoArquivo(String nomeArquivo, Scheduler scheduler) {
        try {
            File arquivo = new File(nomeArquivo);
            Scanner scanner = new Scanner(arquivo);
            
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] dados = linha.split(",");
                
                // Formato esperado no arquivo: id,nome,prioridade,ciclos,recurso
                int id = Integer.parseInt(dados[0].trim());
                String nome = dados[1].trim();
                int prioridade = Integer.parseInt(dados[2].trim());
                int ciclos = Integer.parseInt(dados[3].trim());
                String recurso = dados.length > 4 ? dados[4].trim() : null;

                Processos novoProcesso = new Processos(id, nome, prioridade, ciclos, recurso);
                scheduler.adicionarProcesso(novoProcesso);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo " + nomeArquivo + " não encontrado.");
            System.exit(1);
        }
    }
}