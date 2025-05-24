
# Sudoku Java

Projeto de Sudoku em Java, com geração automática de tabuleiros, diferentes níveis de dificuldade, validação de jogadas, sistema de pontuação, dicas, histórico de jogadas (desfazer/refazer) e persistência do estado do jogo.

## Funcionalidades

- Geração de tabuleiros válidos de Sudoku (9x9) com solução única
- Níveis de dificuldade: Fácil, Médio, Difícil, Extremo
- Validação automática de jogadas
- Sistema de pontuação baseado em tempo, dificuldade e ações do jogador
- Dicas automáticas para ajudar o jogador
- Histórico de jogadas (desfazer última jogada)
- Salvar e carregar o estado do jogo (Base64)
- Reinício do jogo mantendo a dificuldade

## Estrutura do Projeto

- `matriz/`: Representação do tabuleiro, células e posições
    - `Tabuleiro.java`: Matriz de células do Sudoku
    - `Celula.java`: Representa cada célula do tabuleiro
    - `Posicao.java`: Representa uma posição (linha, coluna)
    - `Sudoku.java`: Lógica principal do jogo Sudoku
- `jogo/`: Lógica de controle do jogo
    - `Jogo.java`: Gerencia o estado do jogo, pontuação, histórico, dicas, salvar/carregar
    - `Jogada.java`: Representa uma jogada feita pelo jogador
    - `Gerador.java`: Gera tabuleiros válidos e aplica dificuldade
- `helpers/`: Utilitários de validação e resolução
    - `ValidadorHelper.java`: Valida regras do Sudoku
    - `ResolvedorHelper.java`: Resolve o Sudoku (backtracking)
- `enums/`
    - `Dificuldade.java`: Enumeração dos níveis de dificuldade
- `Main.java`: Exemplo de uso da API do jogo

## Exemplo de Uso

```java
import enums.Dificuldade;
import jogo.Jogo;

public class Main {
    public static void main(String[] args) {
        Jogo jogo = new Jogo(Dificuldade.MEDIO);
        jogo.novoJogo();
        System.out.println(jogo.getTabuleiro());

        jogo.fazerJogada(0, 1, 5);

        if (jogo.isJogoCompleto()) {
            System.out.println("Parabéns! Você completou o Sudoku!");
            System.out.println("Sua pontuação: " + jogo.calcularPontuacao());
        }

        String dadosSalvos = jogo.salvarJogo();
        // jogo.carregarJogo(dadosSalvos);
    }
}
```


# Mermaid

Para isso, o diagrama abaixo faz o desenho das classes:

