package javac.law;

/**
 * Clears raw read file of unwanted signs and strings.
 *
 * @author Xylini
 */

import java.util.List;
import java.util.regex.Pattern;

public class ClearInput {
    List<String> cleanedFile;

    /**
     * Clears raw read file of unwanted signs and strings.
     *
     * @param rawContent
     *              The List of Strings with raw read file content.
     *
     */

    public ClearInput(List<String> rawContent) {
        List<String> file = rawContent;
        //removing trash linie in file's strings, ex. date, ©
        for (int i = 0; i < file.size(); i++) {
            if (Pattern.matches("^(.*\\d{4}-\\d{2}-\\d{2}|©.*)|\\w", file.get(i))) {
                file.remove(i);
                i--;
            }
        }

        //finds lines witch ends with '-' and reples it with first word from next line
        for (int i = 0; i < file.size(); i++) {
            //finds lines witch ends with '-'
            if (Pattern.matches(".*(-$)", file.get(i))) {
                //splits next line to get the part of prev line
                String[] splitstr = file.get(i + 1).split(" ", 2);

                //replaces "half-word" to "full-word" and if next line has only the missing part of word - removes it
                String changeStr = file.get(i).replaceFirst("(-$)", splitstr[0]);
                file.set(i, changeStr);

                if (splitstr.length > 1) {
                    file.set(i + 1, splitstr[1]);
                } else {
                    file.remove(i + 1);
                }
            }
            //moves a part of line to next one when it containt both art name and some point.
            if(Pattern.matches("^(Art\\. \\d+\\w*?\\.).+",file.get(i))){
                String[] splitArt = file.get(i).split(" ",3);
                splitArt[0] = splitArt[0] + " " + splitArt[1];
                file.set(i,splitArt[0]);
                file.add(i+1,splitArt[2]);
            }
        }
        this.cleanedFile = file;
    }

    /**
     * @return
     *              Returns cleaned given content.
     */
    public List returnStringList(){
        return this.cleanedFile;
    }
}
