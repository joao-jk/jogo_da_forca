import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        SelectWord selectWord;
        try {
            selectWord = new SelectWord();
            selectWord.getRandomWord();
        } catch (RuntimeException e) {
            System.out.println("Erro ao iniciar o jogo: " + e.getMessage());
            scanner.close();
            return;
        }

        Verification verification = new Verification(selectWord.getSelectedWord());
        LifeManager lifeManager = new LifeManager();

        System.out.println("=== JOGO DA FORCA ===");
        System.out.println("Dica: " + selectWord.getHint());
        System.out.println("Digite uma letra por vez. Boa sorte!\n");

        while (!verification.acertouTudo() && !lifeManager.isDead()) {
            System.out.println("\nPalavra: " + verification.getPalavraComTracos());
            System.out.println("Vidas restantes: " + lifeManager.getLife());
            System.out.println("Letras tentadas: " + verification.getLetrasTentadas());
            System.out.print("Digite uma letra: ");

            String entrada = scanner.next().trim().toUpperCase();

            if (entrada.length() != 1 || !Character.isLetter(entrada.charAt(0))) {
                System.out.println("Entrada inválida! Digite apenas UMA letra (A-Z).");
                continue;
            }

            char chute = entrada.charAt(0);

            if (verification.jaFoiTentada(chute)) {
                System.out.println("Você já tentou essa letra! Tente outra.");
                continue;
            }

            boolean acertou = verification.verificarChute(chute);

            if (acertou) {
                System.out.println("Boa! Acertou a letra.");
            } else {
                System.out.println("Errou! Você perdeu 1 vida.");
                lifeManager.deductLife(true);
            }
        }

        System.out.println("\n=========================");
        if (verification.acertouTudo()) {
            System.out.println("PARABÉNS! Você venceu!");
            System.out.println("Palavra: " + verification.getPalavraSecreta());
        } else {
            System.out.println("GAME OVER! Suas vidas acabaram.");
            System.out.println("A palavra era: " + verification.getPalavraSecreta());
        }
        System.out.println("=========================");

        scanner.close();
    }
}
