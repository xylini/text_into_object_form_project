package javac.law;

/**
 * Builds Chapter.
 *
 * @author Xylini
 */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

class Chapter {
    int start_index;
    int stop_index; //stop_index means the first index of next Chapter
    String chapName;
    List<String> content;
    Map<String, SubChapter> subChapMap;
    Boolean hasSubChap;

    /**
     * Builds content of Chapter and creates instances of SubChapter [class].
     * If there is no SubChapter in it then creates an instance named "none",
     * which is whole Chapter except it's name and description.
     *
     * @param start_index
     *              The index where chapter starts in cleanedContent.
     * @param stop_index
     *              The index where chapter stops in cleanedContent.
     * @param cleanedContent
     *              The cleaned full content of file.
     */
    public Chapter(int start_index, int stop_index, List<String> cleanedContent){
        this.chapName = cleanedContent.get(start_index);
        this.start_index = start_index;
        this.stop_index = stop_index;
        this.content = new ArrayList<String>();
        this.content.add(cleanedContent.get(start_index));
        this.content.add(cleanedContent.get(start_index+1));

        int subChapStartIndex= 0;
        int subChapStopIndex = 0;

        this.subChapMap = new LinkedHashMap<String, SubChapter>();
        this.hasSubChap = true;
        for(int i = start_index+2; i < stop_index; i++){
            if(Pattern.matches("^([A-ZĘÓĄŚŁŻŹĆŃ]{2}.*)|(Rozdział [0-9]+[a-z]*.*)$", cleanedContent.get(i))){
                String pointKey = cleanedContent.get(i);
                subChapStartIndex = i;
                i++;

                while (i < stop_index && !Pattern.matches("^([A-ZĘÓĄŚŁŻŹĆŃ]{2}.*)|(Rozdział [0-9]+[a-z]*.*)$",cleanedContent.get(i))){
                    i++;
                }
                subChapStopIndex = i;
                i--;
                this.subChapMap.put(pointKey, new SubChapter(subChapStartIndex, subChapStopIndex, cleanedContent));
            }
            else
            if (Pattern.matches("^(Art.*)$", cleanedContent.get(i))){
                this.hasSubChap = false;
                subChapStartIndex = i;
                subChapStopIndex = stop_index;
                subChapMap.put("none", new SubChapter(subChapStartIndex, subChapStopIndex, cleanedContent));
                break;
            }
            else{
                this.content.add(cleanedContent.get(i));
            }
        }

    }

    /**
     * Prints the content of built Chapter
     * and ask for the same created SubChapters.
     */
    public void writeContent(){
        this.content.forEach(s -> System.out.println(s));
        this.subChapMap.forEach((key, value) -> value.writeContent());
    }

    /**
     * Prints the title and it's description of Chapter
     * and ask for the same created SubChapters.
     */
    public void writeTitle(){
        System.out.println(this.chapName + " - " + this.content.get(1));
        this.subChapMap.forEach((key, value) -> value.writeTitle());
    }

    /**
     * @return
     *              Returns every Arts which Chapter's SubChapters may own.
     */
    public Map returnArts(){
        Map<String, Art> arts = new LinkedHashMap<>();
        this.subChapMap.forEach((key,value) -> arts.putAll(value.returnArts()));

        return arts;
    }

    /**
     * Returns an instance of given subChap name if it is possessed by Chap.
     * @param subChap
     *              The precise name of wanted subChap.
     * @return
     *              Returns instance of SubChapter.
     */
    public SubChapter selectSubChap(String subChap){
        return this.subChapMap.get(subChap);
    }
}
