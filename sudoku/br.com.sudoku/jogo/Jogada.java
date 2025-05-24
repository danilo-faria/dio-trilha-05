package jogo;

import matriz.Posicao;

import java.time.LocalDateTime;

/**
 * Representa uma jogada em um jogo de Sudoku.
 * Esta classe armazena a posição da jogada, o valor anterior e o novo valor inserido,
 * além de um timestamp indicando quando a jogada foi realizada.
 */
public class Jogada {
    private Posicao posicao;
    private Integer valorAnterior;
    private Integer valorNovo;
    private LocalDateTime timestamp;

    /**
     * Construtor da classe Jogada.
     *
     * @param posicao       A posição da jogada no tabuleiro.
     * @param valorAnterior  O valor que estava na posição antes da jogada.
     * @param valorNovo     O novo valor inserido na posição.
     */
    public Jogada(Posicao posicao, Integer valorAnterior, Integer valorNovo) {
        // aqui estou registrando a posicao e histiroco do valor
        this.posicao = posicao;
        this.valorAnterior = valorAnterior;
        this.valorNovo = valorNovo;
        this.timestamp = LocalDateTime.now();
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public Integer getValorAnterior() {
        return valorAnterior;
    }

    public Integer getValorNovo() {
        return valorNovo;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("Jogada[posição=%s, anterior=%s, novo=%s, timestamp=%s]",
                posicao,
                valorAnterior != null ? valorAnterior : "vazio",
                valorNovo != null ? valorNovo : "vazio",
                timestamp);
    }
}