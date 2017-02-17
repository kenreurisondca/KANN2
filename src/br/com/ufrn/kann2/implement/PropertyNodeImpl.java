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
        registerField("label");
    }
    
    protected void setNet(Double v) {
        setMapValue("value", v);
    }
    
    Double getNet() {
        return getMapValue("value");
    }
    
    Double getBias() {
        return getMapValue("bias");
    }
    
    Double getActivation() {
        return getMapValue("activation");
    }
    
    void setActivation(Double d) {
        setMapValue("activation", d);
    }
    
    void setBias(Double b) {
        Double oldBias = getMapValue("bias");
        setMapValue("oldBias", oldBias);
        setMapValue("bias", b);
    }
    
}
