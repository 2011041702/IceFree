package com.christiandeveloper.icefree;


class productos {

    private int id;
    private String nombre;
    private int precio;
    private  String imagen;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    String getImagen() {
        return imagen;
    }

    void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
