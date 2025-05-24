package matriz;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Celula {
    private Integer valor;
    private boolean fixo;
    private Set<Integer> valoresPermitidos;

    public Celula() {
        this.valor = null;
        this.fixo = false;
        this.valoresPermitidos = new HashSet<>();
        inicializarValoresPermitidos();
    }

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