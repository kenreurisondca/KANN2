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
import br.com.ufrn.kann2.padrao.OutputError;
import br.com.ufrn.kann2.padrao.Pattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kenreurison
 */
public class Backpropagation extends Algorithm {

    private List<Node> hiddenNodes = new ArrayList<>();
    private List<Node> outputNodes = new ArrayList<>();
    private Double trainningError;

    public Backpropagation(Graph graph) {
        super(graph);
    }

    public Backpropagation() {
        super();
    }

    @Override
    public void forwardRec() {
        pattern.generateInputOutput();
        Map<String, Double> input = pattern.getInputs();
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

    @Override
    public void forwardIter() {
        cleanAllValues();
        for (Node n : graph.getInputs().values()) {
            n.setValue(pattern.getInputs().get(n.getLabel()));
        }
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
    public Double backwardRec() {
        cleanAllValues();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Double backwardIter() {
        List<Node> output = this.getOutputNodes();
        Double y, d, delta, bpError;
        Double erro = 0.;
        this.op = new OutputError(0., 0.);
        for (Node nodeOut : output) {
            String label = nodeOut.getLabel();
            d = pattern.getOutputs().get(label);
            y = nodeOut.getActivation();
            delta = y * (1 - y) * (d - y);
            nodeOut.getpNode().updateField("delta", delta);
            updateOutputNode(nodeOut);
            erro += (d - y) * (d - y);
        }

        hiddenNodes = this.getOrderedHiddenNodes();
        for (Node nodeHidden : hiddenNodes) {
            y = nodeHidden.getActivation();
            delta = y * (1 - y);
            bpError = 0.;
            for (Edge edgeOut : nodeHidden.getEdgesOut()) {
                bpError += edgeOut.getOldWeigth() * edgeOut.getNodeOut().getpNode().getField("delta");

            }
            delta *= bpError;
            nodeHidden.getpNode().updateField("delta", delta);
            updateHiddenNode(nodeHidden);
        }
        return erro;
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
        for (Edge in : nodeHidden.getEdgesIn()) {
            Double input = in.getInValue();
            in.setWeigth(in.getOldWeigth() + eta * delta * input);
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

    private List<Node> getOrderedHiddenNodes() {
        this.getHiddenNodes();
        Collections.sort(hiddenNodes, (Node o1, Node o2) -> {
            if (o1.getLevel() > o2.getLevel()) {
                return -1;
            } else if (o1.getLevel() < o2.getLevel()) {
                return 1;
            } else {
                return 0;
            }
        });
        return hiddenNodes;
    }

    private void cleanAllValues() {
        graph.getNodes().values().forEach((n) -> n.setValue(0.));

    }

    private Double getMaxIter() {
        return ((PropertyAlgorithmImpl) p).getMaxIter();
    }

    private Double getMaxError() {
        return ((PropertyAlgorithmImpl) p).getMaxError();
    }

    private Double getSampleSize() {
        return ((PropertyAlgorithmImpl) p).getSampleSize();
    }

    public void setMaxError(Double d) {
        ((PropertyAlgorithmImpl) p).setMaxError(d);
    }

    @Override
    public void train() {
        Double totalError = 0.;
        Double erroAntigo = 0.;
        Double epocas = 0.;
        Double N = this.getSampleSize();
        trainningError = this.getMaxError();
        epocas = 0.;
        do {
            epocas++;
            totalError = 0.;
            for (int i = 0; i < N; i++) {
                pattern.generateInputOutput();
                forwardIter();
                totalError += backwardIter();
            }
            erroAntigo = trainningError;
            trainningError = (0.5 * totalError) / N;
        } while (epocas < this.getMaxIter() && trainningError > this.getMaxError()
                && Math.abs(erroAntigo - trainningError) > this.getMaxError() / 10);
    }

    public Double getTrainningError() {
        return this.trainningError;
    }

    @Override
    public void setPattern(Pattern p) {
        pattern = p;
    }

}
