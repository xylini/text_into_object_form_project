package javac.law;
/**
 * Builds SubPointNumber
 *
 * @author Xylini
 */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class SubPointNumber {
    int start_index;
    int stop_index;
    String subPointNumber;
    List<String> content;
    Map<String, LetterInSubPoint> subPointLetters;

    /**
     * Builds content of SubPointNumber and creates instances of LetterInSubPoint [class] (if contain).
     *
     * @param start_index
     *              The index where SubPointNumber starts in cleanedContent.
     * @param stop_index
     *              The index where SubPointNumber stops in cleanedContent.
     * @param cleanedContent
     *              The cleaned full content of file.
     */
    public SubPointNumber(int start_index, int stop_index, List<String> cleanedContent){
        this.start_index = start_index;
        this.stop_index = stop_index;
        this.subPointNumber = cleanedContent.get(start_index).split(" ")[0];

        int letterStartIndex = 0;
        int letterStopIndex = 0;
        content = new ArrayList<String>();
        subPointLetters = new LinkedHashMap<>();
        for(int i = start_index; i < stop_index; i++){
            if(Pattern.matches("^([a-z]\\) .*)", cleanedContent.get(i))) {
                String subPointLetterKey = cleanedContent.get(i).split(" ")[0];
                letterStartIndex = i;
                i++;
                while (i < stop_index && !Pattern.matches("^([a-z]\\) .*)$",cleanedContent.get(i))){
                    i++;
                }
                letterStopIndex = i;
                i--;
                this.subPointLetters.put(subPointLetterKey, new LetterInSubPoint(letterStartIndex, letterStopIndex, cleanedContent));
            }
            else{
                this.content.add(cleanedContent.get(i));
            }
        }
    }

    /**
     * Prints the content of built SubPointNumber
     * and ask for the same created LetterInSubPoint.
     */
    public void writeContent(){
        this.content.forEach(s -> System.out.println(s));
        this.subPointLetters.forEach((key, value) -> value.writeContent());
    }

    /**
     *  Returns instance of SubPointNumber in SubPoint.
     * @param letter
     *              The name of wanted LetterInSubPoint in SubPointNumber.
     * @return
     *              Returns instance of LetterInSubPoint.
     */
    public LetterInSubPoint selectLetter(String letter){
        return this.subPointLetters.get(letter);
    }
}
