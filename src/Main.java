import io.githib.arkobat.smas.test.*;

public class Main {

    public static void main(String[] args) {
        new KolmogorovSmirnov(100).test();
        System.out.println("\n\n");
        new ChiSq(10_000, 10).test();
        System.out.println("\n\n");
        new Runs().test();
        System.out.println("\n\n");
        new Autocorrelation().test();
    }

}