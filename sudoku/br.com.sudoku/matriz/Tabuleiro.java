package matriz;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe que representa o tabuleiro do Sudoku.
 * O tabuleiro é representado como uma matriz de células.
 */
public class Tabuleiro {
    private List<List<Celula>> grid;
    private int tamanho;

    /**
     * Construtor que inicializa o tabuleiro com o tamanho especificado.
     *
     * @param tamanho O tamanho do tabuleiro (deve ser um quadrado perfeito).
     */
    public Tabuleiro(int tamanho) {
        this.tamanho = tamanho;
        this.grid = new ArrayList<>(tamanho);

        // já cria a matriz no tamanho predefinido
        for (int i = 0; i < tamanho; i++) {
            List<Celula> linha = new ArrayList<>(tamanho);
            for (int j = 0; j < tamanho; j++) {
                linha.add(new Celula());
            }
            grid.add(linha);
        }
    }

    /**
     * Construtor que inicializa o tabuleiro com uma matriz de células existente.
     *
     * @param linha A linha da matriz
     * @param coluna O número da coluna na matriz
     */
    public Celula getCelula(int linha, int coluna) {
        if (isIndiceValido(linha, coluna)) {
            return grid.get(linha).get(coluna);
        }

        // garantindo que pediu a celula dentro do permitido
        throw new IllegalArgumentException("Índices inválidos: " + linha + ", " + coluna);
    }

    public void setCelula(int linha, int coluna, Celula celula) {
        if (isIndiceValido(linha, coluna)) {
            grid.get(linha).set(coluna, celula);
        } else {
            throw new IllegalArgumentException("Índices inválidos: " + linha + ", " + coluna);
        }
    }

    public int getTamanho() {
        return tamanho;
    }

    public List<List<Celula>> getGrid() {
        return grid;
    }

    /**
     * Limpa o tabuleiro, redefinindo todas as células para o estado inicial.
     */
    public void limpar() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                grid.get(i).set(j, new Celula());
            }
        }
    }

    /**
     * Cria uma cópia do tabuleiro atual.
     *
     * @return Uma nova instância de Tabuleiro com os mesmos valores.
     */
    public Tabuleiro copiar() {
        Tabuleiro copia = new Tabuleiro(tamanho);
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                copia.setCelula(i, j, this.getCelula(i, j).clone());
            }
        }
        return copia;
    }

    public boolean isIndiceValido(int linha, int coluna) {
        // dentro do tamanho predefinido do tabuleiro
        return linha >= 0 && linha < tamanho && coluna >= 0 && coluna < tamanho;
    }

    public List<Celula> getLinha(int linha) {
        if (linha >= 0 && linha < tamanho) {
            return new ArrayList<>(grid.get(linha));
        }
        throw new IllegalArgumentException("Índice de linha inválido: " + linha);
    }

    public List<Celula> getColuna(int coluna) {
        if (coluna >= 0 && coluna < tamanho) {
            return grid.stream()
                    .map(linha -> linha.get(coluna))
                    .collect(Collectors.toList());
        }
        throw new IllegalArgumentException("Índice de coluna inválido: " + coluna);
    }

    /**
     * Retorna o quadrante correspondente a uma célula específica.
     *
     * @param linha A linha da célula
     * @param coluna A coluna da célula
     * @return Uma lista de células que pertencem ao quadrante
     */
    public List<Celula> getQuadrante(int linha, int coluna) {
        if (!isIndiceValido(linha, coluna)) {
            throw new IllegalArgumentException("Índices inválidos: " + linha + ", " + coluna);
        }

        // feito no chat gpt
        int tamanhoQuadrante = (int) Math.sqrt(tamanho);
        int inicioLinha = (linha / tamanhoQuadrante) * tamanhoQuadrante;
        int inicioColuna = (coluna / tamanhoQuadrante) * tamanhoQuadrante;

        List<Celula> quadrante = new ArrayList<>(tamanho);
        for (int i = inicioLinha; i < inicioLinha + tamanhoQuadrante; i++) {
            for (int j = inicioColuna; j < inicioColuna + tamanhoQuadrante; j++) {
                quadrante.add(grid.get(i).get(j));
            }
        }

        return quadrante;
    }

    @Override
    public String toString() {
        // feito no chat gpt
        StringBuilder sb = new StringBuilder();
        int tamanhoQuadrante = (int) Math.sqrt(tamanho);

        for (int i = 0; i < tamanho; i++) {
            if (i > 0 && i % tamanhoQuadrante == 0) {
                for (int k = 0; k < tamanho + tamanhoQuadrante - 1; k++) {
                    sb.append("-");
                }
                sb.append("\n");
            }

            for (int j = 0; j < tamanho; j++) {
                if (j > 0 && j % tamanhoQuadrante == 0) {
                    sb.append("|");
                }

                Celula celula = grid.get(i).get(j);
                sb.append(celula.getValor().map(String::valueOf).orElse(" "));

                if (j < tamanho - 1) {
                    sb.append(" ");
                }
            }

            sb.append("\n");
        }

        return sb.toString();
    }
}