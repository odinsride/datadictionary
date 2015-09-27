package datadictionary;

import java.util.HashMap;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Pulls in data from HashMap and TableList and generates an XML (XHTML)
 * document formatted in the manner used in Design Documentation.
 * 
 * @author Kevin Custer
 * @author Sal Pol
 */
 
public class BuildXML {
    
    /**
     * Main function used to build document.  Creates the document object and
     * calls sub function handleHTML to build individual sections of the 
     * document
     * 
     * @param map                   The HashMap containing data
     * @param ndplist               The List of NameDescriptionPair objects
     *                              from the Table List file
     * @return doc                  The generated document object
     */
    public static Document buildDocument(HashMap<String,List<String[]>> map, 
        List<NameDescriptionPair> ndplist) throws Exception{
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docbuilder = factory.newDocumentBuilder();
        Document doc = docbuilder.newDocument();
        
        handleHTML(doc,map,ndplist);
        
        return doc;
    }
    
    /**
     * Builds the root HTML element and calls sub functions handleHeader
     * and handleBody to build the corresponding sections of the document.
     * 
     * @param doc                   The document object
     * @param map                   The HashMap containing data
     * @param ndp                   The List of NameDescriptionPair objects
     *                              from the Table List file
     */
    public static void handleHTML(Document doc, HashMap<String,List<String[]>> map, 
        List<NameDescriptionPair> ndp) {
        
        Element html = doc.createElement("html");
        doc.appendChild(html);
        handleHeader(doc, html);
        handleBody(doc,map,ndp, html);
    
    }
    /**
     * Builds the HEAD section of the document, with calls to irs.css stylesheet
     * 
     * @param doc                     The document object
     * @param parent                  The containing parent element (html)
     */
    public static void handleHeader(Document doc, Element parent){
        
        Element link = doc.createElement("link");
        Element head = doc.createElement("head");
        parent.appendChild(head);
        head.appendChild(link);
        link.setAttribute("href", "irs.css");
        link.setAttribute("rel", "stylesheet");
        link.setAttribute("type", "text/css");
        
    }
    
    /**
     * Builds the BODY section of the document.  Calls handleTable function to
     * build out tables.
     * 
     * @param doc                   The document object
     * @param map                   The HashMap containing data
     * @param ndplist               The List of NameDescriptionPair objects
     *                              from the Table List file
     * @param parent                The containing parent element (HTML)
     * 
     */
    public static void handleBody(Document doc, HashMap<String,List<String[]>> map, 
        List<NameDescriptionPair> ndplist, Element parent){
        
        Element body = doc.createElement("body");
        
        parent.appendChild(body);
        
        // will build an HTML table for each database table contained in the
        // table list file
        for(NameDescriptionPair ndp:ndplist) {

            String tablename = ndp.getTName();
            List<String> descr = ndp.getTDescription();
            List<String[]> columns = map.get(tablename);
    
            handleTable(doc, map, ndplist, columns, tablename, descr, body);
            
        }
        
    }
    
    /**
     * Builds out an HTML table for the database table name passed in.  This
     * function handles the DB table name and description, html table declaration 
     * and header row, and calls the handleRows function to handle data.
     * 
     * @param doc                   The document object
     * @param map                   The HashMap containing data
     * @param ndplist               The List of NameDescriptionPair objects
     *                              from the Table List file
     * @param columns               A List of database table columns from the
     *                              HashMap
     * @param tablename             The current table name from the Table List
     *                              file being processed.
     * @param descr                 The current table description from the Table
     *                              List file being processed.
     * @param parent                The parent containing element (BODY)
     */
    public static void handleTable(Document doc, HashMap<String,List<String[]>> map, 
        List<NameDescriptionPair> ndplist, List<String[]> columns, String tablename, 
        List<String> descr, Element parent){
            
        Element table = doc.createElement("table");
        Element thead = doc.createElement("thead");
        Element h1 = doc.createElement("h1");
        Element h2 = doc.createElement("h2");
        Element p1 = doc.createElement("p");
        Element p2 = doc.createElement("p");
        
        Element td1 = doc.createElement("td");
        Element td2 = doc.createElement("td");
        Element td3 = doc.createElement("td");
        Element td4 = doc.createElement("td");
        Element td5 = doc.createElement("td");
        
        parent.appendChild(h1);
        h1.appendChild(doc.createTextNode(tablename));
        parent.appendChild(p1);
    
        // loop through each line of the description
        for(String descline:descr){
            Element div = doc.createElement("div");
            p1.appendChild(div);
            div.appendChild(doc.createTextNode(descline));
        }
        
        parent.appendChild(p2);
        p2.appendChild(table);
        table.setAttribute("border", "1");
        table.setAttribute("cellpadding", "0");
        table.setAttribute("cellspacing", "0");
        table.appendChild(thead);
        
        thead.appendChild(td1);
          td1.appendChild(doc.createTextNode("Column Name"));
        thead.appendChild(td2);
          td2.appendChild(doc.createTextNode("Column Datatype"));
        thead.appendChild(td3);
          td3.appendChild(doc.createTextNode("Primary Key"));
          td3.setAttribute("align", "center");
        thead.appendChild(td4);
          td4.appendChild(doc.createTextNode("Column Comment"));
        thead.appendChild(td5);
          td5.appendChild(doc.createTextNode("Derivation"));
          
        handleRows(doc, columns, table);
        
    }
    /**
     * Generates the rows for the table.  Appends any notes to the derivation
     * field.  Converts all PK's to either Y or Null.
     * 
     * @param doc                   The document object
     * @param columns               A List of database table columns from the
     *                              HashMap
     * @param parent                The parent containing element (TABLE)
     */
    public static void handleRows(Document doc, List<String[]> columns, Element parent){
        
        String col;
        String datatype;
        String pk;
        String comment;
        String derivation;
        String notes;
        
        for(String[] str:columns){
            Element tr = doc.createElement("tr");
            Element td1 = doc.createElement("td");
            Element td2 = doc.createElement("td");
            Element td3 = doc.createElement("td");
            Element td4 = doc.createElement("td");
            Element td5 = doc.createElement("td");
            Element div1 = doc.createElement("div"); // derivation
            Element div2 = doc.createElement("div"); // notes
            
            col = str[1];
            datatype = str[2];
            pk = str[3];
            comment = str[5];
            derivation = str[6];
            
            // check for notes, if none exist set to Nothing
            try{
                notes = str[7];
                
            }
            catch (ArrayIndexOutOfBoundsException ae){
                notes = "";
            }
            
            // set PK column to either 'Y' or Nothing
            // in the csv file, the column is 'Optional?' so the values
            // for pk need to be reversed.  I.e. Optional of N should mean
            // that PK? is Y
            if (pk.equals("N"))
                pk = "Y";
            else
                pk = "";
                
            parent.appendChild(tr);
            tr.appendChild(td1);
              td1.appendChild(doc.createTextNode(col));
            tr.appendChild(td2);
              td2.appendChild(doc.createTextNode(datatype));
            tr.appendChild(td3);
              td3.appendChild(doc.createTextNode(pk));
              td3.setAttribute("align","center");
            tr.appendChild(td4);
              td4.appendChild(doc.createTextNode(comment));
            tr.appendChild(td5);
              td5.appendChild(div1);
              div1.appendChild(doc.createTextNode(derivation));
              
              if (notes != "") {
                td5.appendChild(div2);
                div2.appendChild(doc.createTextNode(notes));
              }
            
        }
        
    }
}
