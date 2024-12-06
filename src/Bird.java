
public class Bird extends Animal {
    public Bird(String name, String food, String medication) {
        super(name, food, medication);
    }

    @Override
    public void makeSound() {
        System.out.println("Kvitter-kvitter!");
    }
}

