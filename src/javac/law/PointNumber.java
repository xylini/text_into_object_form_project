package javac.law;
/**
 * Builds PointNumber
 *
 * @author Xylini
 */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class PointNumber {
    int start_index;
    int stop_index;
    String pointNumber;
    List<String> content = new ArrayList<String>();
    Map<String, SubPointNumber> subPoints = new LinkedHashMap<>();

    /**
     * Builds content of PointNumber and creates instances of SubPointNumber [class] (if contain).
     *
     * @param start_index
     *              The index where PointNumber starts in cleanedContent.
     * @param stop_index
     *              The index where PointNumber stops in cleanedContent.
     * @param cleanedContent
     *              The cleaned full content of file.
     */
    public PointNumber(int start_index, int stop_index, List<String> cleanedContent){
        this.start_index = start_index;
        this.stop_index = stop_index;
        this.pointNumber = cleanedContent.get(start_index).split(" ")[0];

        int pointStartIndex = 0;
        int pointStopIndex = 0;
        for(int i = start_index; i < stop_index; i++){
            if(Pattern.matches("^([0-9]+[a-z]?\\) .*)$", cleanedContent.get(i))) {
                String subPointKey = cleanedContent.get(i).split(" ")[0];
                pointStartIndex = i;
                i++;

                while (i < stop_index && !Pattern.matches("^([0-9]+[a-z]?\\) .*)$",cleanedContent.get(i))){
                    i++;
                }
                pointStopIndex = i;
                i--;
                this.subPoints.put(subPointKey, new SubPointNumber(pointStartIndex, pointStopIndex, cleanedContent));
            }
            else{
                this.content.add(cleanedContent.get(i));
            }
        }
    }

    /**
     * Prints the content of built PointNumber
     * and ask for the same created SubPointNumber.
     */
    public void writeContent(){
        this.content.forEach(s -> System.out.println(s));
        this.subPoints.forEach((key, value) -> value.writeContent());
    }

    /**
     *  Returns instance of SubPointNumber in SubPoint.
     * @param subPoint
     *              The name of wanted SubPointNumber in SubPoint.
     * @return
     *              Returns instance of SubPointNumber.
     */
    public SubPointNumber selectSubPoint(String subPoint){
        return this.subPoints.get(subPoint);
    }
}
