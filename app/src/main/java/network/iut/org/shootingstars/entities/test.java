package network.iut.org.shootingstars.entities;

/**
 * Created by Android on 21/03/2017.
 */
public class test {
    private static test ourInstance = new test();

    public static test getInstance() {
        return ourInstance;
    }

    private test() {
    }
}
