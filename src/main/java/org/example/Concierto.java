package org.example;

public class Concierto {
    //creamos los atributos: id, artista (el id), fecha, lugar y precio
    private int id;
    private int artista;
    //fecha es java.sql.Date ya que ese es el valor que acepta sql, y no acepta el Date de java
    private java.sql.Date fecha;
    private String lugar;
    private int precio;

    //creamos un constructor vacio para poder inicializarlo sin ningun valor
    public Concierto() {
    }

    //getters y setters para todos los atributos:
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArtista() {
        return artista;
    }

    public void setArtista(int artista) {
        this.artista = artista;
    }

    public java.sql.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.sql.Date fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