```mermaid
classDiagram
    class Sudoku {
        -tabuleiro: Tabuleiro
        -validador: ValidadorSudoku
        +Sudoku()
        +inicializarJogo(): void
        +carregarJogo(matriz: int[][]): void
        +validarJogada(linha: int, coluna: int, valor: int): boolean
        +fazerJogada(linha: int, coluna: int, valor: int): boolean
        +isCompleto(): boolean
        +getTabuleiro(): Tabuleiro
        +resolver(): boolean
        +toString(): String
    }

    class Tabuleiro {
        -grid: List~List~Celula~~
        -tamanho: int
        +Tabuleiro(tamanho: int)
        +getCelula(linha: int, coluna: int): Celula
        +setCelula(linha: int, coluna: int, celula: Celula): void
        +getTamanho(): int
        +getGrid(): List~List~Celula~~
        +limpar(): void
        +copiar(): Tabuleiro
        +isIndiceValido(linha: int, coluna: int): boolean
        +getLinha(linha: int): List~Celula~
        +getColuna(coluna: int): List~Celula~
        +getQuadrante(linha: int, coluna: int): List~Celula~
        +toString(): String
    }

    class Celula {
        -valor: Integer
        -fixo: boolean
        -valoresPermitidos: Set~Integer~
        +Celula()
        +Celula(valor: Integer, fixo: boolean)
        +getValor(): Optional~Integer~
        +setValor(Integer valor): void
        +isFixo(): boolean
        +setFixo(boolean fixo): void
        +getValoresPermitidos(): Set~Integer~
        +adicionarValorPermitido(Integer valor): void
        +removerValorPermitido(Integer valor): void
        +limparValoresPermitidos(): void
        +inicializarValoresPermitidos(): void
        +isValorPermitido(Integer valor): boolean
        +isEmpty(): boolean
        +reset(): void
        +clone(): Celula
        +toString(): String
    }

    class ValidadorSudoku {
        -tamanhoQuadrante: int
        +ValidadorSudoku(tamanhoTabuleiro: int)
        +validarLinha(tabuleiro: Tabuleiro, linha: int, valor: Integer): boolean
        +validarColuna(tabuleiro: Tabuleiro, coluna: int, valor: Integer): boolean
        +validarQuadrante(tabuleiro: Tabuleiro, linha: int, coluna: int, valor: Integer): boolean
        +validarPosicao(tabuleiro: Tabuleiro, linha: int, coluna: int, valor: Integer): boolean
        +validarTabuleiroCompleto(tabuleiro: Tabuleiro): boolean
        +temConflito(tabuleiro: Tabuleiro): boolean
        -getInicioQuadrante(posicao: int): int
        -validarCollection(celulas: Collection~Celula~, valor: Integer): boolean
    }

    class GeradorSudoku {
        -random: Random
        -validador: ValidadorSudoku
        +GeradorSudoku()
        +gerarTabuleiro(tamanho: int, dificuldade: Dificuldade): Tabuleiro
        +gerarTabuleiroCompleto(tamanho: int): Tabuleiro
        +removerNumeros(tabuleiro: Tabuleiro, quantidade: int): void
        +calcularQuantidadeRemover(dificuldade: Dificuldade): int
        -preencherRecursivamente(tabuleiro: Tabuleiro): boolean
        -embaralharLista(lista: List~Integer~): void
        -encontrarCelulaAleatoria(tabuleiro: Tabuleiro): Posicao
    }

    class ResolvedorSudoku {
        -validador: ValidadorSudoku
        +ResolvedorSudoku()
        +resolver(tabuleiro: Tabuleiro): boolean
        +temSolucaoUnica(tabuleiro: Tabuleiro): boolean
        +contarSolucoes(tabuleiro: Tabuleiro): int
        +encontrarProximaCelulaVazia(tabuleiro: Tabuleiro): Optional~Posicao~
        +obterValoresPossiveis(tabuleiro: Tabuleiro, linha: int, coluna: int): Set~Integer~
        -resolverBacktracking(tabuleiro: Tabuleiro): boolean
        -contarSolucoesRecursivo(tabuleiro: Tabuleiro, limite: int): int
    }

    class Posicao {
        -linha: int
        -coluna: int
        +Posicao(linha: int, coluna: int)
        +getLinha(): int
        +getColuna(): int
        +setLinha(int linha): void
        +setColuna(int coluna): void
        +isValida(tamanhoTabuleiro: int): boolean
        +equals(Object obj): boolean
        +hashCode(): int
        +toString(): String
    }

    class JogoSudoku {
        -sudoku: Sudoku
        -dificuldade: Dificuldade
        -tempoInicio: LocalDateTime
        -pontuacao: int
        -historicoJogadas: Stack~Jogada~
        +JogoSudoku(dificuldade: Dificuldade)
        +novoJogo(): void
        +fazerJogada(linha: int, coluna: int, valor: Integer): boolean
        +desfazerJogada(): boolean
        +obterDica(): Optional~Posicao~
        +salvarJogo(): String
        +carregarJogo(dadosJogo: String): void
        +calcularPontuacao(): int
        +isJogoCompleto(): boolean
        +getTempoDecorrido(): Duration
        +reiniciar(): void
    }

    class Jogada {
        -posicao: Posicao
        -valorAnterior: Integer
        -valorNovo: Integer
        -timestamp: LocalDateTime
        +Jogada(posicao: Posicao, valorAnterior: Integer, valorNovo: Integer)
        +getPosicao(): Posicao
        +getValorAnterior(): Integer
        +getValorNovo(): Integer
        +getTimestamp(): LocalDateTime
        +toString(): String
    }

    class Dificuldade {
        <<enumeration>>
        FACIL(35)
        MEDIO(45)
        DIFICIL(55)
        EXTREMO(65)
        -numerosParaRemover: int
        +Dificuldade(numerosParaRemover: int)
        +getNumerosParaRemover(): int
    }

    %% Relacionamentos
    Sudoku "1" --> "1" Tabuleiro : possui
    Sudoku "1" --> "1" ValidadorSudoku : usa
    Tabuleiro "1" --> "81" Celula : contém
    JogoSudoku "1" --> "1" Sudoku : gerencia
    JogoSudoku "1" --> "1" Dificuldade : tem
    JogoSudoku "1" --> "*" Jogada : histórico
    GeradorSudoku --> Tabuleiro : cria
    GeradorSudoku --> Dificuldade : usa
    GeradorSudoku --> ValidadorSudoku : usa
    ResolvedorSudoku --> Tabuleiro : resolve
    ResolvedorSudoku --> Posicao : usa
    ResolvedorSudoku --> ValidadorSudoku : usa
    ValidadorSudoku --> Tabuleiro : valida
    Jogada --> Posicao : referencia
```
