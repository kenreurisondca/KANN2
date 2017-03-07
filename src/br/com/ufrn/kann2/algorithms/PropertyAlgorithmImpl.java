/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.algorithms;

import br.com.ufrn.kann2.implement.Property;

/**
 *
 * @author kenreurison
 */
public final class PropertyAlgorithmImpl extends Property {

    public PropertyAlgorithmImpl() {
        super();
    }

    @Override
    protected void createFields() {
        registerField("eta");
        registerField("maxIter");
        registerField("maxError");
        setMaxIter(1000.);
        setEta(0.5);
        setMaxError(0.01);
    }

    public Double getEta() {
        return getField("eta");
    }

    void setEta(Double _eta) {
        setMapValue("eta", _eta);
    }

    private void incMaxIter() {
        incMapValue("maxIter");
    }

    public Double getMaxIter() {
        return getField("maxIter");
    }

    public void setMaxIter(Double d) {
        setMapValue("maxIter", d);
    }

    public Double getMaxError() {
        return getField("maxError");
    }

    public void setMaxError(Double d) {
        setMapValue("maxError", d);
    }
}
