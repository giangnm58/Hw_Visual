package org.tensorflow.demo.Adaptadores;

/**
 * Created by jj on 14/07/19.
 */

public class TitulosA {
    private String tituloo;
    private int imagenn;
    private int fondooo;




    public TitulosA(String tituloo, int imagenn, int fondooo) {
        this.tituloo = tituloo;
        this.imagenn = imagenn;
        this.imagenn=fondooo;
    }


    public int getFondooo() {
        return fondooo;
    }

    public void setFondooo(int fondooo) {
        this.fondooo = fondooo;
    }

    public String getTituloo() {
        return tituloo;
    }

    public void setTituloo(String tituloo) {
        this.tituloo = tituloo;
    }

    public int getImagenn() {
        return imagenn;
    }

    public void setImagenn(int imagenn) {
        this.imagenn = imagenn;
    }
}
