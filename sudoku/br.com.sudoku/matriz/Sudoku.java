package matriz;

import helpers.ResolvedorHelper;
import helpers.ValidadorHelper;

public class Sudoku {
    private Tabuleiro tabuleiro;
    private ValidadorHelper validador;

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