import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String FILE_NAME = "src/owners.txt";

    public void saveOwners(List<Owner> owners) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Owner owner : owners) {
                writeOwner(writer, owner);
            }
        } catch (IOException e) {
            System.out.println("Fel vid sparning av ägare: " + e.getMessage());
        }
    }

    private void writeOwner(BufferedWriter writer, Owner owner) throws IOException {
        writer.write(owner.getName() + ";" + owner.getPhone());
        writer.newLine();

        for (Animal animal : owner.getAnimals()) {
            writer.write(animal.getClass().getSimpleName() + ";" +
                    animal.getName() + ";" +
                    animal.getFood() + ";" +
                    animal.getMedication());
            writer.newLine();
        }
        writer.newLine(); // Tom rad mellan ägare i (owners.txt).
    }

    public void appendOwnerToFile(Owner owner) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writeOwner(writer, owner);
        } catch (IOException e) {
            System.out.println("Fel vid sparning av ägare: " + e.getMessage());
        }
    }

    public List<Owner> loadOwners() {
        List<Owner> owners = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            System.out.println("Läser in ägare från fil: " + FILE_NAME); // ENDAST FÖR DEBUG
            String line;
            Owner currentOwner = null;

            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    currentOwner = null;
                    continue;
                }

                String[] parts = line.split(";");
                if (parts.length == 2) {
                    System.out.println("Laddar ägare: " + parts[0] + ", telefon: " + parts[1]); // ENDAST FÖR DEBUG
                    currentOwner = new Owner(parts[0], parts[1]);
                    owners.add(currentOwner);
                } else if (parts.length == 4 && currentOwner != null) {
                    System.out.println("Hittade djur: " + parts[1] + " av typ: " + parts[0]); // ENDAST FÖR DEBUG
                    String type = parts[0];
                    String name = parts[1];
                    String food = parts[2];
                    String medication = parts[3];

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
                    System.out.println("Laddar djur: " + name + ", typ: " + type); // ENDAST FÖR DEBUG
                    currentOwner.addAnimal(animal);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ingen tidigare data hittades. En ny fil skapas vid sparning.");
        } catch (IOException e) {
            System.out.println("Fel vid laddning av ägare: " + e.getMessage());
        }
        return owners;
    }
}
