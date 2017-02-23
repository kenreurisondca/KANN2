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
public class PropertyGraphImpl extends Property {
    
    public PropertyGraphImpl() {
        super();
    }
    
    @Override
    public void createFields() {
        registerField("lambda");
        registerField("alpha");
        registerField("maxIter");
        registerField("maxError");
        registerField("maxLevel");
    }
    
    public Integer getMaxLevel() {
        return getMapValue("maxLevel").intValue();
    }
    
    public void setMaxLevel(double doubleValue) {
        setMapValue("maxLevel", doubleValue);
    }
    
    public void setMaxLevel(Integer max) {
        setMapValue("maxLevel", max.doubleValue());
    }
    
    void updateMaxLevel(Double level) {
        if (getMaxLevel() < level) {
            setMaxLevel(level);
        }
    }
    
}
