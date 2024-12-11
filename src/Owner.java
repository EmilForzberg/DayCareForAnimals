import java.util.ArrayList;
import java.util.List;

public class Owner {
    private String name;
    private String phone;
    private List<Animal> animals = new ArrayList<>();

    public Owner(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
    public Owner(){
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public Animal getAnimalByName(String name) {
        for (Animal animal : animals) {
            if (animal.getName().equalsIgnoreCase(name)) {
                return animal;
            }
        }
        return null;
    }

    public List<Animal> getAnimals() {
        return animals;
    }
//----------------------------------------------
    public void removeAnimal(Animal animal){
        for (var animal1 : animals) {
            if (animal1.getName().equalsIgnoreCase(animal.getName())){
                animals.remove(animal);
            }else System.out.printf("FEL");
        }


    }
    //------------------------------------------------
}
