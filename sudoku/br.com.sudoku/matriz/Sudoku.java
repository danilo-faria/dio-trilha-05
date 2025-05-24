package matriz;

public class Sudoku {
    private Tabuleiro tabuleiro;

    public Sudoku() {
        // criando um tabuleiro padrão 9x9
        this.tabuleiro = new Tabuleiro(9);
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

    @Override
    public String toString() {
        return tabuleiro.toString();
    }
}
