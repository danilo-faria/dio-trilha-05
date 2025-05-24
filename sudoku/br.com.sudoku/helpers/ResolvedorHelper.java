package helpers;

import matriz.Posicao;
import matriz.Tabuleiro;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ResolvedorHelper {
    private final ValidadorHelper validador;

    public ResolvedorHelper() {
        // fazendo 9x9
        this.validador = new ValidadorHelper(9);
    }

    public boolean resolver(Tabuleiro tabuleiro) {
        return resolverBacktracking(tabuleiro);
    }

    public boolean temSolucaoUnica(Tabuleiro tabuleiro) {
        return contarSolucoes(tabuleiro) == 1;
    }

    public int contarSolucoes(Tabuleiro tabuleiro) {
        return contarSolucoesRecursivo(tabuleiro.copiar(), 2);
    }

    public Optional<Posicao> encontrarProximaCelulaVazia(Tabuleiro tabuleiro) {
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

    public Set<Integer> obterValoresPossiveis(Tabuleiro tabuleiro, int linha, int coluna) {
        Set<Integer> valores = new HashSet<>();
        int tamanho = tabuleiro.getTamanho();

        for (int valor = 1; valor <= tamanho; valor++) {
            if (validador.validarPosicao(tabuleiro, linha, coluna, valor)) {
                valores.add(valor);
            }
        }

        return valores;
    }

    private boolean resolverBacktracking(Tabuleiro tabuleiro) {
        Optional<Posicao> posicaoVazia = encontrarProximaCelulaVazia(tabuleiro);
        if (posicaoVazia.isEmpty()) {
            // tabuleiro completo
            return true;
        }

        int linha = posicaoVazia.get().getLinha();
        int coluna = posicaoVazia.get().getColuna();

        for (int valor = 1; valor <= tabuleiro.getTamanho(); valor++) {
            if (validador.validarPosicao(tabuleiro, linha, coluna, valor)) {
                tabuleiro.getCelula(linha, coluna).setValor(valor);

                if (resolverBacktracking(tabuleiro)) {
                    return true;
                }

                // backtracking
                tabuleiro.getCelula(linha, coluna).setValor(null);
            }
        }

        return false;
    }

    private int contarSolucoesRecursivo(Tabuleiro tabuleiro, int limite) {
        Optional<Posicao> posicaoVazia = encontrarProximaCelulaVazia(tabuleiro);
        if (posicaoVazia.isEmpty()) {
            // 1 solução encontrada
            return 1;
        }

        int linha = posicaoVazia.get().getLinha();
        int coluna = posicaoVazia.get().getColuna();
        int contagem = 0;

        for (int valor = 1; valor <= tabuleiro.getTamanho(); valor++) {
            if (validador.validarPosicao(tabuleiro, linha, coluna, valor)) {
                tabuleiro.getCelula(linha, coluna).setValor(valor);

                contagem += contarSolucoesRecursivo(tabuleiro, limite - contagem);

                // backtracking
                tabuleiro.getCelula(linha, coluna).setValor(null);

                // se já tem o número limite de soluções, pode parar
                if (contagem >= limite) {
                    break;
                }
            }
        }

        return contagem;
    }
}