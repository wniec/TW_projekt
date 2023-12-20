package tw.projekt;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author macwozni
 */
public class Checker {

    // machine precision epsilon
    static double epsilon = 0.00001;

    /**
     * @param args should be 2 strings with addresses of 2 files with matrixes at given format
     * @throws FileNotFoundException if the give file does not exist we just frow exception - from main subroutine...
     * @throws IOException if there is some problem with IO we just frow exception - from main subroutine...
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        // print arguments count
        System.out.println(args.length);
        // print arguments list
        for (String arg : args) {
            System.out.println(arg);
        }
        
        // if there are more or less arguments then 2 file addresses
        if (args.length != 2) {
            System.err.print("wrong amount of arguments");
            System.exit(1);
        }
        // read input file
        // a file with source matrix
        // parse file and create data structure
        Matrix source = Matrix.apply(args[0]);

        // solve
        source.solve();

        // read output file
        // a file with processed/solved matrix
        // parse file and create data structure
        Matrix processed = Matrix.apply(args[1]);

        // in processed/solved matrix it should be 1 on diagonal and 0 elsewhere
        for (int i = 0; i < processed.size(); i++) {
            for (int j = 0; j < processed.size(); j++) {
                // if diagonal - should be 1.0
                if (i == j) {
                    // if it is not 1.0 - print it to the output and exit
                    if (!Matrix.compare(1., processed.lhs()[i][j], epsilon)) {
                        System.out.println("Error 1 " + i + " " + j);
                        System.exit(0);
                    }
                    //if  not diagonal - should be 0.0
                } else if (!Matrix.compare(0., processed.lhs()[i][j], epsilon)) {
                    // if it is not 0.0 - print it to the output and exit
                    System.out.println("Error 2 " + i + " " + j);
                    System.exit(0);
                }
            }
        }

        System.out.print(source.array()[0][0]);
        for (int j = 1; j < processed.size(); j++) {
            System.out.print(" ");
            System.out.print(source.array()[j][0]);
        }
        System.out.println();
        
        System.out.print(processed.rhs()[0]);
        for (int j = 1; j < processed.size(); j++) {
            System.out.print(" ");
            System.out.print(processed.rhs()[j]);
        }
        System.out.println();
        
        // check RHS vector - should be equal to the one from solved here initial problem
        for (int j = 0; j < processed.size(); j++) {
            if (!Matrix.compare(source.rhs()[j], processed.rhs()[j], epsilon)) {
                // if it is not equal - print it to the output and exit
                System.out.println("Error 3 " + (processed.size() + 1) + " " + j);
                System.out.println(source.rhs()[j] + " " + processed.rhs()[j]);
                System.exit(0);
            }
        }
    }
}
