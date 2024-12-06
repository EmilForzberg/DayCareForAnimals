
public class Dog extends Animal {
    public Dog(String name, String food, String medication) {
        super(name, food, medication);
    }

    @Override
    public void makeSound() {
        System.out.println("Voff voff!");
    }
}
