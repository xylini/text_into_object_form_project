package javac.law;
/**
 * The class changes List of String to object form.
 *
 * @author Xylini
 */

import java.util.*;
import java.util.regex.Pattern;

public class MergeContentClass {
    private List<String> content;
    private Map<String, Art> artMap = new LinkedHashMap<String, Art>();
    private Map<String, Chapter> chapMap = new LinkedHashMap<String, Chapter>();

    /**
     * Checks a kind of given document and makes an object form of it.
     *
     * @param fullContent
     *              Ready (cleaned, prepared) form of document.
     */
    public MergeContentClass(List<String> fullContent) {
        this.content = fullContent;

        int chapStart = 0;
        int chapStop = 0;
        int artStart = 0;
        int artStop = 0;

        for (int i = 0; i < fullContent.size(); i++) {
            if (Pattern.matches("^(Rozdział [IXVL]+.*)|(DZIAŁ.*)$", fullContent.get(i))) {
                chapStart = i;
                i++;
                while (!Pattern.matches("^(Rozdział [IXVL]+.*)|(DZIAŁ.*)$", fullContent.get(i)) && i < fullContent.size()-1) {
                    i++;
                }
                if(i != fullContent.size()-1)
                    chapStop = i;
                else
                    chapStop = i+1;
                chapMap.put(fullContent.get(chapStart), new Chapter(chapStart, chapStop, fullContent));
                i--;
            }
        }

        this.chapMap.forEach((key, value) -> this.artMap.putAll(value.returnArts()));
    }

    public Map returnArtMap(){
        return this.artMap;
    }

    public Map returnChapterMap(){
        return this.chapMap;
    }

}
