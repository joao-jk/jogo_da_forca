import java.util.ArrayList;
import java.util.List;

/**
 * Estado e regras do jogo da forca.
 *
 * <p>Encapsula a palavra secreta, a palavra oculta (com traços) e a
 * lista de letras já tentadas. As letras são normalizadas para
 * maiúscula para evitar duplicidades por caso.</p>
 */
public class Verification {

    private String secretWord;
    private char[] hiddenWord;
    private List<Character> attemptedLetters;

    public Verification(String secretWord) {
        this.secretWord = secretWord.toUpperCase();
        this.attemptedLetters = new ArrayList<>();
        this.hiddenWord = new char[this.secretWord.length()];

        for (int i = 0; i < hiddenWord.length; i++) {
            this.hiddenWord[i] = '_';
        }
    }

    /**
     * Normaliza uma letra para maiúscula, centralizando a regra.
     */
    private char normalize(char letter) {
        return Character.toUpperCase(letter);
    }

    /**
     * Verifica se uma letra já foi tentada anteriormente.
     */
    public boolean jaFoiTentada(char letter) {
        return attemptedLetters.contains(normalize(letter));
    }

    /**
     * Registra uma tentativa de letra no estado do jogo.
     * <p>Não verifica se acertou — veja {@link #isCorrectGuess(char)}.</p>
     */
    public void registerAttempt(char letter) {
        attemptedLetters.add(normalize(letter));
    }

    /**
     * Consulta se uma letra aparece na palavra secreta.
     * <p>Não altera o estado — serve para o Main decidir se
     * deve descontar vida antes de registrar.</p>
     */
    public boolean isCorrectGuess(char letter) {
        char upperLetter = normalize(letter);
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == upperLetter) {
                return true;
            }
        }
        return false;
    }

    /**
     * Fachada que mantém compatibilidade: registra a tentativa e
     * revela a letra na palavra oculta se acertou.
     *
     * @return true se a letra aparece na palavra secreta
     */
    public boolean verificarChute(char letter) {
        char upperLetter = normalize(letter);
        attemptedLetters.add(upperLetter);

        boolean acertou = false;
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == upperLetter) {
                hiddenWord[i] = upperLetter;
                acertou = true;
            }
        }
        return acertou;
    }

    public String getPalavraComTracos() {
        String resultado = "";
        for (char c : hiddenWord) {
            resultado += c + " ";
        }
        return resultado;
    }

    public boolean acertouTudo() {
        for (char c : hiddenWord) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    public List<Character> getLetrasTentadas() {
        return attemptedLetters;
    }

    public String getPalavraSecreta() {
        return secretWord;
    }
}