package javac.law;
/**
 * Builds Art
 *
 * @author Xylini
 */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Art {
    int start_index;
    int stop_index;
    String artNumber;
    List<String> content;
    Map<String, PointNumber> points;
    Boolean hasNumbers;

    /**
     * Builds content of Art and creates instances of PointNumber [class].
     * If there is no PointNumber in it then creates an instance named "none",
     * which is whole Art except its name and art-level-content.
     *
     * @param start_index
     *              The index where Art starts in cleanedContent.
     * @param stop_index
     *              The index where Art stops in cleanedContent.
     * @param cleanedContent
     *              The cleaned full content of file.
     */
    public Art(int start_index, int stop_index, List<String> cleanedContent){
        this.artNumber = cleanedContent.get(start_index);
        this.start_index = start_index;
        this.stop_index = stop_index;

        int pointStartIndex= 0;
        int pointStopIndex = 0;

        content = new ArrayList<String>();
        points = new LinkedHashMap<>();
        hasNumbers = true;
        for(int i = start_index; i < stop_index; i++){
            if(Pattern.matches("^([0-9]+[a-z]?\\. .*)$", cleanedContent.get(i))){
                String pointKey = cleanedContent.get(i).split(" ")[0];
                pointStartIndex = i;
                i++;

                while (i < stop_index && !Pattern.matches("^([0-9]+[a-z]?\\. .*)$",cleanedContent.get(i))){
                    i++;
                }
                pointStopIndex = i;
                i--;
                this.points.put(pointKey, new PointNumber(pointStartIndex, pointStopIndex, cleanedContent));
            }
            else
                if (Pattern.matches("^([0-9]+[a-z]?\\) .*)$", cleanedContent.get(i))){
                    this.hasNumbers = false;
                    pointStartIndex = i;
                    pointStopIndex = stop_index;
                    points.put("none", new PointNumber(pointStartIndex, pointStopIndex, cleanedContent));
                    break;
                }
                else{
                    this.content.add(cleanedContent.get(i));
                }
        }
    }

    /**
     * Prints the content of built Art
     * and ask for the same created PointNumbers.
     */
    public void writeContent(){
        this.content.forEach(s -> System.out.println(s));
        this.points.forEach((key, value) -> value.writeContent());
    }


    /**
     * Returns instance of PointNumber in Art.
     * @param point
     *              The name of wanted point in Art.
     * @return
     *              Returns instance of PointNumber.
     */
    public PointNumber selectPoint(String point){
        return this.points.get(point);
    }


    /**
     * @return
     *              Returns start index of Art.
     */
    public int getStart_index(){
        return this.start_index;
    }
}
