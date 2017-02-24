/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.implement;

import br.com.ufrn.kann2.observer.Subject;
import java.util.ArrayList;

/**
 *
 * @author kenreurison
 */
public class Node extends Subject {

    private ArrayList<Edge> edgesIn = new ArrayList<>();
    private ArrayList<Edge> edgesOut = new ArrayList<>();
    private String label;
    Property pNode = new PropertyNodeImpl();

    public Node(String label) {
        this.label = label;
    }

    public void net() {
        Double sum = ((PropertyNodeImpl) pNode).getBias();
        for (Edge e : edgesIn) {
            sum += e.getWeigth();
        }
        ((PropertyNodeImpl) pNode).setNet(sum);
        notifyObserver();
    }

    public Double getNet() {
        return ((PropertyNodeImpl) pNode).getNet();
    }

    public void activation() {
        ((PropertyNodeImpl) pNode).setActivation(logsig(getNet()));
        notifyObserver();
    }

    public void activation(Double d) {
        ((PropertyNodeImpl) pNode).setActivation(d);
        notifyObserver();
    }

    public Double getActivation() {
        return ((PropertyNodeImpl) pNode).getActivation();
    }

    private Double logsig(Double net) {
        return 1. / (1. + Math.exp(-net));
    }

    void setBias(Double b) {
        ((PropertyNodeImpl) pNode).setBias(b);
    }

    Double getBias() {
        return ((PropertyNodeImpl) pNode).getBias();
    }

    void addEdgeIn(Edge e) {
        if (!edgesIn.contains(e)) {
            edgesIn.add(e);
        }
    }

    void addEdgeOut(Edge e) {
        if (!edgesOut.contains(e)) {
            edgesOut.add(e);
        }
    }

    public String getLabel() {
        return label;
    }

    public ArrayList<Edge> getEdgesIn() {
        return edgesIn;
    }

    public void setEdgesIn(ArrayList<Edge> edgesIn) {
        this.edgesIn = edgesIn;
    }

    public ArrayList<Edge> getEdgesOut() {
        return edgesOut;
    }

    public void setEdgesOut(ArrayList<Edge> edgesOut) {
        this.edgesOut = edgesOut;
    }

    void setLevel(Integer i) {
        ((PropertyNodeImpl) pNode).setLevel(i.doubleValue());
    }

    void setLevel(Double i) {
        ((PropertyNodeImpl) pNode).setLevel(i);
    }

    public Double getLevel() {
        return ((PropertyNodeImpl) pNode).getLevel();
    }

    @Override
    public String toString() {
        return "Node{" + "label=" + label + ", level=" + getLevel() + '}';
    }

}
