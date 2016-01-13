package epic_code.sprites;

import epic_code.EpicTypedHandler;
import epic_code.helper.MathStuff;

/**
 * Created by m.lobanov on 16.12.2015
 *
 */
public class Particle {

    // Radiate
    public static int DIRECTION_EVERYWHERE = 1;
    // Only up spreading
    public static int DIRECTION_UP         = 2;

    // How far they'll fly
    public static Integer FLY_ITERATIONS = EpicTypedHandler.FLY_ITERATIONS;

    protected Integer tries = 0;
    protected int start_x;
    protected int start_y;

    protected double randX;
    protected double randY;

    protected int x;
    protected int y;
    protected float opacity;

    public Particle(Integer start_x, Integer start_y) {

        this.start_x = start_x;
        this.start_y = start_y;

        Integer direction = DIRECTION_UP; // todo: config

        // Direction
        if (direction == DIRECTION_UP) {
            randX = -1 + Math.random() * 2f;
            randY = -3.5f + Math.random() * 2f;
        } else {
            randX = MathStuff.randInt(-9, 9) / 10f;
            randY = MathStuff.randInt(-9, 9) / 10f;
        }
    }

    public boolean iterate() {
        if (tries > FLY_ITERATIONS) {
            return false;
        }
        // Fake gravity. Looks pretty enough
        start_y += Math.pow(tries, 2)/300;

        x = start_x + (int) Math.round(randX * tries);
        y = start_y + (int) Math.round(randY * tries);

        // Opacity calculated from passed way in percents
        opacity = Math.round(10f - tries/(FLY_ITERATIONS/100f)/10f)/10f;

        tries++;
        return true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getOpacity() {
        return opacity;
    }

}
