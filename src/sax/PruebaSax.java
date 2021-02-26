package sax;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class PruebaSax {
    
    public static void main(String[] args) {
        try {
            GestionContenido gestor = new GestionContenido();
            GestionErrores error = new GestionErrores();
            
            XMLReader procesadorXML = XMLReaderFactory.createXMLReader();
            
            //Para usar nuestro gestor de contenido
            procesadorXML.setContentHandler(gestor);//Indico que use mi clase para leer el XML
            procesadorXML.setErrorHandler(error);//Indico que use la clase de GestionErrores
            procesadorXML.setFeature("http://xml.org/sax/features/validation", true);
            
            InputSource fichero = new InputSource("karasuno.xml");
            procesadorXML.parse(fichero);//Lee el fichero
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

//DefaultHandler - GestorContenido
class GestionContenido extends DefaultHandler {
    public GestionContenido(){
        super();//Todo lo que tiene el padre
    }
    //Empezar el documento
    public void startDocument() throws SAXException{
        System.out.println("-> Comienzo del Documento XML");
    }
    //Cerrar el documento
    public void endDocument() throws SAXException{
        System.out.println("-> Final del Documento XML");
    }
    public void startElement(String nameSpaceURI, String localName, String qName, Attributes atts){
        System.out.println("\tPrincipio de elemento: " + localName);//\t es para meter una tabulación
        for (int i = 0; i < atts.getLength(); i++) {
            System.out.println("\t\tAtributo: " + atts.getLocalName(i));//Lee el nombre del atributo
            System.out.println("\t\tAtributo: " + atts.getValue(i));//Lee el valor del atributo
        }
    }
    public void endElement(String nameSpaceURI, String localName, String qName){
        System.out.println("\tFinal de elemento: " + localName);
    }
    /*
    public void characters(char[] ch, int start, int length) throws SAXException{
        String palabra = new String(ch, start, length); //'h', 'o', 'l', 'a' -> 'hola'
        //String linea = "";
        //for(int i = start; ){
    
        System.out.println("\tCaracteres: " + palabra);
    }
    */
    public void characters(char[] ch, int start, int length) throws SAXException{
        String palabra = new String(ch, start, length);
        palabra = palabra.replace("[\t\n]", "");
        palabra = palabra.trim();
        if(!palabra.isEmpty()){
            System.out.println("\tCaracteres: " + palabra);
        }
    }
    public void ignorableWhiteSpace(char[] ch, int start, int length) throws SAXException{
        System.out.println("Caracteres eliminados en blanco: " + length);
    }
}
//ErrorHandler
class GestionErrores implements ErrorHandler{
    
    public String getParseExceptionInfo(SAXParseException spe){
        String systemID = spe.getSystemId();
        if (systemID == null){
            systemID = "null";
        }
        String info = "URI= " + systemID + ". Line= " + spe.getLineNumber() + " : " + spe.getMessage();
        return info;
    }
    
    @Override
    public void warning(SAXParseException saxpe) throws SAXException {
        System.err.println("Error: " + getParseExceptionInfo(saxpe)); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void error(SAXParseException saxpe) throws SAXException {
        System.err.println("Error: " + getParseExceptionInfo(saxpe)); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fatalError(SAXParseException saxpe) throws SAXException {
        System.err.println("Error: " + getParseExceptionInfo(saxpe)); //To change body of generated methods, choose Tools | Templates.
    }
     
}