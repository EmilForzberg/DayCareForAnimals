public class Cat extends Animal {
    public Cat(String name, int age, String food, String medication) {
        super(name, age, food, medication);
    }

    @Override
    public void makeSound() {
        System.out.println("Mjau!");
    }
}
