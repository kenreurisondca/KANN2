/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.padrao;

import br.com.ufrn.kann2.implement.Node;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.scene.AccessibleAttribute;

/**
 *
 * @author kenreurison
 */
public abstract class InputPattern {

    private Map<String, Double> inputs;
    private Map<String, Double> outputs;
    private Map<String, Double> intermediate;

    public InputPattern() {
        inputs = new HashMap<>();
        outputs = new HashMap<>();
    }

    public InputPattern(Map<String, Double> inputs, Map<String, Double> outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    private Map<String, Double> mappingInput(String[] label, Double[] values) throws Exception {
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

    public Map<String, inputError> getErrorByUnit(Map<String, Double> graph) {
        Set<String> keys = graph.keySet();
        for (String s : keys) {
            Double graphValue = graph.get(s);
            Double patternValue = intermediate.get(s);
            if (Math.round(graphValue) > Math.round(patternValue)) {

            }
        }
    }

    protected abstract void generateOutput();

    protected abstract void generateIntermediateConclusions();

}
