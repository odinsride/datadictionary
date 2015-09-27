package datadictionary;

import java.util.List;

/**
 * Defines an Object Type NameDescriptionPair, used to contain a Table Name
 * and its matching Description
 * 
 * @author Kevin Custer
 * @author Sal Pol
 */
 
public class NameDescriptionPair {
    
    private String tName;
    private List<String> tDescription;   // List because Description may have multiple lines
    
    
    /**
     * Constructor.  Sets tName and tDescription to values passed in
     * 
     * @param tName             Table Name
     * @param tDescription      Description - list item for each line
     */
    public NameDescriptionPair(String tName, List<String> tDescription) {
    
        this.tName = tName;
        this.tDescription = tDescription;      
    }
    
    /**
     * Accessor Methods
     */
    public void setTName(String tName) {
        this.tName = tName;
    }

    public String getTName() {
        return tName;
    }

    public void setTDescription(List<String> tDescription) {
        this.tDescription = tDescription;
    }

    public List<String> getTDescription() {
        return tDescription;
    }
}
