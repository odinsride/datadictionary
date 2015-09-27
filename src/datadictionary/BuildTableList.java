package datadictionary;

import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Reads from a text file containing Table Names and Descriptions.  Stores the
 * values in a NameDescriptionPair ArrayList.  Returns the ArrayList
 * 
 * @author Kevin Custer
 * @author Sal Pol
 */
 
public class BuildTableList {
    /**
     * Reads from a text file containing Table Names and Descriptions.  Stores the
     * values in a NameDescriptionPair ArrayList.  Returns the ArrayList
     * 
     * @param filename          The path/name of the list of tables text file
     * @return ndplist          ArrayList containing Table Names and Descriptions
     */
    public static List<NameDescriptionPair> buildTableList(String filename) throws Exception {
        
        BufferedReader br = new BufferedReader(new FileReader(filename));
        
        List<NameDescriptionPair> ndplist = new ArrayList<NameDescriptionPair>(20);
        List<String> description = null;
        String mess;
        NameDescriptionPair ndp;
        
        while ((mess = br.readLine())!=null){
            if(mess.charAt(0)!='\t'){
                description = new ArrayList<String>(10);
                ndp = new NameDescriptionPair(mess,description);
                ndplist.add(ndp);
            }
            else {
                description.add(mess);
            }
        }
        
        return ndplist;
    }
}
