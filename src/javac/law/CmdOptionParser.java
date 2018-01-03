/**
 * @author Xylini
 *
 * CmdOptionParser parses command line input
 * and prints wanted elements of given input file
 */

package javac.law;

import java.util.*;

public class CmdOptionParser {
    private String [] cmdList;
    private List<String> fileContent;
    private MergeContentClass inputFile;
    private Map<String, Art> artMap;
    private Map<String, Chapter> chapMap;
    private String help = "Poprawna forma wejścia to: \"[ścieżka_pliku] [tryb_działania] [dokładna_nazwa_elementu]\"\n"+
                      "np. 1)\"C:\\Users\\Jakub\\IdeaProjects\\law\\konstytucja.txt -t całość\"\n"+
                      "    2)\"konstytucja.txt -t Art. 4.,2.\" \n"+
                      "    3)\"uokik.txt -t DZIAŁ II,Rozdział 3\"\n"+
                      "    4)\"uokik.txt -t DZIAŁ II\", itp.\n"+
                      "    5)\"konstytucja.txt -s całość\"\n"+
                      "    6)\"konstytucja.txt -s Rozdział VIII\"\n"+
                      "    7)\"konstytucja.txt -t Art. 72.:Art. 80.\" - dwukropkiem rozdzielamy zakres artykułów\n"+
                      "Przykładowa nazwa specyficznego elementu: \"Art. 111.,3.,1),b)\", bądź po prostu \"całość\"\n"+
                      "Podawane wyróżnione elementy powinny być oddzielane przecinkiem, jw.\n"+
                      "Dostępne tryby działania: -t - treść, -s - spis treści.\n";

    /**
     * Converts given arguments cmd to choose what should be done.
     *
     * @param cmd
     *              Command line input.
     */

    public CmdOptionParser(String cmd){
        this.cmdList = cmd.split(" ",3);
        this.fileContent = new ClearInput(new Read(this.cmdList[0]).returnReadFile()).returnStringList();
        this.inputFile =  new MergeContentClass(this.fileContent);
        this.artMap = this.inputFile.returnArtMap();
        this.chapMap = this.inputFile.returnChapterMap();

        if(this.cmdList[1].equals("-t"))
            writeElement(this.cmdList[2]);
        else if(this.cmdList[1].equals("-s"))
            writeTable(this.cmdList[2]);
    }

    /**
     * Chooses what kind of element users wants to print and do it.
     *
     * @param track
     *              A String witch contains path to selected element.
     */
    private void writeElement(String track){
        String [] elements = track.split("(,)|(:)",2);
        try{
            if(track.matches("^(Art. [1-9][0-9a-z]*\\.,[1-9][0-9a-z]*\\.,[0-9]+[a-z]?\\),[a-z]\\))$"))
                this.artMap.get(elements[0]).selectPoint(elements[1]).selectSubPoint(elements[2]).selectLetter(elements[3]).writeContent();
            else if(track.matches("^(Art. [1-9][0-9a-z]*\\.,[1-9][0-9a-z]*\\.,[0-9]+[a-z]?\\))$"))
                this.artMap.get(elements[0]).selectPoint(elements[1]).selectSubPoint(elements[2]).writeContent();
            else if(track.matches("^(Art. [1-9][0-9a-z]*\\.,[1-9][0-9a-z]*\\.)$"))
                this.artMap.get(elements[0]).selectPoint(elements[1]).writeContent();
            else if(track.matches("^(Art. [1-9][0-9a-z]*\\.)$"))
                this.artMap.get(elements[0]).writeContent();
            else if(track.matches("^(Art. [1-9][0-9a-z]*\\.,[0-9]+[a-z]?\\),[a-z]\\))$"))
                this.artMap.get(elements[0]).selectPoint("none").selectSubPoint(elements[1]).selectLetter(elements[2]).writeContent();
            else if(track.matches("^(Art. [1-9][0-9a-z]*\\.,[0-9]+[a-z]?\\))$"))
                this.artMap.get(elements[0]).selectPoint("none").selectSubPoint(elements[1]).writeContent();
            else if(track.matches("^((Rozdział|DZIAŁ) [IXVL]+[A-Z]*)$"))
                this.chapMap.get(elements[0]).writeContent();
            else if(track.matches("^((Rozdział|DZIAŁ) [IXVL]+[A-Z]*),(Rozdział [0-9]+[a-z]*)$"))
                this.chapMap.get(elements[0]).selectSubChap(elements[1]).writeContent();
            else if(track.matches("^((Rozdział|DZIAŁ) [IXVL]+[A-Z]*),([A-ZĘÓĄŚŁŻŹĆŃ ,]{2,})$"))
                this.chapMap.get(elements[0]).selectSubChap(elements[1]).writeContent();
            else if(track.matches("^(całość)$"))
                this.chapMap.forEach((key, value) -> value.writeContent());
            else if(track.matches("^(Art\\. .*\\.:Art\\. .*\\.)$"))
                writeArtRange(elements[0],elements[1]);
            else{
                System.out.println("Coś poszło nie tak.");
                System.out.println(this.help);
            }

        }

        catch(NullPointerException ex){
            System.out.println("Podany specyficzny element nie istnieje.");
        }
    }

    /**
     * Chooses what kind of Table od Content users wants to print.
     *
     * @param track
     *              The String with precise name of Chapter or "całość" when whole.
     */
    private void writeTable(String track){
        if(track.matches("całość"))
            this.chapMap.forEach((key, value) -> value.writeTitle());
        else if(track.matches("^((Rozdział|DZIAŁ) [IXVL]+[A-Z]*)$") && this.chapMap.containsKey(track))
            this.chapMap.get(track).writeTitle();
        else{
            System.out.println("Coś poszło nie tak.");
            System.out.println(this.help);
        }
    }

    /**
     * Writes Range of given Arts.
     *
     * @param start
     *              The precise name of start Art.
     * @param stop
     *              The precise name of stop Art.
     */

    private void writeArtRange(String start, String stop) {
        Boolean canIStart = false;

        if (this.artMap.containsKey(start) && this.artMap.containsKey(stop) && start != stop && this.artMap.get(start).getStart_index() < this.artMap.get(stop).getStart_index()) {
            for (Map.Entry<String, Art> entry : this.artMap.entrySet()) {
                if (Objects.equals(entry.getKey(), start)) {
                    canIStart = true;
                } else if (canIStart && Objects.equals(entry.getKey(), stop)) {
                    entry.getValue().writeContent();
                    break;
                }
                if (canIStart) {
                    entry.getValue().writeContent();
                }
            }
        } else {
            System.out.println("Zły przedział, bądź wprowadzono nieistniejący artykuł. Wprowadz jeszcze raz.");
        }
    }

}
