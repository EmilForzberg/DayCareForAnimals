import java.util.Scanner;

public class Reception {
    private static boolean returnToMenu = false; // Flagga för att återgå till huvudmenyn
    private OwnerManager ownerManager = new OwnerManager(); // Hanterar ägare
    private AnimalManager animalManager = new AnimalManager(); // Hanterar djur
    private FileHandler fileHandler = new FileHandler(); // Hanterar filhantering
    private Scanner scanner = new Scanner(System.in); // Scanner

    public void start() {
        // Ladda ägare från fil vid programstart
        ownerManager.setOwners(fileHandler.loadOwners());

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
        System.out.println("\n--- Visa Djur --- (Skriv MENY för att återgå)");
        animalManager.listAnimals(ownerManager.getAllOwners());
    }

    private void registerOwner() {
        System.out.print("Ange ägarens namn (eller skriv MENY för att återgå): ");
        String name = getInput();
        if (returnToMenu) return;

        System.out.print("Ange telefonnummer: ");
        String phone = getInput();
        if (returnToMenu) return;

        Owner owner = new Owner(name, phone);
        ownerManager.addOwner(owner);
        System.out.println("Ny ägare registrerad. Vill du lägga till ett djur? (ja/nej)");
        String response = getInput();
        if (returnToMenu) return;

        if (response.equalsIgnoreCase("ja")) {
            animalManager.addAnimal(owner);
        }
    }

    private void exitProgram() {
        // Spara ägare till fil vid avslut
        fileHandler.saveOwners(ownerManager.getAllOwners());
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
