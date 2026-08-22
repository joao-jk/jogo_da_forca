public class LifeManager {

    private int life;

    public LifeManager() {
        this.life = 6;
    }

    public int getLife() {
        return life;
    }

    public int deductLife(boolean error) {
        if (error) {
            this.life--;
        }
        return this.life;
    }

    public boolean isDead() {
        return this.life <= 0;
    }
}