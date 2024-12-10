import java.util.Scanner;

public class ReceptionController {
    private AnimalManager animalManager;
    private OwnerManager ownerManager;
    private ReceptionView view;
    private static boolean returnToMenu = false;
    private FileHandler fileHandler;

    public ReceptionController(AnimalManager animalManager, OwnerManager ownerManager, ReceptionView view) {
        this.animalManager = animalManager;
        this.ownerManager = ownerManager;
        this.view = view;
    }

    public void checkIn() {
        view.displayMessage("Ange ägarens telefonnummer (eller skriv MENY för att återgå): ");
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

    public void checkOut() {
        view.displayMessage("Ange ägarens telefonnummer (eller skriv MENY för att återgå): ");
        String phone = getInput();
        if (returnToMenu) return;

        Owner owner = ownerManager.findOwner(phone);
        if (owner == null) {
            view.displayMessage ("Ägare hittades inte.");
        } else {
            animalManager.checkOutAnimal(owner);
        }
    }

    public void getInfoOnAnimal(){
        view.displayMessage("Ange ägarens telefonnummer");
        String phone= getInput();

        Owner owner = ownerManager.findOwner(phone);
        if (owner == null) {
            view.displayMessage ("Ägare hittades inte.");
        } else {
            animalManager.listAnimalInfo(owner);
        }

    }

    public void listAnimals() {
        view.displayMessage ("\nVisa Djur"); // Visar alla ägare och deras djur
        animalManager.listAnimals(ownerManager.getAllOwners());
    }

    public void registerOwner() {
        view.displayMessage("Ange ägarens telefonnummer (eller skriv MENY för att återgå): ");
        String phone = getInput();
        if (returnToMenu) return;

        // Kontrollera om telefonnumret redan finns
        Owner existingOwner = ownerManager.findOwner(phone);
        if (existingOwner != null) {
            view.displayMessage("En ägare finns redan med detta telefonnummer.");
            System.out.print("Vill du lägga till ett djur till ägaren istället? (Ja/Nej): ");
            String response = getInput();
            if (returnToMenu) return;

            if (response.equalsIgnoreCase("JA")) {
                animalManager.addAnimal(existingOwner); // Lägg till nytt djur
                fileHandler.saveOwners(ownerManager.getAllOwners()); // Uppdaterar filen
                view.displayMessage ("Djuret har registrerats till ägaren " + existingOwner.getName() + ".");
            } else {
                view.displayMessage ("Återgår till huvudmenyn.");
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

        view.displayMessage ("Ny ägare registrerad. Vill du lägga till ett djur? (Ja/Nej): ");
        String response = getInput();
        if (returnToMenu) return;

        if (response.equalsIgnoreCase("JA")) {
            animalManager.addAnimal(newOwner);
            fileHandler.saveOwners(ownerManager.getAllOwners()); // Uppdaterar filen
        }
    }

    public String getInput() {
        String input = view.getInput().trim();
        if (input.equalsIgnoreCase("MENY")) {
            returnToMenu = true;
        }
        return input;
    }

}
