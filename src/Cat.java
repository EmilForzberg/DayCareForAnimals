
public class Cat extends Animal {
    public Cat(String name, String food, String medication) {
        super(name, food, medication);
    }

    @Override
    public void makeSound() {
        System.out.println("Mjau!");
    }
}
