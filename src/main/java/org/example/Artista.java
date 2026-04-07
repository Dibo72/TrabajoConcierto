package org.example;

public class Artista {
    //creamos los atributos: id, nombre, paisOrigen
    private int id;
    private String nombre;
    private String generoMusical;
    private String paisOrigen;

    //hacemos un constructor vacio para poder crear un abjeto sin ningun atributo
    public Artista() {
    }

    //getters y setters para todos los metodos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGeneroMusical() {
        return generoMusical;
    }

    public void setGeneroMusical(String generoMusical) {
        this.generoMusical = generoMusical;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }
}
