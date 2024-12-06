import java.util.List;
import java.util.Scanner;

public class AnimalManager {
    private Scanner scanner = new Scanner(System.in);

    public void checkInAnimal(Owner owner) {
        System.out.print("Ange djurets namn: ");
        String name = scanner.nextLine();
        Animal animal = owner.getAnimalByName(name);
        if (animal != null) {
            System.out.println(name + " är nu incheckad.");
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
            System.out.println(name + " har hämtats. Läte: ");
            animal.makeSound();
        } else {
            System.out.println("Djuret hittades inte i systemet.");
        }
    }

    public void listAnimals(List<Owner> owners) {
        System.out.println("\nVisa Djur");
        for (Owner owner : owners) {
            for (Animal animal : owner.getAnimals()) {
                System.out.println("Ägare: " + owner.getName() + ", Djur: " + animal.getInfo());
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
}
