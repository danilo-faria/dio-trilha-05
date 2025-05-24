package matriz;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Classe que representa uma célula de um Sudoku.
 * Cada célula pode conter um valor entre 1 e 9 ou estar vazia (null).
 * A célula também pode ser fixa (não pode ser alterada) ou não fixa.
 * Além disso, a célula mantém um conjunto de valores permitidos.
 */
public class Celula {
    private Integer valor;
    private boolean fixo;
    private Set<Integer> valoresPermitidos;

    /**
     * Construtor padrão da classe Celula.
     * Inicializa a célula como vazia (null) e não fixa.
     */
    public Celula() {
        this.valor = null;
        this.fixo = false;
        this.valoresPermitidos = new HashSet<>();
        inicializarValoresPermitidos();
    }

    /**
     * Construtor da classe Celula.
     *
     * @param valor O valor inicial da célula (pode ser null).
     * @param fixo  Indica se a célula é fixa (não pode ser alterada).
     */
    public Celula(Integer valor, boolean fixo) {
        this.valor = valor;
        this.fixo = fixo;
        this.valoresPermitidos = new HashSet<>();
        if (valor == null) {
            inicializarValoresPermitidos();
        }
    }

    public Optional<Integer> getValor() {
        return Optional.ofNullable(valor);
    }

    public void setValor(Integer valor) {
        if (!fixo) {
            this.valor = valor;
        }
    }

    public boolean isFixo() {
        return fixo;
    }

    public void setFixo(boolean fixo) {
        //fixo pra travar mudanças
        this.fixo = fixo;
    }

    public Set<Integer> getValoresPermitidos() {
        return new HashSet<>(valoresPermitidos);
    }

    public void adicionarValorPermitido(Integer valor) {
        valoresPermitidos.add(valor);
    }

    public void removerValorPermitido(Integer valor) {
        // remove valor permitido para facilitar quando ja esta em uso
        valoresPermitidos.remove(valor);
    }

    public void limparValoresPermitidos() {
        valoresPermitidos.clear();
    }

    /**
     * Inicializa os valores permitidos para a célula.
     * Neste momento, todos os valores de 1 a 9 são permitidos.
     */
    public void inicializarValoresPermitidos() {
        limparValoresPermitidos();
        // nesse momento todos os valores sao permitidos ainda
        for (int i = 1; i <= 9; i++) {
            valoresPermitidos.add(i);
        }
    }

    public boolean isValorPermitido(Integer valor) {
        return valoresPermitidos.contains(valor);
    }

    public boolean isEmpty() {
        return valor == null;
    }

    public void reset() {
        if (!fixo) {
            valor = null;
            inicializarValoresPermitidos();
        }
    }

    @Override
    public Celula clone() {
        Celula clone = new Celula(this.valor, this.fixo);
        clone.valoresPermitidos = new HashSet<>(this.valoresPermitidos);
        return clone;
    }

    @Override
    public String toString() {
        return valor != null ? valor.toString() : " ";
    }
}