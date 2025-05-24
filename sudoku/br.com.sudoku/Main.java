import enums.Dificuldade;
import jogo.Jogo;

/**
 * Classe principal para execução do jogo Sudoku.
 *
 * Esta classe demonstra como criar, iniciar, interagir, salvar e carregar um jogo de Sudoku
 * utilizando a classe {@link jogo.Jogo}.
 *
 * Exemplo de uso:
 * <pre>
 *     Jogo jogo = new Jogo(Dificuldade.MEDIO);
 *     jogo.novoJogo();
 *     System.out.println(jogo.getTabuleiro());
 *     jogo.fazerJogada(0, 1, 5);
 *     if (jogo.isJogoCompleto()) {
 *         System.out.println("Parabéns! Você completou o Sudoku!");
 *         System.out.println("Sua pontuação: " + jogo.calcularPontuacao());
 *     }
 *     String dadosSalvos = jogo.salvarJogo();
 *     // jogo.carregarJogo(dadosSalvos);
 * </pre>
 *
 * @author danilo-faria
 */
public class Main {
    public static void main(String[] args) {
        // Criar uma nova instância de Jogo com dificuldade MEDIO
        Jogo jogo = new Jogo(Dificuldade.MEDIO);

        // Iniciar um novo jogo
        jogo.novoJogo();

        // A partir daqui, você pode interagir com o jogo
        // Por exemplo, imprimir o tabuleiro atual
        System.out.println(jogo.getTabuleiro());

        // Fazer uma jogada (linha 0, coluna 1, valor 5)
        jogo.fazerJogada(0, 1, 5);

        // Verificar se o jogo está completo
        if (jogo.isJogoCompleto()) {
            System.out.println("Parabéns! Você completou o Sudoku!");
            System.out.println("Sua pontuação: " + jogo.calcularPontuacao());
        }

        // Você também pode salvar o jogo para continuar depois
        String dadosSalvos = jogo.salvarJogo();

        // E mais tarde, carregar o jogo salvo
        // jogo.carregarJogo(dadosSalvos);
    }
}