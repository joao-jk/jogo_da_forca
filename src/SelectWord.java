import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Seleciona uma palavra aleatória e sua dica a partir do arquivo
 * de recursos {@code palavras.txt} (no classpath).
 *
 * Formato esperado do arquivo (uma entrada por linha):
 *   PALAVRA;DICA
 *
 * Linhas em branco e que começam com '#' são ignoradas.
 */
public class SelectWord {

    private static final String RESOURCE_PATH = "/palavras.txt";

    private final Map<String, String> words = new HashMap<>();
    private final Random random = new Random();
    private String selectedWord;
    private String hint;

    /**
     * Carrega o arquivo de palavras e seleciona uma entrada aleatória.
     *
     * @throws RuntimeException se o recurso não puder ser lido
     */
    public void getRandomWord() {
        loadWords();

        List<String> keysList = new ArrayList<>(words.keySet());
        int randomIndex = random.nextInt(keysList.size());
        this.selectedWord = keysList.get(randomIndex);
        this.hint = words.get(this.selectedWord);
    }

    public String getSelectedWord() {
        return selectedWord;
    }

    public String getHint() {
        return hint;
    }

    private void loadWords() {
        words.clear();

        InputStream input = SelectWord.class.getResourceAsStream(RESOURCE_PATH);
        if (input == null) {
            throw new RuntimeException(
                    "Arquivo de palavras não encontrado no classpath: " + RESOURCE_PATH
                            + ". Verifique se src/resources/palavras.txt existe."
            );
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(input, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split(";", 2);
                if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
                    // Pula entradas malformadas sem derrubar o jogo
                    continue;
                }

                words.put(parts[0].trim().toUpperCase(), parts[1].trim());
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo de palavras: " + e.getMessage(), e);
        }

        if (words.isEmpty()) {
            throw new RuntimeException("Nenhuma palavra válida encontrada em " + RESOURCE_PATH);
        }
    }
}
