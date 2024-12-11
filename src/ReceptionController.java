public class ReceptionController {
    private OwnerManager ownerManager = new OwnerManager();
    private AnimalManager animalManager = new AnimalManager(ownerManager);
    private ReceptionView view = new ReceptionView();
    private static boolean returnToMenu = false;
    private FileHandler fileHandler = new FileHandler();

    public ReceptionController(AnimalManager animalManager, OwnerManager ownerManager, ReceptionView view, FileHandler filhandler) {
        this.animalManager = animalManager;
        this.ownerManager = ownerManager;
        this.fileHandler = filhandler;
        this.view = view;
    }

    public ReceptionController(){}

    public void start() {
        ownerManager.setOwners(fileHandler.loadOwners()); // Ladda ägare och incheckade djur

        boolean running = true;
        while (true) {
            returnToMenu = false;
            view.displayMenu();
            String choice = view.getInput().toLowerCase();

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
                    getInfoOfAnimal();
                    break;
                case "6":
                    String checkedInAnimals = getAllCheckedInAnimals();
                    if (checkedInAnimals != null) {

                        view.displayMessage(checkedInAnimals);
                        view.displayMessage("Det går inte att stänga programmet. Alla djur måste vara utcheckade!");
                    } else {
                        exitProgram();
                        running = false;
                    }
                    break;
                default:
                    view.displayMessage("Ogiltigt val. Försök igen.");
            }
        }
    }

    public String getAllCheckedInAnimals() {
        StringBuilder message = new StringBuilder("\nFöljande djur är fortfarande incheckade:\n");
        boolean hasCheckedInAnimals = false;

        for (Owner owner : ownerManager.getAllOwners()) {
            for (Animal animal : owner.getAnimals()) {
                if (animal.isCheckedIn()) {
                    hasCheckedInAnimals = true;
                    message.append("- ").append(animal.getName())
                            .append(" (Ägare: ").append(owner.getName()).append(")\n");
                }
            }
        }

        if (hasCheckedInAnimals) {
            return message.toString();
        } else {
            return null;
        }
    }

    private void exitProgram() {
        String checkedInAnimals = getAllCheckedInAnimals();

        if (checkedInAnimals != null) {
            view.displayMessage(checkedInAnimals);
            view.displayMessage("Det går inte att stänga programmet. Alla djur måste vara utcheckade!");
            return;
        }

        fileHandler.saveOwners(ownerManager.getAllOwners());
        view.displayMessage("\nAvslutar programmet. Tack för att du använde Djurdagiset!");
        System.exit(0);
    }

    public void checkIn() {
        view.displayMessage("\nAnge ägarens telefonnummer (eller skriv MENY för att återgå): ");
        String phone = getInput();
        if (returnToMenu) return;

        Owner owner = ownerManager.findOwner(phone);
        if (owner == null) {
            view.displayMessage("Ägare hittades inte. Vill du registrera en ny ägare? (Ja/Nej)");
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
        view.displayMessage("\nAnge ägarens telefonnummer (eller skriv MENY för att återgå): ");
        String phone = getInput();
        if (returnToMenu) return;

        Owner owner = ownerManager.findOwner(phone);
        if (owner == null) {
            view.displayMessage ("Ägare hittades inte.");
        } else {
            animalManager.checkOutAnimal(owner);
        }
    }

    public void getInfoOfAnimal(){
        view.displayMessage("\nAnge ägarens telefonnummer (eller skriv MENY för att återgå): ");
        String phone = getInput();

        Owner owner = ownerManager.findOwner(phone);
        if (owner == null) {
            view.displayMessage ("Ägare hittades inte.");
        } else {
            animalManager.listAnimalInfo(owner);
        }
    }

    public void listAnimals() {
        view.displayMessage ("\nÄgare & Djur"); // Visar alla ägare och deras djur
        animalManager.listAnimals(ownerManager.getAllOwners());
    }

    public void registerOwner() {
        view.displayMessage("\nAnge ägarens telefonnummer (eller skriv MENY för att återgå): ");
        String phone = getInput();
        if (returnToMenu) return;

        // Kontrollera om telefonnumret redan finns
        Owner existingOwner = ownerManager.findOwner(phone);
        if (existingOwner != null) {
            view.displayMessage("En ägare finns redan med detta telefonnummer.");
            view.displayMessage("Vill du lägga till ett djur till ägaren istället? (Ja/Nej): ");
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
        view.displayMessage("Ange ägarens namn (eller skriv MENY för att återgå): ");
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
