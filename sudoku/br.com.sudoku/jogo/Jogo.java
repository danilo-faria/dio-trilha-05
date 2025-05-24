package jogo;

import enums.Dificuldade;
import helpers.ResolvedorHelper;
import matriz.Celula;
import matriz.Posicao;
import matriz.Sudoku;
import matriz.Tabuleiro;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Stack;
import java.util.Base64;


/**
 * Classe que representa um jogo de Sudoku.
 *
 * Esta classe gerencia o estado do jogo, incluindo o tabuleiro, dificuldade, tempo de início,
 * pontuação e histórico de jogadas. Ela também fornece métodos para iniciar um novo jogo,
 * fazer jogadas, desfazer jogadas, obter dicas, salvar e carregar jogos.
 *
 * @author danilo-faria
 */
public class Jogo {
    private Sudoku sudoku;
    private Dificuldade dificuldade;
    private LocalDateTime tempoInicio;
    private int pontuacao;
    private Stack<Jogada> historicoJogadas;

    /**
     * Construtor da classe Jogo.
     *
     * @param dificuldade A dificuldade do jogo (FACIL, MEDIO, DIFICIL, EXTREMO).
     */
    public Jogo(Dificuldade dificuldade) {
        this.sudoku = new Sudoku();
        this.dificuldade = dificuldade;
        this.historicoJogadas = new Stack<>();
        this.tempoInicio = LocalDateTime.now();
        this.pontuacao = 0;
    }

    /**
     * Inicia um novo jogo de Sudoku com a dificuldade especificada.
     */
    public void novoJogo() {
        Gerador gerador = new Gerador();
        Tabuleiro tabuleiro = gerador.gerarTabuleiro(9, dificuldade);
        sudoku = new Sudoku();
        sudoku.carregarJogo(converterTabuleiroParaMatriz(tabuleiro));
        tempoInicio = LocalDateTime.now();
        pontuacao = 0;
        historicoJogadas.clear();
    }

    /**
     * Faz uma jogada no tabuleiro de Sudoku.
     *
     * @param linha A linha da célula onde a jogada será feita.
     * @param coluna A coluna da célula onde a jogada será feita.
     * @param valor O valor a ser inserido na célula.
     * @return true se a jogada foi bem-sucedida, false caso contrário.
     */
    public boolean fazerJogada(int linha, int coluna, Integer valor) {
        Tabuleiro tabuleiro = sudoku.getTabuleiro();
        if (!tabuleiro.isIndiceValido(linha, coluna)) {
            return false;
        }

        Celula celula = tabuleiro.getCelula(linha, coluna);
        if (celula.isFixo()) {
            return false;
        }

        Integer valorAnterior = celula.getValor().orElse(null);

        if (sudoku.fazerJogada(linha, coluna, valor)) {
            Jogada jogada = new Jogada(new Posicao(linha, coluna), valorAnterior, valor);
            historicoJogadas.push(jogada);
            return true;
        }

        return false;
    }

    /**
     * Desfaz a última jogada feita no tabuleiro de Sudoku.
     *
     * @return true se a jogada foi desfeita com sucesso, false caso contrário.
     */
    public boolean desfazerJogada() {
        if (historicoJogadas.isEmpty()) {
            return false;
        }

        Jogada jogada = historicoJogadas.pop();
        Posicao posicao = jogada.getPosicao();
        Integer valorAnterior = jogada.getValorAnterior();

        Tabuleiro tabuleiro = sudoku.getTabuleiro();
        Celula celula = tabuleiro.getCelula(posicao.getLinha(), posicao.getColuna());
        celula.setValor(valorAnterior);

        return true;
    }

