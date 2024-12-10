import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Reception {
    private static boolean returnToMenu = false; // Flagga för att återgå till huvudmenyn
    private OwnerManager ownerManager= new OwnerManager(); // Hanterar ägare
    private AnimalManager animalManager= new AnimalManager(ownerManager); // Hanterar djur
    private FileHandler fileHandler = new FileHandler(); // Hanterar filhantering
    private Scanner scanner = new Scanner(System.in); // Scanner
    private ReceptionView view=new ReceptionView();
    private ReceptionController controller=new ReceptionController(animalManager, ownerManager, view);

    public void start() {
        ownerManager.setOwners(fileHandler.loadOwners()); // Ladda ägare och incheckade djur

        while (true) {
            returnToMenu = false;
            view.displayMenu();
            String choice = view.getInput().toLowerCase();

            switch (choice) {
                case "1":
                    controller.checkIn();
                    break;
                case "2":
                    controller.checkOut();
                    break;
                case "3":
                   controller.listAnimals();
                    break;
                case "4":
                    controller.registerOwner();
                    break;
                case "5":
                    controller.getInfoOnAnimal();
                    break;
                case "6":
                    exitProgram();
                    return;
                default:
                    System.out.println("Ogiltigt val. Försök igen.");
            }
        }
    }

    private void exitProgram() {
        fileHandler.saveOwners(ownerManager.getAllOwners()); // Spara ägare och incheckade djur
        view.displayMessage ("Avslutar programmet. Tack för att du använde Djurdagis!");
    }


}
