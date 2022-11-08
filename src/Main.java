import io.githib.arkobat.smas.test.*;

public class Main {

    public static void main(String[] args) {
        new KolmogorovSmirnov(100).test();
        new ChiSq(10_000, 10).test();
        new Runs().test();
        new Autocorrelation().test();
    }

}