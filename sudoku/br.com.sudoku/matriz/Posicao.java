package matriz;

import java.awt.Point;
import java.util.Objects;

public class Posicao extends Point {

    public Posicao(int linha, int coluna) {
        // como extendi da classe Point,
        //   x = coluna, y = linha
        super(coluna, linha);
    }

    public int getLinha() {
        return y;
    }

    public int getColuna() {
        return x;
    }

    public void setLinha(int linha) {
        y = linha;
    }

    public void setColuna(int coluna) {
        x = coluna;
    }

    public boolean isValida(int tamanhoTabuleiro) {
        // limita dentro do tabuleiro
        return y >= 0 && y < tamanhoTabuleiro &&
                x >= 0 && x < tamanhoTabuleiro;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Posicao posicao)) return false;
        return y == posicao.y && x == posicao.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + y + ", " + x + ")";
    }
}
