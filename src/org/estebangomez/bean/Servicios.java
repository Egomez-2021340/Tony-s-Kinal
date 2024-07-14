package org.estebangomez.bean;

import java.sql.Time;
import java.util.Date;


public class Servicios {
    private int codigoServicio;
    private Date fechaServicio;
    private String tipoServicio;
    private Time horaServicio;
    private String lugarDeServicio;
    private String telefonoContacto;
    private int codigoEmpresa;

    public Servicios() {
    }

    public int getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(int codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public Date getFechaServicio() {
        return fechaServicio;
    }

    public void setFechaServicio(Date fechaServicio) {
        this.fechaServicio = fechaServicio;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Time getHoraServicio() {
        return horaServicio;
    }

    public void setHoraServicio(Time horaServicio) {
        this.horaServicio = horaServicio;
    }

    public String getLugarDeServicio() {
        return lugarDeServicio;
    }

    public void setLugarDeServicio(String lugarDeServicio) {
        this.lugarDeServicio = lugarDeServicio;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

}