/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthomehelp;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author hass-pc
 * Clase que contiene metodos de ayuda para leer Sensores y Habitaciones
 * 
 * Para los que preguntan porque la clase es de tipo <strong>abstractr</strong> es para que
 * no se pueda instanciar esta clase, ya que no contiene atributos.
 */
public abstract class Help {
    /**
     *  Metodo para obtener El <strong>Document</strong> para leer el xml
     * @param rute Ubicacion del archivo
     * @return Objeto de Tipo Document para operar sobre el xml
     */
    public static Document getXmlFromFile(File rute)
    {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(rute); // s_file seria la ruta del archivo
            doc.getDocumentElement().normalize();
            return doc;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Help.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    /**
     * Metodo para crea un nuevo <strong>Document</strong> para interactuar con el xml
     * @return 
     */
    public static Document newXml()
    {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            doc.setXmlStandalone(true);
            return doc;
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Help.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    /**
     * Metodo para guardar el xml
     * @param rute ruta de donde se guardara
     * @param doc Objeto que contiene la informacion del xml a guardar
     * @return <strong>True</strong> si guardo, <strong>False</strong> en caso contrario
     */
    public static boolean SaveXml(File rute, Document doc)
    {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result =  new StreamResult(rute);
            transformer.transform(source, result);
            return true;
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Help.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Help.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    /**
     * Metodo que te devuelve todos los Nodos que el nombre coincide con <strong>Name</strong>
     * @param doc Objeto para interactuar con el xml
     * @param Name Nombre del de Todas las apariciones de Nodos a buscar
     * @return 
     */
    public static NodeList getNodes(Document doc, String Name)
    {
        return doc.getElementsByTagName(Name);
    }
    /**
     * Lee la informacion de un Nodo y lo convierte a una Objeto de tipo <strong>Sensor</strong>
     * @param node Nodo que contiene la informacion
     * @param r Clase para generar aleatoriamente La frecuencia que lee el sensor al dia
     * @return Un Objeto de Clase <strong>Sensor</strong>
     */
    public static Sensor getSensorFromNode(Node node, Random r)
    {
        Element ele = (Element)node;
        String id = ele.getAttribute("id")+";";
        String ubi = ele.getElementsByTagName("Ubicacion").item(0).getTextContent();
        String tipo = ele.getElementsByTagName("Tipo").item(0).getTextContent();
        String Fecha = ele.getElementsByTagName("Fecha").item(0).getTextContent();
        Sensor s = new Sensor(Integer.parseInt(id), tipo, Fecha, r.nextInt(15));
        s.Ubicacion = ubi;
        return s;
    }
    /**
     * Lee la informacion de un Nodo y lo convierte a una Objeto de tipo <strong>Alarmar</strong>
     * @param node Nodo que contiene la informacion
     * @return Un Objeto de Clase <strong>Alarma</strong>
     */
    public static Alarma getEventoFromNode(Node node)
    {
        Element ele = (Element)node;
        String name = ele.getAttribute("nombre")+";";
        String sensor = ele.getElementsByTagName("Tipo-Sensor").item(0).getTextContent()+";";
        String valor = ele.getElementsByTagName("Valor").item(0).getTextContent()+";";
        String unidad = ele.getElementsByTagName("Unidad-de-Medida").item(0).getTextContent();
        String[] sp = valor.split(":");
        
        Alarma alarm = new Alarma(name, Float.parseFloat(sp[0]), sp[1].equals("mayor"), unidad);
        alarm.Sensor = sensor;
        return alarm;
    }
    /**
     * Metodo para almacenar El objeto <strong>Sensor</strong> en un Nodo que será hijo de <strong>Parent</strong>
     * @param doc Objeto para interactuar con el xml
     * @param Parent Nodo Padre del Sensor a guardar
     * @param s Sensor a guardar
     */
    public static void SaveSensor(Document doc, Element Parent, Sensor s)
    {
        Element sensor = doc.createElement("Sensor");
        Element Ubicacion = CreateNode(doc, "Ubicacion", s.Ubicacion);
        Element Tipo = CreateNode(doc, "Tipo", s.getTipo());
        Element Fecha = CreateNode(doc, "Fecha", s.getFechaInstalacion());
        sensor.setAttribute("id", s.getId()+"");
        sensor.appendChild(Ubicacion);
        sensor.appendChild(Tipo);
        sensor.appendChild(Fecha);
        Parent.appendChild(sensor);
    }
    /**
     * Metodo para almacenar El objeto <strong>Alarma</strong> en un Nodo que será hijo de <strong>Parent</strong>
     * @param doc Objeto para interactuar con el xml
     * @param Parent Nodo Padre del Sensor a guardar
     * @param a Alarma a guardar
     */
    public static void SaveAlarma(Document doc, Element Parent, Alarma a)
    {
        Element alarma = doc.createElement("Evento");
        Element Tipo_Sensor = CreateNode(doc, "Tipo-Sensor", a.Sensor);
        Element Valor = CreateNode(doc, "Valor", a.getValorDeActivacion()+":"+ (a.isMayorOMenor() ? "mayor" : "menor"));
        Element Unidad_de_Medida = CreateNode(doc, "Unidad-de-Medida", a.getUnidadDeMedida());
        alarma.setAttribute("nombre", a.getNombre());
        alarma.appendChild(Tipo_Sensor);
        alarma.appendChild(Valor);
        alarma.appendChild(Unidad_de_Medida);
        Parent.appendChild(alarma);
    }
    /**
     * Metodo Simple para crear un Nodo con contenido <strong>Content</strong>
     * @param doc Objeto para Interactuar con el xml
     * @param name Nombre del Nodo
     * @param Content Contenido del Nodo
     * @return El Nodo de tipo <strong>Element</strong> creado
     */
    private static Element CreateNode(Document doc, String name, String Content)
    {
        Element e = doc.createElement(name);
        e.setTextContent(Content);
        return e;
    }
}
