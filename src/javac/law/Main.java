package javac.law;

/**
 *
 * @author Xylini
 */

public class Main {

    public static void main(String[] args) {
        //preparing input
        String input = "";
        for (String arg : args)
            input = input + arg + " ";
        input = input.substring(0, input.lastIndexOf(" "));

        //the job
        CmdOptionParser run = new CmdOptionParser(input);
    }
}
