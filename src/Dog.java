public class Dog extends Animal {
    public Dog(String name, int age, String food, String medication) {
        super(name, age, food, medication);
    }

    @Override
    public void makeSound() {
        System.out.println("Voff voff!");
    }
}
