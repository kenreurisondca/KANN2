/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.padrao;

import br.com.ufrn.kann2.util.RandomKann;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author kenreurison
 */
public abstract class InputPattern {

    protected Map<String, Double> inputs;
    protected Map<String, Double> outputs;
    private Map<String, Double> intermediate;

    public InputPattern() {
        inputs = new HashMap<>();
        outputs = new HashMap<>();
    }

    public InputPattern(Map<String, Double> inputs, Map<String, Double> outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public void setInput(String[] labels, Double[] values) {
        for (int i = 0; i < labels.length; i++) {
            inputs.put(labels[i], values[i]);
        }
    }

    public Map<String, Double> getInput() {
        return inputs;
    }

    public void generateRandomInput() {
        RandomKann r = RandomKann.getInstance();
        for (String s : inputs.keySet()) {
            Integer nextInt = r.nextInt(2);
            Double doubleValue = nextInt.doubleValue();
            inputs.put(s, doubleValue);
        }
    }

    public Map<String, Double> mappingInput(String[] label, Double[] values) throws Exception {
        if (values.length != label.length || label.length == 0) {
            throw new Exception("Incompatible array length");
        }
        Map<String, Double> res = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            res.put(label[i], values[i]);
        }
        return res;
    }

    public Map<String, Double> getIntermediateConcusions() {
        generateOutput();
        generateIntermediateConclusions();
        return intermediate;
    }

    public Map<String, InputError> getErrorByUnit(Map<String, Double> graph) {
        Set<String> keys = graph.keySet();
        Map<String, InputError> inputErrorMap = new HashMap<>();
        for (String s : keys) {
            Double graphValue = graph.get(s);
            Double patternValue = intermediate.get(s);
            InputError ieActual = inputErrorMap.get(s);
            if (Math.round(graphValue) > Math.round(patternValue)) {
                inputErrorMap.put(s, ieActual.incFP());
            } else if (Math.round(graphValue) < Math.round(patternValue)) {
                inputErrorMap.put(s, ieActual.incFP());
            }
        }
        return inputErrorMap;
    }

    protected abstract void generateOutput();

    protected abstract void generateIntermediateConclusions();

}
