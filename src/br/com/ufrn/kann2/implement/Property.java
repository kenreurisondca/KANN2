/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.implement;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kenreurison
 */
public abstract class Property {
    
    private final Map<String, Double> fields = new HashMap<>();
    
    public Property() {
        createFields();
    }
    
    protected void registerField(String field) {
        fields.put(field, 0.0);
    }
    
    private void cleanField(String field) {
        fields.put(field, 0.0);
    }
    
    private void removeField(String field) {
        fields.remove(field);
    }
    
    protected Double getMapValue(String field) {
        return fields.get(field);
    }
    
    protected void addMapValue(String field, Double value) {
        fields.computeIfPresent(field, (k, v) -> value + v);
    }
    
    protected void incMapValue(String field) {
        fields.computeIfPresent(field, (k, v) -> v + 1.);
    }
    
    protected void setMapValue(String field, Double value) {
        fields.computeIfPresent(field, (k, v) -> value);
    }
    
    @Override
    public String toString() {
        return "Property{" + "fields=" + fields.toString() + '}';
    }
    
    protected abstract void createFields();
    
    protected void cleanFields() {
        fields.forEach((k, v) -> v = 0.);
    }
}
