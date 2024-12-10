import java.util.Scanner;

public class ReceptionView {
    private Scanner scanner=new Scanner(System.in);

    public void displayMenu() {
        displayMessage ("\nVälkommen till Djurdagiset!");
        displayMessage ("1. Lämna djur");
        displayMessage ("2. Hämta djur");
        displayMessage ("3. Visa djur");
        displayMessage ("4. Registrera ny ägare");
        displayMessage ("5. Information om Djur");
        displayMessage ("6. Avsluta" );
        System.out.print("Välj ett alternativ: ");
    }
    public String getInput() {
        return scanner.nextLine().trim();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

}
