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

    protected void removeField(String field) {
        fields.remove(field);
    }

    protected Double getMapValue(String field) {
        return fields.get(field);
    }

    protected void addMapValue(String field, Double value) {
        fields.computeIfPresent(field, (k, v) -> value + v);
    }

    protected void setMapValue(String field, Double value) {
        fields.computeIfPresent(field, (k, v) -> value);
    }

    protected abstract void createFields();
    
    public static void main(String[] args) {
        Property p = new Property() {
            @Override
            protected void createFields() {
                registerField("Kennedy");
            }
        };
        p.addMapValue("Kennedy", 30.);
        p.addMapValue("Kennedy", 45.);
    }
}