    /**
     * Obtém uma dica para a próxima jogada.
     *
     * @return Uma posição sugerida para jogar, ou Optional.empty() se não houver dicas disponíveis.
     */
    public Optional<Posicao> obterDica() {
        ResolvedorHelper resolvedor = new ResolvedorHelper();
        Tabuleiro tabuleiroResolvido = sudoku.getTabuleiro().copiar();

        if (resolvedor.resolver(tabuleiroResolvido)) {
            Tabuleiro tabuleiroAtual = sudoku.getTabuleiro();
            int tamanho = tabuleiroAtual.getTamanho();

            for (int i = 0; i < tamanho; i++) {
                for (int j = 0; j < tamanho; j++) {
                    Celula celulaAtual = tabuleiroAtual.getCelula(i, j);
                    if (celulaAtual.isEmpty()) {
                        Integer valorCorreto = tabuleiroResolvido.getCelula(i, j).getValor().orElse(null);
                        if (valorCorreto != null) {
                            return Optional.of(new Posicao(i, j));
                        }
                    }
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Salva o estado atual do jogo em uma string codificada em Base64.
     *
     * @return A string codificada em Base64 representando o estado do jogo.
     */
    public String salvarJogo() {
        StringBuilder sb = new StringBuilder();

        // dificuldade
        sb.append(dificuldade.name()).append(";");

        // tempo de início
        sb.append(tempoInicio.toString()).append(";");

        // pontuação atual
        sb.append(pontuacao).append(";");

        // tabuleiro
        Tabuleiro tabuleiro = sudoku.getTabuleiro();
        int tamanho = tabuleiro.getTamanho();

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                Celula celula = tabuleiro.getCelula(i, j);
                sb.append(celula.getValor().orElse(0)).append(",");
                sb.append(celula.isFixo() ? "1" : "0").append(";");
            }
        }

        // joga para Base64 para facilitar armazenamento
        return Base64.getEncoder().encodeToString(sb.toString().getBytes());
    }

    /**
     * Carrega um jogo salvo a partir de uma string codificada em Base64.
     *
     * @param dadosJogoBase64 A string codificada em Base64 representando o estado do jogo.
     */
    public void carregarJogo(String dadosJogoBase64) {
        try {
            String dados = new String(Base64.getDecoder().decode(dadosJogoBase64));
            String[] partes = dados.split(";");

            int indice = 0;

            // carrega dificuldade
            this.dificuldade = Dificuldade.valueOf(partes[indice++]);

            // carrega tempo de início
            this.tempoInicio = LocalDateTime.parse(partes[indice++]);

            // carrega pontuação
            this.pontuacao = Integer.parseInt(partes[indice++]);

            // cria novo tabuleiro
            Tabuleiro tabuleiro = new Tabuleiro(9);
            sudoku = new Sudoku();

            // extrai as células
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (indice >= partes.length) {
                        break;
                    }

                    String[] dadosCelula = partes[indice++].split(",");
                    int valor = Integer.parseInt(dadosCelula[0]);
                    boolean fixo = "1".equals(dadosCelula[1]);

                    Celula celula = new Celula(valor > 0 ? valor : null, fixo);
                    tabuleiro.setCelula(i, j, celula);
                }
            }

            // limpa histórico de jogadas ao carregar um jogo salvo
            this.historicoJogadas.clear();

            // carrega o tabuleiro no sudoku
            sudoku.carregarJogo(converterTabuleiroParaMatriz(tabuleiro));

        } catch (Exception e) {
            // se falhar, aí começa vazio
            novoJogo();
        }
    }

    /**
     * Obtém a dificuldade atual do jogo.
     *
     * @return A dificuldade atual do jogo.
     */
    public int calcularPontuacao() {
        // chat GPT:
        // Fórmula para cálculo de pontuação baseada em:
        // - Dificuldade do jogo
        // - Tempo decorrido
        // - Número de dicas utilizadas
        // - Número de jogadas desfeitas

        int pontuacaoBase = switch (dificuldade) {
            case FACIL -> 1000;
            case MEDIO -> 2000;
            case DIFICIL -> 3000;
            case EXTREMO -> 5000;
        };

        // Penalidade por tempo (quanto mais rápido, melhor)
        Duration tempoDecorrido = getTempoDecorrido();
        long segundos = tempoDecorrido.getSeconds();
        double fatorTempo = Math.max(0.1, 1.0 - (segundos / 3600.0)); // Máximo de 1 hora para pontuação ideal

        // Penalidade por desfazer jogadas
        int penalidade = historicoJogadas.size() * 10;

        // Cálculo final
        this.pontuacao = (int) (pontuacaoBase * fatorTempo) - penalidade;

        // Garantir pontuação mínima
        this.pontuacao = Math.max(0, this.pontuacao);

        return this.pontuacao;
    }

    public boolean isJogoCompleto() {
        return sudoku.isCompleto();
    }

    public Duration getTempoDecorrido() {
        return Duration.between(tempoInicio, LocalDateTime.now());
    }

    /**
     * Reinicia o jogo, mantendo a mesma dificuldade, mas reiniciando o tabuleiro.
     */
    public void reiniciar() {
        // manter a mesma dificuldade, mas reiniciar o tabuleiro
        Tabuleiro tabuleiro = sudoku.getTabuleiro();

        // resetar células não fixas
        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            for (int j = 0; j < tabuleiro.getTamanho(); j++) {
                Celula celula = tabuleiro.getCelula(i, j);
                if (!celula.isFixo()) {
                    celula.reset();
                }
            }
        }

        // resetar histórico e tempo
        historicoJogadas.clear();
        tempoInicio = LocalDateTime.now();
        pontuacao = 0;
    }

    /**
     * Reinicia o jogo, mantendo a mesma dificuldade, mas reiniciando o tabuleiro.
     */
    private int[][] converterTabuleiroParaMatriz(Tabuleiro tabuleiro) {
        // método auxiliar para converter um Tabuleiro em matriz de inteiros
        int tamanho = tabuleiro.getTamanho();
        int[][] matriz = new int[tamanho][tamanho];

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                Celula celula = tabuleiro.getCelula(i, j);
                matriz[i][j] = celula.getValor().orElse(0);
            }
        }

        return matriz;
    }

    public Tabuleiro getTabuleiro() {
        return sudoku.getTabuleiro();
    }
}