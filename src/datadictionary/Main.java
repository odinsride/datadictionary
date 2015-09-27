package datadictionary;

import java.io.FileOutputStream;

import java.util.HashMap;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import javax.xml.transform.dom.DOMSource;

import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * Main class that calls all other classes/functions and generates the document.
 * 
 * @author Kevin Custer
 * @author Sal Pol
 */
 
public class Main {

    public static void main(String[] args) throws Exception {
        String excelFilename = args[0];
        String xmlFilename = args[1];
        String tableList = args[2]; //cannot have blank lines
        
        HashMap<String,List<String[]>> map;
        map = BuildMap.buildMap(excelFilename);
        
        List<NameDescriptionPair> ndplist = BuildTableList.buildTableList(tableList);
        
        Document doc = BuildXML.buildDocument(map, ndplist);
        
        
        // the following converts Document object into a file
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new FileOutputStream(xmlFilename));
        transformer.transform(source, result);
    }
}
