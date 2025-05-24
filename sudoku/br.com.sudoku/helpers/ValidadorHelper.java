package helpers;

import matriz.Celula;
import matriz.Tabuleiro;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ValidadorHelper {
    private int tamanhoQuadrante;

    public ValidadorHelper(int tamanhoTabuleiro) {
        this.tamanhoQuadrante = (int) Math.sqrt(tamanhoTabuleiro);
    }

    public boolean validarLinha(Tabuleiro tabuleiro, int linha, Integer valor) {
        return validarCollection(tabuleiro.getLinha(linha), valor);
    }

    public boolean validarColuna(Tabuleiro tabuleiro, int coluna, Integer valor) {
        return validarCollection(tabuleiro.getColuna(coluna), valor);
    }

    public boolean validarQuadrante(Tabuleiro tabuleiro, int linha, int coluna, Integer valor) {
        return validarCollection(tabuleiro.getQuadrante(linha, coluna), valor);
    }

    public boolean validarPosicao(Tabuleiro tabuleiro, int linha, int coluna, Integer valor) {
        if (!tabuleiro.isIndiceValido(linha, coluna)) {
            return false;
        }

        Celula celula = tabuleiro.getCelula(linha, coluna);
        if (celula.isFixo()) {
            return false;
        }

        return validarLinha(tabuleiro, linha, valor) &&
                validarColuna(tabuleiro, coluna, valor) &&
                validarQuadrante(tabuleiro, linha, coluna, valor);
    }

    public boolean validarTabuleiroCompleto(Tabuleiro tabuleiro) {
        int tamanho = tabuleiro.getTamanho();

        // verifica se todas as células estão preenchidas
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (tabuleiro.getCelula(i, j).isEmpty()) {
                    return false;
                }
            }
        }

        // checa conflitos
        return !temConflito(tabuleiro);
    }

    public boolean temConflito(Tabuleiro tabuleiro) {
        int tamanho = tabuleiro.getTamanho();

        // linhas
        for (int i = 0; i < tamanho; i++) {
            if (temValoresDuplicados(tabuleiro.getLinha(i))) {
                return true;
            }
        }

        // colunas
        for (int j = 0; j < tamanho; j++) {
            if (temValoresDuplicados(tabuleiro.getColuna(j))) {
                return true;
            }
        }

        // ou quadrantes
        for (int i = 0; i < tamanho; i += tamanhoQuadrante) {
            for (int j = 0; j < tamanho; j += tamanhoQuadrante) {
                if (temValoresDuplicados(tabuleiro.getQuadrante(i, j))) {
                    return true;
                }
            }
        }

        return false;
    }

    private int getInicioQuadrante(int posicao) {
        return (posicao / tamanhoQuadrante) * tamanhoQuadrante;
    }

    private boolean validarCollection(Collection<Celula> celulas, Integer valor) {
        // não pode existir o valor antes
        return celulas.stream()
                .map(Celula::getValor)
                .filter(Optional::isPresent)
                .noneMatch(v -> v.get().equals(valor));
    }

    private boolean temValoresDuplicados(Collection<Celula> celulas) {
        Set<Integer> valores = new HashSet<>();
        for (Celula celula : celulas) {
            Optional<Integer> valor = celula.getValor();
            if (valor.isPresent()) {
                if (valores.contains(valor.get())) {
                    return true;
                }
                valores.add(valor.get());
            }
        }
        return false;
    }
}
