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

}
