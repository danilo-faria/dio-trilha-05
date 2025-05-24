package enums;

public enum Dificuldade {
    FACIL(35),
    MEDIO(45),
    DIFICIL(55),
    EXTREMO(65);

    private final int numerosParaRemover;

    Dificuldade(int numerosParaRemover) {
        // quanto mais numeros para resolver, mais dificil né?
        this.numerosParaRemover = numerosParaRemover;
    }

    public int getNumerosParaRemover() {
        return numerosParaRemover;
    }
}

