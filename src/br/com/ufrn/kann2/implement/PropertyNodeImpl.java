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
        registerField("activation");
        registerField("label");
        registerField("value");
        registerField("countInput");
        registerField("sizeInput");
    }

    @Override
    protected void cleanFields() {
//        cleanField("bias");
//        cleanField("oldBias");
//        cleanField("value");
//        cleanField("activation");
//        cleanField("countInput");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected void setValue(Double v) {
        setMapValue("value", v);
    }

    public Double getValue() {
        return getMapValue("value");
    }

    Double getBias() {
        return getMapValue("bias");
    }

    public Double getActivation() {
        return getMapValue("activation");
    }

    public void setActivation(Double d) {
        setMapValue("activation", logsig(d));
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
        Double countInput = getMapValue("countInput");
        Double sizeInput = getMapValue("sizeInput");
        setMapValue("countInput", (countInput + 1.) % sizeInput);
    }

    private Double getCountInput() {
        return getMapValue("countInput");
    }

    protected void incCountInput() {
        incMapValue("countInput");
    }

    protected void incSizeInput() {
        incMapValue("sizeInput");
    }

    private Double logsig(Double d) {
        return 1 / (1. + Math.exp(-d));
    }

    protected boolean isReady() {
        return (getMapValue("countInput") == 0.);
    }
}
