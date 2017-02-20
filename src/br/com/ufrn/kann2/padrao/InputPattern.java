/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.padrao;

import br.com.ufrn.kann2.implement.Node;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kenreurison
 */
public abstract class InputPattern {

    private Map<String, Node> inputs = new HashMap<>();
    private Map<String, Node> outputs = new HashMap<>();

    public InputPattern(Map<String, Node> inputs, Map<String, Node> outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public InputPattern(List<Node> input, List<Node> output) {
        for (Node n : input) {
            inputs.put(n.getLabel(), n);
        }
        for (Node n : input) {
            outputs.put(n.getLabel(), n);
        }
    }

    public void mappingInput(String[] label, Double[] values) throws Exception {
        if (values.length != label.length || label.length == 0) {
            throw new Exception("Incompatible array length");
        }
        for (int i = 0; i < label.length; i++) {
            String name = label[i];
            Double valor = values[i];
            inputs.get(name).activation(valor);
        }
    }
}
