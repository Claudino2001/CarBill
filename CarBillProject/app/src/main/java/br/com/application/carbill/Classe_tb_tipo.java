package br.com.application.carbill;

public class Classe_tb_tipo {

    int id_tipo;
    String tipo;

    public Classe_tb_tipo(int id_tipo, String tipo) {
        this.id_tipo = id_tipo;
        this.tipo = tipo;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
