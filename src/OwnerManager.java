import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OwnerManager {
    private List<Owner> owners = new ArrayList<>();
    public void addOwner(Owner owner) {
        owners.add(owner);
    }
    Scanner sc=new Scanner(System.in);

    public Owner findOwner(String phone) {
        for (Owner owner : owners) {
            // System.out.println("Jämför med: " + owner.getPhone()); // ENDAST FÖR DEBUG
            if (owner.getPhone().trim().equals(phone.trim())) {
                return owner;
            }
        }
        return null;
    }

    public void changePetOwner(String oldOwnerPhone, FileHandler fileHandler) {
        Scanner scanner = new Scanner(System.in);

        Owner oldOwner = findOwner(oldOwnerPhone);
        if (oldOwner == null) {
            System.out.println("Ingen ägare med detta telefonnummer hittades." + oldOwnerPhone);
            return;
        }

        Animal petGettingNewOwner = selectPetFromOwner(oldOwner);
        if (petGettingNewOwner == null) {
            System.out.println("Inget giltigt djur valdes.");
            return;
        }

        System.out.println("Ange telefonnumret för det nya ägaren: ");
        String newOwnerPhone = scanner.nextLine();

        Owner newOwner = findOwner(newOwnerPhone);
        if (newOwner == null) {
            System.out.println("Ingen ägare hittad med detta telefonnummer: " + newOwnerPhone);
            System.out.println("Byter ägare...");
            if (newOwner == null) {
                System.out.println("Fel vi byte av ägare.");
                return;
            }
        }

        transferOwnershipForAnimal(oldOwner, newOwner, petGettingNewOwner);

        // UPPDATERAR TEXTFIL!
        fileHandler.saveOwners(owners);

        System.out.println("Djuret " + petGettingNewOwner.getName() + " har bytt ägare från "
                        + oldOwner.getName() + " till " + newOwner.getName() + ".");
    }

    public Animal selectPetFromOwner(Owner owner) {
        Scanner scanner = new Scanner(System.in);

        // OM ÄGARE HAR FLERA DJUR
        if (owner.getAnimals().isEmpty()) {
            System.out.println(owner.getName() + " har inga husdjur som kan bytas.");
            return null;
        }

        System.out.println("Vilket husdjur ska byta ägare? ");
        while (true) {
            for (Animal animal : owner.getAnimals()) {
                System.out.println("– " + animal.getName() + ".");
            }

            String selectedPet = scanner.nextLine().trim();
            for (Animal animal : owner.getAnimals()) {
                if (animal.getName().equalsIgnoreCase(selectedPet)) {
                    return animal;
                }
            }
            System.out.println("Djuret finns inte. Försök igen.");
        }
    }

    public void transferOwnershipForAnimal(Owner oldOwner, Owner newOwner, Animal petGettingNewOwner) {
        oldOwner.getAnimals().remove(petGettingNewOwner);
        newOwner.getAnimals().add(petGettingNewOwner);
    }

    // Returnerar en kopia av listan
    public List<Owner> getAllOwners() {
        return new ArrayList<>(owners);
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }



     //-------------------------------------------------------------//
    private Animal findPetByName(Owner owner, String petName) {
        for (Animal animal : owner.getAnimals()) {
            if (animal.getName().equalsIgnoreCase(petName)) {
                return animal;
            }
        }
        return null;
    }

    private Owner addToNewOwner(){
        System.out.printf("Ange ägarens telefonnummer");
        String phone= sc.nextLine().trim();
        Owner ownerToAdd = findOwner(phone);
        System.out.printf("Ange nya ägarens namn");
        String ownerName= sc.nextLine().trim();
       Owner changedOwner= new Owner(ownerName, phone);
        return changedOwner;
    }

    private Owner checkIfOwner(String phoneNumber) {
        for (Owner petOwner : owners) {
            if (petOwner.getPhone().equalsIgnoreCase(phoneNumber.trim())) {
                return petOwner;
            }
        }
        System.out.println("Det finns ingen ägare med det här telefonummret");
        return null;
    }

    public void changePetOwnership(String phoneNumber) {
        Animal pet = null;

        Owner oldOwner = checkIfOwner(phoneNumber);
        if (oldOwner == null) {
            return;
        }

        if (oldOwner.getAnimals() == null || oldOwner.getAnimals().isEmpty()) {
            System.out.println("Den här ägaren har inga husdjur");
            return;
        }

        System.out.println("Skapa ny djurägare: ");
        Owner newOwner= addToNewOwner();
       // Owner newOwner=; // anropa metod som lägger till i listan där uppe


        if (oldOwner.getAnimals().size() > 1) {
            while (pet == null) {
                System.out.println("Vilket husdjur ska byta ägare?: ");
                for (Animal animal : oldOwner.getAnimals()) {
                    System.out.println(animal.getName());
                }

                String whatPet = sc.nextLine();
                pet = findPetByName(oldOwner, whatPet);
                if (pet == null) {
                    System.out.println("Husdjuret finns inte. Försök igen: ");
                }
            }
        } else {
            pet = oldOwner.getAnimals().get(0);
        }
        oldOwner.removeAnimal(pet);
        newOwner.addAnimal(pet);
      //  oldOwner.getAnimals().remove(pet);
//        newOwner.getAnimals().add(pet);

        if (oldOwner.getAnimals() == null || oldOwner.getAnimals().isEmpty()) {
            owners.remove(oldOwner);
        }

        System.out.println("Djuret har bytt ägare");
    }
//----------------------------------------------------------------------------------------
}