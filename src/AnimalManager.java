import java.awt.*;
import java.util.List;
import java.util.Scanner;

public class AnimalManager {
    private Scanner scanner = new Scanner(System.in);
    private FileHandler fileHandler = new FileHandler();
    private OwnerManager ownerManager;

    public AnimalManager(OwnerManager ownerManager) {
        this.ownerManager = ownerManager;
    }

    public void checkInAnimal(Owner owner) {
        System.out.print("Ange djurets namn: ");
        String name = scanner.nextLine();
        Animal animal = owner.getAnimalByName(name);

        if (animal != null) {
            if (animal.isCheckedIn()) {
                System.out.println("Djuret " + name + " är redan incheckad.");
                Toolkit.getDefaultToolkit().beep();
            } else {
                animal.setCheckedIn(true);
                System.out.println(name + " har checkats in.");
                fileHandler.saveOwners(ownerManager.getAllOwners());
            }
        } else {
            System.out.println("Djuret hittades inte. Vill du registrera det? (Ja/Nej)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("JA")) {
                addAnimal(owner);
            }
        }
    }

    public void checkOutAnimal(Owner owner) {
        System.out.print("Ange djurets namn: ");
        String name = scanner.nextLine();
        Animal animal = owner.getAnimalByName(name);
        if (animal != null) {
            if (!animal.isCheckedIn()) {
                System.out.println("Djuret " + name + " är inte incheckat.");
            } else {
                animal.setCheckedIn(false);
                System.out.println(name + " har hämtats. Läte:");
                animal.makeSound();
                fileHandler.saveOwners(ownerManager.getAllOwners());
            }
        } else {
            System.out.println("Djuret hittades inte.");
        }
    }

    public void listAnimals(List<Owner> owners) {
        for (Owner owner : owners) {
            for (Animal animal : owner.getAnimals()) {
                String animalType = animal.getClass().getSimpleName(); // VILKEN TYP AV DJUR
                String status = animal.isCheckedIn() ? "Incheckad" : "Ej incheckad";
                System.out.println(
                        "Ägare: " + owner.getName() +
                                ", Tele: " + owner.getPhone() +
                                ", " + animalType +
                                ", Namn: " + animal.getName() +
                                ", Mat: " + animal.getFood() +
                                ", Medicin: " + animal.getMedication() +
                                ", Status: " + status
                );
            }
        }
    }

    public void addAnimal(Owner owner) {
        System.out.print("Ange djurets namn: ");
        String name = scanner.nextLine();
        System.out.print("Ange matvanor: ");
        String food = scanner.nextLine();
        System.out.print("Ange medicin: ");
        String medication = scanner.nextLine();
        System.out.print("Ange djurtyp (Hund, Katt, Fågel): ");
        String type = scanner.nextLine().toLowerCase();

        Animal animal;
        switch (type) {
            case "hund":
                animal = new Dog(name, food, medication);
                break;
            case "katt":
                animal = new Cat(name, food, medication);
                break;
            case "fågel":
                animal = new Bird(name, food, medication);
                break;
            default:
                System.out.println("Ogiltig djurtyp. Djuret registrerades inte.");
                return;
        }

        owner.addAnimal(animal);
        System.out.println("Djuret " + name + " har lagts till hos ägaren " + owner.getName() + ".");
    }

    public void listAnimalInfo(Owner owner) {
        if (owner.getAnimals().isEmpty()) {
            System.out.println(owner.getName() + " har inga registrerade djur.");
            return;
        }

        //System.out.println("Djur hos ägaren " + owner.getName() + ":");
        for (Animal animal : owner.getAnimals()) {
            System.out.println(animal.getInfo()); // Använd getInfo() för att hämta djurets information
        }
    }
}