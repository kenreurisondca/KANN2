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
public final class PatternExample extends Pattern {

    public PatternExample() {
        super();
        String labels[] = {"D", "E", "F", "G"};
        for (int i = 0; i < labels.length; i++) {
            inputs.put(labels[i], 0.);
        }
        this.generateInput();
        this.generateOutput();
    }

    @Override
    protected void generateOutput() {
        if (inputs.get("D") == 1. && inputs.get("F") == 1) {
            outputs.put("A", 1.);
        } else {
            outputs.put("A", 0.);
        }
    }

    @Override
    protected void generateIntermediateConclusions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Map<String, Double> getInputs() {
        return inputs;
    }

}
