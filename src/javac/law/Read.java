package javac.law;

/**
 * Reads given file path.
 *
 * @author Xylini
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Read {
    List<String> finalContent;

    /**
     * Reads and checks for correctness od given file's path.
     *
     * @param fileName
     *              The file's path.
     */

    public Read(String fileName) {
        String line = null;
        List<String> content = new ArrayList<String>();

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                content.add(line);
            }
            bufferedReader.close();
        }
         catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
         }
         catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
            ex.printStackTrace();
         }
         this.finalContent = content;
    }

    /**
     * Returns read file.
     *
     * @return
     *              Returns read file.
     */
    public List returnReadFile(){
        return this.finalContent;
    }
}
