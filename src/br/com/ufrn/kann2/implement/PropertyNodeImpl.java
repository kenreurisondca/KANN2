/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.implement;

/**
 *
 * @author kenreurison
 */
public class PropertyNodeImpl extends Property {

    public PropertyNodeImpl() {
        super();
    }

    @Override
    public void createFields() {
        registerField("bias");
        registerField("oldBias");
        registerField("nivel");
        registerField("value");
        registerField("activation");
    }
    
    protected void setNet(Double v){
        setMapValue("value", v);
    }

    Double getBias() {
        return getMapValue("bias");
    }

    Double getNet() {
        return getMapValue("value");
    }

    Double getActivation() {
        return getMapValue("activation");
    }

    void setActivation(double d) {
        setMapValue("activation", d);
    }

}
