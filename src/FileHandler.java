import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String FILE_NAME = "src/owners.txt";

    public void saveOwners(List<Owner> owners) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Owner owner : owners) {
                writer.write(owner.getName() + ";" + owner.getPhone());
                writer.newLine();

                for (Animal animal : owner.getAnimals()) {
                    writer.write(animal.getClass().getSimpleName() + ";" +
                            animal.getName() + ";" +
                            animal.getFood() + ";" +
                            animal.getMedication());
                    writer.newLine();
                }

                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Fel vid registrering av ägare: " + e.getMessage());
        }
    }

    public List<Owner> loadOwners() {
        List<Owner> owners = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            Owner currentOwner = null;

            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    currentOwner = null;
                    continue;
                }

                String[] parts = line.split(";");
                if (parts.length == 2) {
                    currentOwner = new Owner(parts[0], parts[1]);
                    owners.add(currentOwner);
                } else if (parts.length == 5 && currentOwner != null) {
                    String type = parts[0];
                    String name = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String food = parts[3];
                    String medication = parts[4];

                    Animal animal;
                    switch (type.toLowerCase()) {
                        case "dog":
                            animal = new Dog(name, food, medication);
                            break;
                        case "cat":
                            animal = new Cat(name, food, medication);
                            break;
                        case "bird":
                            animal = new Bird(name, food, medication);
                            break;
                        default:
                            System.out.println("Okänd djurtyp: " + type);
                            continue;
                    }
                    currentOwner.addAnimal(animal);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ingen tidigare data hittades. En ny fil skapas vid registrering av ägare.");
        } catch (IOException e) {
            System.out.println("Fel vid laddning av ägare: " + e.getMessage());
        }
        return owners;
    }
}
