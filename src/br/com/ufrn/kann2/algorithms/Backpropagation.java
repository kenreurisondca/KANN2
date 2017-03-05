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

    public Backpropagation(Graph graph) {
        super(graph);
    }

    public Backpropagation() {
        super();
    }

    @Override
    public void forwardRec() {
        pattern.generateRandomInput();
        Map<String, Double> input = pattern.getInput();
        graph.getInputs().forEach((k, v) -> v.setValue(input.get(k)));
        for (Node n : graph.getInputs().values()) {
            n.propagateRec();
        }
    }

    public void propagateRec(Node n) {
        PropertyNodeImpl prop = (PropertyNodeImpl) n.getpNode();
        if (n.getEdgesIn().isEmpty()) {
            n.getEdgesOut().forEach((e) -> e.getOut().addValue(prop.getValue() * e.getWeigth()));
            n.getEdgesOut().forEach((e) -> e.getOut().propagateRec());
        } else if (n.isReady()) {
            prop.setActivation(n.getValue() + n.getBias());
            n.getEdgesOut().forEach((e) -> e.getOut().addValue(prop.getActivation() * e.getWeigth()));
            n.getEdgesOut().forEach((e) -> e.getOut().propagateRec());
        }
    }

    public void setPattern(PatternExample patternExample) {
        pattern = patternExample;
    }

    @Override
    public void forwardIter() {
        pattern.generateRandomInput();
        graph.getInputs().forEach((k, v) -> v.setValue(pattern.getInput().get(k)));
        List<Node> inputNodeList = new ArrayList<>(graph.getInputs().values());
        while (!inputNodeList.isEmpty()) {
            Node nodeRemove = inputNodeList.remove(0);
            ArrayList<Edge> edgesOut = nodeRemove.getEdgesOut();
            nodeRemove.propagateIter();
            for (Edge e : edgesOut) {
                if (!inputNodeList.contains(e.getOut())) {
                    inputNodeList.add(e.getOut());
                }
            }
        }
    }

    @Override
    public void backwardRec() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void backwardIter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
