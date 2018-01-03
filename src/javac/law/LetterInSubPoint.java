package javac.law;

/**
 * Builds LetterInSubPoint
 *
 * @author Xylini
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LetterInSubPoint {
    int start_index;
    int stop_index;
    String subPointLetter;
    List<String> content = new ArrayList<String>();

    /**
     * Builds content of LetterInSubPoint.
     *
     * @param start_index
     *              The index where LetterInSubPoint starts in cleanedContent.
     * @param stop_index
     *              The index where LetterInSubPoint stops in cleanedContent.
     * @param cleanedContent
     *              The cleaned full content of file.
     */
    public LetterInSubPoint(int start_index, int stop_index, List<String> cleanedContent){
        this.start_index = start_index;
        this.stop_index  = stop_index;
        this.subPointLetter = cleanedContent.get(start_index).split(" ")[0];
        for(int i = start_index; i < stop_index; i++){
            this.content.add(cleanedContent.get(i));
        }
    }

    /**
     * Prints the content of built LetterInSubPoint.
     */
    public void writeContent(){
        this.content.forEach(s -> System.out.println(s));
    }
}
