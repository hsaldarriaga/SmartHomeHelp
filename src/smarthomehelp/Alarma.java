/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthomehelp;

/**
 *
 * @author hass-pc
 */
public class Alarma {
    private String Nombre;
    private float ValorDeActivacion;
    private String UnidadDeMedida;
    /**
     * Si es mayor ValorDeActivacion : true
     * Si es menor ValorDeActivacion : false
     */
    private boolean MayorOMenor;
    public String Sensor;
    public Alarma(String name, float ValorDeActivacion, boolean MayorOMenor, String UnidadDeMedida) {
        this.Nombre = name;
        this.ValorDeActivacion = ValorDeActivacion;
        this.MayorOMenor = MayorOMenor;
        this.UnidadDeMedida = UnidadDeMedida;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public float getValorDeActivacion() {
        return ValorDeActivacion;
    }

    public void setValorDeActivacion(float ValorDeActivacion) {
        this.ValorDeActivacion = ValorDeActivacion;
    }
    /**
     * @return <strong>True</strong> Si es Mayor. <strong>False</strong> Si es Menor
     */
    public boolean isMayorOMenor() {
        return MayorOMenor;
    }

    public void setMayorOMenor(boolean MayorOMenor) {
        this.MayorOMenor = MayorOMenor;
    }

    public String getUnidadDeMedida() {
        return UnidadDeMedida;
    }

    public void setUnidadDeMedida(String UnidadDeMedida) {
        this.UnidadDeMedida = UnidadDeMedida;
    }
    
    
}
