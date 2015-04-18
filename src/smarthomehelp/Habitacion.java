/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthomehelp;

import java.util.ArrayList;

/**
 *
 * @author hass-pc
 */
public class Habitacion {
    private String Nombre;
    private String Localizacion;
    private float Area;
    ArrayList<Sensor> Sensores;

    public Habitacion(String Nombre, String Localizacion, float Area) {
        this.Nombre = Nombre;
        this.Localizacion = Localizacion;
        this.Area = Area;
        Sensores = new ArrayList<>();
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getLocalizacion() {
        return Localizacion;
    }

    public void setLocalizacion(String Localizacion) {
        this.Localizacion = Localizacion;
    }

    public float getArea() {
        return Area;
    }

    public void setArea(float Area) {
        this.Area = Area;
    }

    public ArrayList<Sensor> getSensores() {
        return Sensores;
    }   
    
}
