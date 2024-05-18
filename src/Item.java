public abstract class Item {
    private String name;
    private int probability;

    public Item(String name, int probability) {
        this.name = name;
        this.probability = probability;
    }

    public String getName() {
        return name;
    }

    public int getProbability() {
        return probability;
    }

    public abstract void use();
}
