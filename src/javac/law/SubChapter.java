package javac.law;
/**
 * Builds SubChapter
 *
 * @author Xylini
 */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class SubChapter {
    int start_index;
    int stop_index;
    String subChapName;
    List<String> subChapDescription;
    List<String> content;
    Map<String, Art> artMap;

    /**
     * Builds content of SubChapter and creates instances of Art [class].
     *
     * @param start_index
     *              The index where chapter starts in cleanedContent.
     * @param stop_index
     *              The index where chapter stops in cleanedContent.
     * @param cleanedContent
     *              The cleaned full content of file.
     */
    public SubChapter(int start_index, int stop_index, List<String> cleanedContent){
        subChapDescription = new ArrayList<String>();
        if(Pattern.matches("^([A-ZĘÓĄŚŁŻŹĆŃ]{2}.*)|(Rozdział [0-9]+[a-z]*.*)$", cleanedContent.get(start_index))){
            this.subChapName = cleanedContent.get(start_index);
            if(Pattern.matches("^(Rozdział [0-9]+[a-z]*.*)$", cleanedContent.get(start_index)))
                for(int i = start_index+1; i < stop_index && !cleanedContent.get(i).matches("^(Art\\..*)$"); i++)
                    subChapDescription.add(cleanedContent.get(i));
        }
        else this.subChapName = "null";
        this.start_index = start_index;
        this.stop_index = stop_index;

        int artStartIndex= 0;
        int artStopIndex = 0;

        content = new ArrayList<String>();
        artMap = new LinkedHashMap<String, Art>();
        for(int i = start_index; i < stop_index; i++){
            if(Pattern.matches("^(Art.*)$", cleanedContent.get(i))){
                String pointKey = cleanedContent.get(i);
                artStartIndex = i;
                i++;

                while (i < stop_index && !Pattern.matches("^(Art.*)$",cleanedContent.get(i))){
                    i++;
                }
                artStopIndex = i;
                i--;
                this.artMap.put(pointKey, new Art(artStartIndex, artStopIndex, cleanedContent));
            }
            else{
                this.content.add(cleanedContent.get(i));
            }
        }
    }

    /**
     * Prints the content of built SubChapter
     * and ask for the same created Arts.
     */
    public void writeContent(){
        this.content.forEach(s -> System.out.println(s));
        this.artMap.forEach((key, value) -> value.writeContent());
    }

    /**
     * Prints the title and it's description of SubChapter.
     */
    public void writeTitle(){
        if(this.subChapName != "null") {
            System.out.println(this.subChapName);
            subChapDescription.forEach(s -> System.out.println(s));
        }
    }

    /**
     * @return
     *              Returns Map of possessed by itself Arts.
     */
    public Map returnArts(){
        return this.artMap;
    }
}
