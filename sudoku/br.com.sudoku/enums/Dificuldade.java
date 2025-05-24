package enums;

/**
 * Enumeração que representa os níveis de dificuldade do jogo Sudoku.
 * Cada nível de dificuldade tem um número associado que indica quantos números
 * devem ser removidos do tabuleiro para criar o quebra-cabeça.
 */
public enum Dificuldade {
    FACIL(35),
    MEDIO(45),
    DIFICIL(55),
    EXTREMO(65);

    private final int numerosParaRemover;

    /**
     * Construtor para a enumeração Dificuldade.
     *
     * @param numerosParaRemover O número de células a serem removidas do tabuleiro.
     */
    Dificuldade(int numerosParaRemover) {
        // quanto mais numeros para resolver, mais dificil né?
        this.numerosParaRemover = numerosParaRemover;
    }

    public int getNumerosParaRemover() {
        return numerosParaRemover;
    }
}

