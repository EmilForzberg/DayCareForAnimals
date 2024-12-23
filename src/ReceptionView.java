import java.util.Scanner;

public class ReceptionView {
    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu() {
        displayMessage("\nVälkommen till Djurdagiset!\n");
        displayMessage("1. Lämna Djur");
        displayMessage("2. Hämta Djur");
        displayMessage("3. Visa Ägare & Djur");
        displayMessage("4. Registrera Ny Ägare");
        displayMessage("5. Byt Ägare");
        displayMessage("6. Information Om Djur");
        displayMessage("7. Avsluta");
        System.out.print("\nVälj Ett Alternativ: ");
    }

    public String getInput() {
        return scanner.nextLine().trim();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

}
