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
    private Map<String, Art> artMap;
    private Map<String, Chapter> chapMap;

    /**
     * Checks a kind of given document and makes an object form of it.
     *
     * @param cleanedContent
     *              Ready (cleaned, prepared) form of document.
     */
    public MergeContentClass(List<String> cleanedContent) {
        this.content = cleanedContent;

        int chapStart = 0;
        int chapStop = 0;
        int artStart = 0;
        int artStop = 0;

        chapMap = new LinkedHashMap<String, Chapter>();
        for (int i = 0; i < cleanedContent.size(); i++) {
            if (Pattern.matches("^(Rozdział [IXVL]+.*)|(DZIAŁ.*)$", cleanedContent.get(i))) {
                chapStart = i;
                i++;
                while (!Pattern.matches("^(Rozdział [IXVL]+.*)|(DZIAŁ.*)$", cleanedContent.get(i)) && i < cleanedContent.size()-1) {
                    i++;
                }
                if(i != cleanedContent.size()-1)
                    chapStop = i;
                else
                    chapStop = i+1;
                chapMap.put(cleanedContent.get(chapStart), new Chapter(chapStart, chapStop, cleanedContent));
                i--;
            }
        }

        artMap = new LinkedHashMap<String, Art>();
        this.chapMap.forEach((key, value) -> this.artMap.putAll(value.returnArts()));
    }

    public Map returnArtMap(){
        return this.artMap;
    }

    public Map returnChapterMap(){
        return this.chapMap;
    }

}
