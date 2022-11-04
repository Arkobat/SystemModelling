import io.githib.arkobat.smas.test.*;

public class Main {

    public static void main(String[] args) {
        new KolmogorovSmirnov(100).test();
        new ChiSq().test();
        new Runs().test();
        new Autocorrelation().test();
    }

}