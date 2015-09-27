package datadictionary;

import com.Ostermiller.util.CSVParser;

import com.Ostermiller.util.ExcelCSVParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Reads from a CSV formatted Excel File and builds/returns a HashMap object
 * containing the data from the CSV file.
 * 
 * @author Sal Pol
 */

public class BuildMap {
   
    /**
     * Reads from a CSV formatted Excel File and builds/returns a HashMap object
     * containing the data from the CSV file.
     * 
     * @param fileName          The path/name of the CSV file to be read
     * @return hm               The HashMap created from the CSV file
     */
    static HashMap<String, List<String[]>> buildMap(String fileName) throws IOException, FileNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ExcelCSVParser tokenizer = new ExcelCSVParser(fis);
        HashMap<String,List<String[]>> hm=new HashMap<String,List<String[]>>(1500);
        String[] line;
        String[] header=tokenizer.getLine();
        while((line=tokenizer.getLine())!=null){
            List<String[]> temp=hm.get(line[0]);
            if(line[0].equals(""))
                continue;
            if(temp==null){
                temp=new ArrayList<String[]>();
                hm.put(line[0],temp);
            }
            temp.add(line);
        }
        
        return hm;
    }
}
