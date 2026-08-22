import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SelectWord {
    private final Map<String, String> words = Map.of(
            "JAVA", "Linguagem de programação orientada a objetos",
            "INTELLIJ", "Ambiente de desenvolvimento integrado (IDE)",
            "GIT", "Sistema de controle de versão distribuído",
            "LINUX", "Sistema operacional de código aberto"
    );

    private final Random random = new Random();
    private String selectedword;
    private String hint;

    public void getRandomWord() {
        List<String> keyslist = new ArrayList<>(words.keySet());

        int randomIndex = random.nextInt(keyslist.size());
        this.selectedword = keyslist.get(randomIndex);
        this.hint = words.get(this.selectedword);
    }

    public String getSelectedWord() {
        return selectedword;
    }

    public String getHint() {
        return hint;
    }
}
