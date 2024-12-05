public abstract class Animal {
    private String name;
    private int age;
    private String food;
    private String medication;

    public Animal(String name, int age, String food, String medication) {
        this.name = name;
        this.age = age;
        this.food = food;
        this.medication = medication;
    }

    public String getName() {
        return name;
    }

    public abstract void makeSound();

    public String getInfo() {
        return "Namn: " + name + ", Ålder: " + age + ", Mat: " + food + ", Medicin: " + medication;
    }
}

