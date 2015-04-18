/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthomehelp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author hass-pc
 */
public class Lista extends ArrayList<Habitacion>{
    
    public Lista() {
        super();
    }
    
    public boolean CargarInformacion()
    {
        BufferedReader reader = null;
        try {
            //Comienzo
            reader = new BufferedReader(new FileReader(new File(ArchivoHabitaciones)));
            String line;
            while ((line = reader.readLine())!=null) {
                String[] data = line.split(";");
                this.add(new Habitacion(data[0], data[1], Float.parseFloat(data[2])));
            }
            //Una vez cargadas las habitaciones, se procede a cargar los sensores y eventos (Alarmas)
            Document doc = Help.getXmlFromFile(new File(ArchivoSensores));
            if (doc != null)
            {
                ArrayList<Sensor> sensores = new ArrayList<>();
                NodeList XmlSensores = Help.getNodes(doc, "Sensor");
                Random r = new Random();
                for (int i = 0; i < XmlSensores.getLength(); i++) {
                    Node n = XmlSensores.item(i);
                    sensores.add(Help.getSensorFromNode(n, r));
                }
                //Ya se tienen los sensores, ahora las alarmas
                ArrayList<Alarma> Alarmas = new ArrayList<>();
                NodeList XmlEventos = Help.getNodes(doc, "Evento");
                for (int i = 0; i < XmlEventos.getLength(); i++) {
                    Node n = XmlEventos.item(i);
                    Alarmas.add(Help.getEventoFromNode(n));
                }
                //Se relacionan Las Alarmas con los eventos.
                for (Sensor s : sensores)
                {
                    for (Alarma a : Alarmas)
                    {
                        if (s.getTipo().equals(a.Sensor))
                            s.getAlarmas().add(a);
                    }
                }
                //Se relacionan los sensores con las habitaciones
                for (Habitacion hab : this)
                {
                    for (Sensor s : sensores)
                    {
                        if (hab.getNombre().equals(s.Ubicacion))
                            hab.getSensores().add(s);
                    }
                }
                return true;
            }
            return false;
            //Final
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lista.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Lista.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException ex) {
                Logger.getLogger(Lista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    public boolean GuardarInformacion()
    {
        PrintWriter writer = null;
        try {
            //Guardar Habitaciones
            writer = new PrintWriter(new FileWriter(new File(ArchivoHabitaciones)));
            for (int i = 0; i < this.size(); i++) {
                Habitacion hab = this.get(i);
                String data = hab.getNombre()+";"+hab.getLocalizacion()+";"+hab.getArea();
                writer.println(data);
            }
            Document doc = Help.newXml();
            if (doc!=null)
            {
                Element rootElement = doc.createElement("Sensores-y-Eventos");
                doc.appendChild(rootElement);
                Element sensores = doc.createElement("Sensores");
                //Se Agregan Sensores
                ArrayList<Sensor> ListSensores = new ArrayList<>();
                for (Habitacion hab : this)
                {
                    for (Sensor s_hab : hab.getSensores()) 
                    {
                        boolean YaEstaIncluido = false;
                        for (Sensor s : ListSensores)
                        {
                            if (s.getTipo().equals(s.getTipo()))
                            {
                                YaEstaIncluido = true;
                                break;
                            }
                        }
                        if (!YaEstaIncluido)
                            ListSensores.add(s_hab);
                    }
                }
                //Se obtienen todos los sensores
                for (Sensor s: ListSensores)
                {
                    Help.SaveSensor(doc, sensores, s); //Se guardan
                }
                //Final de Agregar Sensores
                rootElement.appendChild(sensores);
                Element eventos = doc.createElement("Eventos");
                //Se agregan Eventos
                ArrayList<Alarma> alarmas = new ArrayList<>();
                for (Sensor s : ListSensores)
                {
                    for (Alarma e_sen : s.getAlarmas())
                    {
                        boolean YaEstaIncluido = false;
                        for (Alarma e : alarmas)
                        {
                            if (e_sen.getNombre().equals(e.getNombre()))
                            {
                                YaEstaIncluido = true;
                                break;
                            }
                        }
                        if (!YaEstaIncluido)
                            alarmas.add(e_sen);
                    }
                }
                //Se obtienen todos los eventos (alarmas)
                for (Alarma a : alarmas)
                {
                    Help.SaveAlarma(doc, eventos, a);
                }
                //Final Agregar Eventos
                rootElement.appendChild(eventos);
                if (Help.SaveXml(new File(ArchivoSensores), doc))
                    return true;
                else
                    return false;
            }
            return false;
        } catch (IOException ex) {
            Logger.getLogger(Lista.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (writer != null)
                writer.close();
        }
        return false;
    }
    
    private static final String ArchivoHabitaciones = "The Walking Dead.txt";
    private static final String ArchivoSensores = "The Walking Dead_sensores.xml";
}
