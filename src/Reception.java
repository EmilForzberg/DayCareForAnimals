import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Reception {
    private static boolean returnToMenu = false; // Flagga för att återgå till huvudmenyn
    private OwnerManager ownerManager = new OwnerManager(); // Hanterar ägare
    private AnimalManager animalManager = new AnimalManager(ownerManager); // Hanterar djur
    private FileHandler fileHandler = new FileHandler(); // Hanterar filhantering
    private Scanner scanner = new Scanner(System.in); // Scanner

    public void start() {
        ownerManager.setOwners(fileHandler.loadOwners()); // Ladda ägare och incheckade djur

        while (true) {
            returnToMenu = false;
            displayMenu();
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                    checkIn();
                    break;
                case "2":
                    checkOut();
                    break;
                case "3":
                    listAnimals();
                    break;
                case "4":
                    registerOwner();
                    break;
                case "5":
                    exitProgram();
                    return;
                default:
                    System.out.println("Ogiltigt val. Försök igen.");
            }
        }
    }
    private void displayMenu() {
        System.out.println("\nVälkommen till Djurdagiset!");
        System.out.println("1. Lämna djur");
        System.out.println("2. Hämta djur");
        System.out.println("3. Visa djur");
        System.out.println("4. Registrera ny ägare");
        System.out.println("5. Avsluta");
        System.out.print("Välj ett alternativ: ");
    }

    private void checkIn() {
        System.out.print("Ange ägarens telefonnummer (eller skriv MENY för att återgå): ");
        String phone = getInput();
        if (returnToMenu) return;

        Owner owner = ownerManager.findOwner(phone);
        if (owner == null) {
            System.out.println("Ägare hittades inte. Vill du registrera en ny ägare? (Ja/Nej)");
            String response = getInput();
            if (returnToMenu) return;
            if (response.equalsIgnoreCase("JA")) {
                registerOwner();
            }
        } else {
            animalManager.checkInAnimal(owner);
        }
    }

    private void checkOut() {
        System.out.print("Ange ägarens telefonnummer (eller skriv MENY för att återgå): ");
        String phone = getInput();
        if (returnToMenu) return;

        Owner owner = ownerManager.findOwner(phone);
        if (owner == null) {
            System.out.println("Ägare hittades inte.");
        } else {
            animalManager.checkOutAnimal(owner);
        }
    }

    private void listAnimals() {
        System.out.println("\nVisa Djur"); // Visar alla ägare och deras djur
        animalManager.listAnimals(ownerManager.getAllOwners());
    }

    private void registerOwner() {
        System.out.print("Ange ägarens telefonnummer (eller skriv MENY för att återgå): ");
        String phone = getInput();
        if (returnToMenu) return;

        // Kontrollera om telefonnumret redan finns
        Owner existingOwner = ownerManager.findOwner(phone);
        if (existingOwner != null) {
            System.out.println("En ägare finns redan med detta telefonnummer.");
            System.out.print("Vill du lägga till ett djur till ägaren istället? (Ja/Nej): ");
            String response = getInput();
            if (returnToMenu) return;

            if (response.equalsIgnoreCase("JA")) {
                animalManager.addAnimal(existingOwner); // Lägg till nytt djur
                fileHandler.saveOwners(ownerManager.getAllOwners()); // Uppdaterar filen
                System.out.println("Djuret har registrerats till ägaren " + existingOwner.getName() + ".");
            } else {
                System.out.println("Återgår till huvudmenyn.");
            }
            return;
        }

        // Om telefonnumret inte finns, fortsätt med att registrera en ny ägare
        System.out.print("Ange ägarens namn (eller skriv MENY för att återgå): ");
        String name = getInput();
        if (returnToMenu) return;

        Owner newOwner = new Owner(name, phone);
        ownerManager.addOwner(newOwner);
        fileHandler.appendOwnerToFile(newOwner); // Spara den nya ägaren direkt i filen

        System.out.println("Ny ägare registrerad. Vill du lägga till ett djur? (Ja/Nej): ");
        String response = getInput();
        if (returnToMenu) return;

        if (response.equalsIgnoreCase("JA")) {
            animalManager.addAnimal(newOwner);
            fileHandler.saveOwners(ownerManager.getAllOwners()); // Uppdaterar filen
        }
    }

    private void exitProgram() {
        fileHandler.saveOwners(ownerManager.getAllOwners()); // Spara ägare och incheckade djur
        System.out.println("Avslutar programmet. Tack för att du använde Djurdagis!");
    }

    private String getInput() {
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("MENY")) {
            returnToMenu = true;
        }
        return input;
    }
}
