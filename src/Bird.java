public class Bird extends Animal {
    public Bird(String name, int age, String food, String medication) {
        super(name, age, food, medication);
    }

    @Override
    public void makeSound() {
        System.out.println("Kvitter kvitter!");
    }
}

