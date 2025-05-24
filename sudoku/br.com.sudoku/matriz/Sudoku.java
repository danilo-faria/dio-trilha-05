package matriz;

import helpers.ResolvedorHelper;
import helpers.ValidadorHelper;

/*
 * Classe principal do jogo Sudoku.
 * Esta classe gerencia o tabuleiro, valida jogadas e resolve o Sudoku.
 */
public class Sudoku {
    private Tabuleiro tabuleiro;
    private ValidadorHelper validador;

    /**
     * Construtor padrão que inicializa um tabuleiro de Sudoku 9x9.
     */
    public Sudoku() {
        // criando um tabuleiro padrão 9x9
        this.tabuleiro = new Tabuleiro(9);
        this.validador = new ValidadorHelper(9);
    }

    public void inicializarJogo() {
        tabuleiro.limpar();
    }

    public void carregarJogo(int[][] matriz) {
        tabuleiro.limpar();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] > 0) {
                    Celula celula = new Celula(matriz[i][j], true);
                    tabuleiro.setCelula(i, j, celula);
                }
            }
        }
    }

    public boolean validarJogada(int linha, int coluna, int valor) {
        return validador.validarPosicao(tabuleiro, linha, coluna, valor);
    }

    /**
     * Faz uma jogada no tabuleiro.
     *
     * @param linha a linha onde a jogada será feita
     * @param coluna a coluna onde a jogada será feita
     * @param valor o valor a ser inserido na célula
     * @return true se a jogada for válida, false caso contrário
     */
    public boolean fazerJogada(int linha, int coluna, int valor) {
        if (!tabuleiro.isIndiceValido(linha, coluna)) {
            return false;
        }

        Celula celula = tabuleiro.getCelula(linha, coluna);
        if (celula.isFixo()) {
            return false;
        }

        if (!validarJogada(linha, coluna, valor)) {
            return false;
        }

        celula.setValor(valor);
        return true;
    }

    public boolean isCompleto() {
        return validador.validarTabuleiroCompleto(tabuleiro);
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public boolean resolver() {
        ResolvedorHelper resolvedor = new ResolvedorHelper();
        return resolvedor.resolver(tabuleiro);
    }

    @Override
    public String toString() {
        return tabuleiro.toString();
    }
}