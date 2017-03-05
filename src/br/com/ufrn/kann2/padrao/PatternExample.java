/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.padrao;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kenreurison
 */
public class PatternExample extends Pattern {

    public PatternExample() {
        super();
        String labels[] = {"D", "E", "F", "G"};
        Double values[] = {1., 1., 1., 1.};
        for (int i = 0; i < labels.length; i++) {
            super.inputs.put(labels[i], values[i]);
        }
    }

    @Override
    protected void generateOutput() {

    }

    @Override
    protected void generateIntermediateConclusions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, Double> performOutput() {
        return new HashMap<>();
    }

    public Map<String, Double> getInputs() {
        return inputs;
    }

}
