package epic_code.helper;

/**
 * Created by m.lobanov on 16.12.2015
 *
 */
public class MathStuff {

    public static int randInt(int min, int max) {
        java.util.Random rand = new java.util.Random();
        Integer result = rand.nextInt((max - min) + 1) + min;
        return result == 0 ? randInt(min, max) : result;
    }

}
