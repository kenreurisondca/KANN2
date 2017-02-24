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
public class PropertyEdgeImpl extends Property {

    public PropertyEdgeImpl() {
        super();
    }

    @Override
    protected final void createFields() {
        registerField("weigth");
        registerField("oldWeigth");
    }

    public Double getWeigth() {
        return getMapValue("weigth");
    }
    
    void setWeigth(Double w) {
        Double oldWeigth = getMapValue("weigth");
        setMapValue("oldWeigth", oldWeigth);
        setMapValue("weigth", w);
    }

    void setBias(Double b) {
        Double oldWeigth = getMapValue("bias");
        setMapValue("oldBias", oldWeigth);
        setMapValue("weigth", b);
    }

    Double getOldWeigth() {
        return getMapValue("oldWeigth");
    }

}
