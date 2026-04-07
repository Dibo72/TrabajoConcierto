package org.example;

public class Entrada {
    //creamos los atributos: id, concierto (el id), comprador, cantidad y fechaCompra
    private int id;
    private int concierto;
    private String comprador;
    private int cantidad;
    //fechacompra es java.sql.Date ya que ese es el valor que acepta sql, y no acepta el Date de java
    private java.sql.Date fechaCompra;

    //Creamos un constructor vacio para poder crear un objeto sin tener que asignarle atributos
    public Entrada() {
    }

    //metodos getters y setters para todos los atributos:
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConcierto() {
        return concierto;
    }

    public void setConcierto(int concierto) {
        this.concierto = concierto;
    }

    public String getComprador() {
        return comprador;
    }

    public void setComprador(String comprador) {
        this.comprador = comprador;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public java.sql.Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(java.sql.Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
}
