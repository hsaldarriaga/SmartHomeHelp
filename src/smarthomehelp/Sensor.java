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
public class Sensor {
    private int Id;
    private String Tipo;
    private String FechaInstalacion;
    private boolean Estado;
    private int FrecuenciaPorDia;
    private float MinRango;
    private float MaxRango;
    private ArrayList<Alarma> alarmas;
    public String Ubicacion;
    
    public Sensor(int Id, String Tipo, String FechaInstalacion, int FrecuenciaPorDia) {
        this.Id = Id;
        this.Tipo = Tipo;
        this.FechaInstalacion = FechaInstalacion;
        this.FrecuenciaPorDia = FrecuenciaPorDia;
        alarmas = new ArrayList<>();
    }
    
    public void CalcularRango()
    {
        
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getFechaInstalacion() {
        return FechaInstalacion;
    }

    public void setFechaInstalacion(String FechaInstalacion) {
        this.FechaInstalacion = FechaInstalacion;
    }

    public boolean isEstado() {
        return Estado;
    }

    public void setEstado(boolean Estado) {
        this.Estado = Estado;
    }

    public int getFrecuenciaPorDia() {
        return FrecuenciaPorDia;
    }

    public void setFrecuenciaPorDia(int FrecuenciaPorDia) {
        this.FrecuenciaPorDia = FrecuenciaPorDia;
    }

    public float getMinRango() {
        return MinRango;
    }

    public void setMinRango(float MinRango) {
        this.MinRango = MinRango;
    }

    public float getMaxRango() {
        return MaxRango;
    }

    public void setMaxRango(float MaxRango) {
        this.MaxRango = MaxRango;
    }

    public ArrayList<Alarma> getAlarmas() {
        return alarmas;
    }
}
