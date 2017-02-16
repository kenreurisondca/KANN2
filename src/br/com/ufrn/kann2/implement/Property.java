/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.implement;

import java.util.Hashtable;
import java.util.Map;

/**
 *
 * @author kenreurison
 */
public abstract class Property {

    private final Map<String, Double> fields;

    public Property() {
        fields = new Hashtable<>();
    }

    protected void registerField(String field) {
        fields.put(field, 0.0);
    }

    protected void removeField(String field) {
        fields.remove(field);
    }

    protected Double getValue(String field) {
        return fields.get(field);
    }

    private void setValue(String field, Double value) {
        fields.computeIfPresent(field, (k, v) -> value);
    }

    public abstract void createFields();
}
