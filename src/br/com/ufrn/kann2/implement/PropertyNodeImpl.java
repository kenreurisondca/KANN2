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
        registerField("level");
        registerField("value");
        registerField("activation");
        registerField("label");
        registerField("countInput");
        registerField("sizeInput");
    }

    @Override
    protected void cleanFields() {
        cleanField("bias");
        cleanField("oldBias");
        cleanField("value");
        cleanField("activation");
        cleanField("countInput");
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

    Double getLevel() {
        return getMapValue("level");
    }

    void setLevel(double doubleValue) {
        setMapValue("level", doubleValue);
    }

    Double getOldBias() {
        return getMapValue("oldBias");
    }

    void addValue(Double v) {
        addMapValue("value", v);
        incMapValue("countInput");
        Double countInput = getMapValue("countInput");
        Double sizeInput = getMapValue("sizeInput");
        addMapValue("contInput", countInput % sizeInput);
    }

    private Double getContInput() {
        return getMapValue("contInput");
    }

    protected void incContInput() {
        incMapValue("contInput");
    }

    protected void incSizeInput() {
        incMapValue("sizeInput");
    }

    public static void main(String[] args) {
        PropertyNodeImpl p = new PropertyNodeImpl();
        p.setMapValue("sizeInput", 3.);
        p.addValue(10.);
        p.addValue(5.);
        p.addValue(8.);
        p.addValue(7.);
        p.addValue(10.);
        p.addValue(12.);
        p.addValue(5.);

    }
}
