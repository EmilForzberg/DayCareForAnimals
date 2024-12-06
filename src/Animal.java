
public abstract class Animal {
    private String name;
    private String food;
    private String medication;

    public Animal(String name, String food, String medication) {
        this.name = name;
        this.food = food;
        this.medication = medication;
    }

    public String getName() {
        return name;
    }

    public String getFood() {
        return food;
    }

    public String getMedication() {
        return medication;
    }

    public abstract void makeSound();

    public String getInfo() {
        return "Namn: " + name + ", Mat: " + food + ", Medicin: " + medication;
    }
}
