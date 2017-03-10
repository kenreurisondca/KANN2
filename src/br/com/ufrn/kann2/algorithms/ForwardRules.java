/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.algorithms;

import br.com.ufrn.kann2.implement.Edge;
import br.com.ufrn.kann2.implement.Node;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kenreurison
 */
public final class ForwardRules extends Algorithm {

    @Override
    public void forwardRec() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void forwardIter() {
        cleanAllValues();
        for (Node n : graph.getInputs().values()) {
            n.setValue(pattern.getInput().get(n.getLabel()));
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

    private void cleanAllValues() {
        graph.getNodes().values().forEach((n) -> n.setValue(0.));

    }

    @Override
    public Double backwardRec() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Double backwardIter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void train() {
        forwardIter();
    }

}
