/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.padrao;

import java.util.Map;

/**
 *
 * @author larihmoura
 */
public class InputPatternExample extends InputPattern {

    public InputPatternExample() {
        super();
    }

    public InputPatternExample(Map<String, Double> inputs, Map<String, Double> outputs) {
        super(inputs, outputs);
    }

    @Override
    protected void generateOutput() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void generateIntermediateConclusions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
