import java.util.ArrayList;
import java.util.List;

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

    public boolean jaFoiTentada(char letter) {
        char upperLetter = Character.toUpperCase(letter);
        return attemptedLetters.contains(upperLetter);
    }

    public boolean verificarChute(char letter) {
        char upperLetter = Character.toUpperCase(letter);
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