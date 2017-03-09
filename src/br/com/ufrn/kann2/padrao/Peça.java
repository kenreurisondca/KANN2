/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.padrao;

/**
 *
 * @author pesquisador
 */
public class Peça {
    
    private String rotulo;
    private boolean cor;
    private String tag;

    public Peça(String rotulo, boolean cor) {
        this.rotulo = rotulo;
        this.cor = cor;
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public boolean isCor() {
        return cor;
    }

    public void setCor(boolean cor) {
        this.cor = cor;
    }

    @Override
    public String toString() {
        return "Pe\u00e7a{" + "rotulo=" + rotulo + ", cor=" + cor + '}';
    }

    void setTag(Integer linha, Integer coluna) {
        
    }
    
    
    
}
