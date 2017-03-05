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
        setEta(0.7);
    }
    
    public Double getEta() {
        return getField("eta");
    }
    
    public void setEta(Double _eta) {
        setMapValue("eta", _eta);
    }
}
