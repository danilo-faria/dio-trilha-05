package jogo;

import enums.Dificuldade;
import helpers.ResolvedorHelper;
import helpers.ValidadorHelper;
import matriz.Celula;
import matriz.Posicao;
import matriz.Tabuleiro;

import java.util.*;

public class Gerador {
    private Random random;
    private ValidadorHelper validador;

    public Gerador() {
        this.random = new Random();
        // tamanho aqu é 9x9
        this.validador = new ValidadorHelper(9);
    }

    public Tabuleiro gerarTabuleiro(int tamanho, Dificuldade dificuldade) {
        Tabuleiro tabuleiro = gerarTabuleiroCompleto(tamanho);
        int quantidadeRemover = calcularQuantidadeRemover(dificuldade);
        removerNumeros(tabuleiro, quantidadeRemover);
        return tabuleiro;
    }

    public Tabuleiro gerarTabuleiroCompleto(int tamanho) {
        Tabuleiro tabuleiro = new Tabuleiro(tamanho);
        preencherRecursivamente(tabuleiro);
        return tabuleiro;
    }

    public void removerNumeros(Tabuleiro tabuleiro, int quantidade) {
        int tamanho = tabuleiro.getTamanho();
        int total = tamanho * tamanho;
        int removidos = 0;

        ResolvedorHelper resolvedor = new ResolvedorHelper();

        while (removidos < quantidade) {
            Posicao posicao = encontrarCelulaAleatoria(tabuleiro);
            int linha = posicao.getLinha();
            int coluna = posicao.getColuna();

            Celula celula = tabuleiro.getCelula(linha, coluna);
            if (celula.isEmpty() || celula.isFixo()) {
                continue;
            }

            Integer valorOriginal = celula.getValor().orElse(null);
            celula.setValor(null);
            celula.setFixo(false);

            // verificar se o tabuleiro ainda tem solução única
            Tabuleiro copia = tabuleiro.copiar();
            if (!resolvedor.temSolucaoUnica(copia)) {
                // restaurar o valor se a remoção causou múltiplas soluções
                celula.setValor(valorOriginal);
                celula.setFixo(true);
                continue;
            }

            removidos++;
        }
    }

    public int calcularQuantidadeRemover(Dificuldade dificuldade) {
        return dificuldade.getNumerosParaRemover();
    }

    private boolean preencherRecursivamente(Tabuleiro tabuleiro) {
        Optional<Posicao> posicaoVazia = encontrarProximaCelulaVazia(tabuleiro);
        if (posicaoVazia.isEmpty()) {
            // tá completo
            return true;
        }

        int linha = posicaoVazia.get().getLinha();
        int coluna = posicaoVazia.get().getColuna();

        List<Integer> valores = new ArrayList<>();
        for (int i = 1; i <= tabuleiro.getTamanho(); i++) {
            valores.add(i);
        }
        embaralharLista(valores);

        for (Integer valor : valores) {
            if (validador.validarPosicao(tabuleiro, linha, coluna, valor)) {
                tabuleiro.getCelula(linha, coluna).setValor(valor);

                if (preencherRecursivamente(tabuleiro)) {
                    return true;
                }

                // backtracking
                tabuleiro.getCelula(linha, coluna).setValor(null);
            }
        }

        return false;
    }

    private void embaralharLista(List<Integer> lista) {
        Collections.shuffle(lista, random);
    }

    private Posicao encontrarCelulaAleatoria(Tabuleiro tabuleiro) {
        int tamanho = tabuleiro.getTamanho();
        int linha = random.nextInt(tamanho);
        int coluna = random.nextInt(tamanho);
        return new Posicao(linha, coluna);
    }

    private Optional<Posicao> encontrarProximaCelulaVazia(Tabuleiro tabuleiro) {
        int tamanho = tabuleiro.getTamanho();
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (tabuleiro.getCelula(i, j).isEmpty()) {
                    return Optional.of(new Posicao(i, j));
                }
            }
        }
        return Optional.empty();
    }
}