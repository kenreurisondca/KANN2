/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.padrao;

import br.com.ufrn.kann2.util.RandomKann;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author kenreurison
 */
public abstract class Pattern {

    protected Map<String, Double> inputs;
    protected Map<String, Double> outputs;
    private Map<String, Double> intermediate;

    public Pattern() {
        inputs = new HashMap<>();
        outputs = new HashMap<>();
    }

    public Pattern(Set<String> inputs, Set<String> outputs) {
        inputs.forEach((k) -> this.inputs.put(k, 0.));
        outputs.forEach((k) -> this.outputs.put(k, 0.));
    }

    private Pattern(Map<String, Double> inputs, Map<String, Double> outputs) {
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

    public void generateInputOutput() {
        RandomKann r = RandomKann.getInstance();
        for (String s : inputs.keySet()) {
            Integer nextInt = r.nextInt(2);
            Double doubleValue = nextInt.doubleValue();
            inputs.put(s, doubleValue);
        }
        generateOutput();
    }

    public void generateInputOutputSequencial(Integer i, String nBits) {
        Integer total = new Integer(nBits);
        total *= total;
        String binaryString = String.format("%" + nBits + "s", Integer.toBinaryString(i % total)).replace(" ", "0");
        Object[] toArray = inputs.keySet().toArray();
        for (int j = 0; j < binaryString.length(); j++) {
            Double d = new Double(binaryString.charAt(j));
            inputs.put((String) toArray[j], d - 48.);
        }
        generateOutput();
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

    public Map<String, OutputError> getErrorByUnit(Map<String, Double> graph) {
        Set<String> keys = graph.keySet();
        Map<String, OutputError> inputErrorMap = new HashMap<>();
        for (String s : keys) {
            Double graphValue = graph.get(s);
            Double patternValue = intermediate.get(s);
            OutputError ieActual = inputErrorMap.get(s);
            if (Math.round(graphValue) > Math.round(patternValue)) {
                inputErrorMap.put(s, ieActual.incFP());
            } else if (Math.round(graphValue) < Math.round(patternValue)) {
                inputErrorMap.put(s, ieActual.incFN());
            }
        }
        return inputErrorMap;
    }

    protected abstract void generateOutput();

    protected abstract void generateIntermediateConclusions();

    private void selectInputInOrder(String[] inputNames) {
        for (int i = 0; i < inputNames.length; i++) {
            inputs.put(inputNames[i], 0.);
        }
    }

    private void selectOutputInOrder(String[] outputNames) {
        for (int i = 0; i < outputNames.length; i++) {
            outputs.put(outputNames[i], 0.);
        }
    }

    public Map<String, Double> getInputs() {
        return inputs;
    }

    public Map<String, Double> getOutputs() {
        return outputs;
    }

    @Override
    public String toString() {
        return "Pattern{" + "inputs=" + inputs + ", outputs=" + outputs + '}';
    }

}
