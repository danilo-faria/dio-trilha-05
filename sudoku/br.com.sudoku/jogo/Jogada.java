package jogo;

import matriz.Posicao;

import java.time.LocalDateTime;

public class Jogada {
    private Posicao posicao;
    private Integer valorAnterior;
    private Integer valorNovo;
    private LocalDateTime timestamp;

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