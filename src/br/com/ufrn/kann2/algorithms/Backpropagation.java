/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.algorithms;

import br.com.ufrn.kann2.implement.Edge;
import br.com.ufrn.kann2.implement.Graph;
import br.com.ufrn.kann2.implement.Node;
import br.com.ufrn.kann2.implement.PropertyNodeImpl;
import br.com.ufrn.kann2.padrao.PatternExample;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kenreurison
 */
public class Backpropagation extends Algorithm {

    private List<Node> hiddenNodes = new ArrayList<>();
    private List<Node> outputNodes = new ArrayList<>();

    public Backpropagation(Graph graph) {
        super(graph);
    }

    public Backpropagation() {
        super();
    }

    @Override
    public void forwardRec() {
        pattern.generateInput();
        Map<String, Double> input = pattern.getInput();
        graph.getInputs().forEach((k, v) -> v.setValue(input.get(k)));
        for (Node n : graph.getInputs().values()) {
            n.propagateRec();
        }
    }

    public void propagateRec(Node n) {
        PropertyNodeImpl prop = (PropertyNodeImpl) n.getpNode();
        if (n.getEdgesIn().isEmpty()) {
            n.getEdgesOut().forEach((e) -> e.getNodeOut().addValue(prop.getValue() * e.getWeigth()));
            n.getEdgesOut().forEach((e) -> e.getNodeOut().propagateRec());
        } else if (n.isReady()) {
            prop.setActivation(n.getValue() + n.getBias());
            n.getEdgesOut().forEach((e) -> e.getNodeOut().addValue(prop.getActivation() * e.getWeigth()));
            n.getEdgesOut().forEach((e) -> e.getNodeOut().propagateRec());
        }
    }

    public void setPattern(PatternExample patternExample) {
        pattern = patternExample;
        pattern.generateInput();
    }

    @Override
    public void forwardIter() {
        graph.getInputs().forEach((k, v) -> v.setValue(pattern.getInput().get(k)));
        List<Node> inputNodeList = new ArrayList<>(graph.getInputs().values());
        while (!inputNodeList.isEmpty()) {
            Node nodeRemove = inputNodeList.remove(0);
            ArrayList<Edge> edgesOut = nodeRemove.getEdgesOut();
            nodeRemove.propagateIter();
            for (Edge e : edgesOut) {
                if (!inputNodeList.contains(e.getNodeOut())) {
                    inputNodeList.add(e.getNodeOut());
                }
            }
        }
    }

    @Override
    public void backwardRec() {

    }

    @Override
    public void backwardIter() {
        List<Node> output = this.getOutputNodes();
        Double y, d, delta, bpError;

        for (Node nodeOut : output) {
            String label = nodeOut.getLabel();
            d = pattern.getOutputs().get(label);
            y = nodeOut.getActivation();
            delta = y * (1 - y) * (d - y);
            nodeOut.getpNode().updateField("delta", delta);
            updateOutputNode(nodeOut);
        }

        List<Node> hiddenNodes = this.getHiddenNodes();
        for (Node nodeHidden : hiddenNodes) {
            y = nodeHidden.getActivation();
            delta = y * (1 - y);
            bpError = 0.;
            for (Edge edgeOut : nodeHidden.getEdgesOut()) {
                bpError += edgeOut.getWeigth() * edgeOut.getNodeOut().getpNode().getField("delta");

            }
            delta *= bpError;
            nodeHidden.getpNode().updateField("delta", delta);
            updateHiddenNode(nodeHidden);
        }
    }

    private void updateOutputNode(Node nodeOut) {
        Double delta = nodeOut.getpNode().getField("delta");
        Double eta = ((PropertyAlgorithmImpl) p).getEta();
        for (Edge in : nodeOut.getEdgesIn()) {
            Double input = in.getNodeIn().getActivation();
            in.addWeigth(eta * delta * input);
        }
        nodeOut.addBias(eta * delta);
    }

    private void updateHiddenNode(Node nodeHidden) {
        Double delta = nodeHidden.getpNode().getField("delta");
        Double eta = ((PropertyAlgorithmImpl) p).getEta();
        for (Edge out : nodeHidden.getEdgesOut()) {
            Double input = out.getNodeIn().getValue();
            out.setWeigth(out.getOldWeigth() + eta * delta * input);
        }
        nodeHidden.setBias(nodeHidden.getOldBias() + eta * delta);
    }

    private List<Node> getOutputNodes() {
        if (outputNodes.isEmpty()) {
            outputNodes = new ArrayList<>(graph.getOutput().values());
        }
        return outputNodes;
    }

    private List<Node> getHiddenNodes() {
        if (hiddenNodes.isEmpty()) {
            for (Node n : graph.getNodes().values()) {
                if (!n.getEdgesOut().isEmpty() && (!n.getEdgesIn().isEmpty())) {
                    hiddenNodes.add(n);
                }
            }
        }
        return hiddenNodes;
    }

    @Override
    public void train() {
        pattern.generateInput();
        forwardIter();
        backwardIter();
    }

}
