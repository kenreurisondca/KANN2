/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.padrao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kenreurison
 */
public class PatternExample_1 extends Pattern {

    public PatternExample_1() {
        super();
        this.selectInputInOrder();
    }

    @Override
    public void selectInputInOrder() {
        inputs.put("D", 0.);
        inputs.put("E", 0.);
        inputs.put("F", 0.);
        inputs.put("G", 0.);
        
    }

    @Override
    protected void generateOutput() {

    }

    @Override
    protected void generateIntermediateConclusions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void selectOutputInOrder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, Double> performOutput() {
        return new HashMap<String, Double>();
    }

    public Map<String, Double> getInputs() {
        return inputs;
    }

}
