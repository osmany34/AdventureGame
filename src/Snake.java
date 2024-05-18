import java.util.Random;

public class Snake extends Obstacle {
    public Snake() {
        super(4, "Yılan",randomDamage(), 12,0); // Para yerine eşya kazanma ihtimali olduğu için null
    }

    private static int randomDamage() {
        return new Random().nextInt(4) + 3; // 3 ve 6 arası rastgele hasar
    }

}

