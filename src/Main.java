import java.io.*;
import itumulator.executable.Program;


public class Main {

    public static void main(String[] args) throws IOException {
//indsæt navnet på den fil du gerne vil indlæse fra data mappen
        inputReader in = new inputReader("data/t4-1a.txt");
        in.readInput();
        Program p = in.getP();
        p.show(); // viser selve simulationen
        for (int i = 0; i < 200; i++) {
            p.simulate();// kører 200 runder, altå kaldes 'act' 200 gange for alle placerede aktører

        }

    }
}

