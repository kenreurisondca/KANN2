/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.implement;

import br.com.ufrn.kann2.observer.Subject;
import br.com.ufrn.kann2.util.RandomKann;
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

    public Property getpNode() {
        return pNode;
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

    public void setBias(Double b) {
        ((PropertyNodeImpl) pNode).setBias(b);
    }

    public Double getBias() {
        return ((PropertyNodeImpl) pNode).getBias();
    }

    public Double getValue() {
        return ((PropertyNodeImpl) pNode).getValue();
    }

    void addEdgeIn(Edge e) {
        if (!edgesIn.contains(e)) {
            ((PropertyNodeImpl) pNode).incSizeInput();
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

    void disturbBias() {
        Double bias = getBias();
        RandomKann r = RandomKann.getInstance();
        ((PropertyNodeImpl) pNode).setBias(bias + r.nextDouble() - 0.5);
    }

    public Double getOldBias() {
        return ((PropertyNodeImpl) pNode).getOldBias();
    }

    public void addValue(Double d) {
        ((PropertyNodeImpl) pNode).addValue(d);
    }

    public void setValue(Double d) {
        ((PropertyNodeImpl) pNode).setMapValue("value", d);
    }

    public void propagateRec() {
        PropertyNodeImpl prop = (PropertyNodeImpl) pNode;
        if (this.edgesIn.isEmpty()) {
            this.getEdgesOut().forEach((e) -> e.getNodeOut().addValue(prop.getValue() * e.getWeigth()));
            this.getEdgesOut().forEach((e) -> e.getNodeOut().propagateRec());
        } else if (this.isReady()) {
            prop.setActivation(this.getValue() + this.getBias());
            this.getEdgesOut().forEach((e) -> e.getNodeOut().addValue(prop.getActivation() * e.getWeigth()));
            this.getEdgesOut().forEach((e) -> e.getNodeOut().propagateRec());
        }
    }

    public void setActivation(Double d) {
        PropertyNodeImpl prop = (PropertyNodeImpl) pNode;
        prop.setActivation(d);
    }

    @Override
    public String toString() {
        return "Node{" + "label=" + label
                + ", bias=" + getBias()
                + ", value=" + getValue()
                + ", activation=" + getActivation() + "}\n";
    }

    public boolean isReady() {
        return ((PropertyNodeImpl) pNode).isReady();
    }

    protected PropertyNodeImpl getProperty() {
        return ((PropertyNodeImpl) pNode);
    }

    public void propagateIter() {
        PropertyNodeImpl prop = (PropertyNodeImpl) pNode;
        if (this.edgesIn.isEmpty()) {
            this.getEdgesOut().forEach((e) -> e.getNodeOut().addValue(prop.getValue() * e.getWeigth()));
        } else if (this.isReady()) {
            prop.setActivation(this.getValue() + this.getBias());
            this.getEdgesOut().forEach((e) -> e.getNodeOut().addValue(prop.getActivation() * e.getWeigth()));
        }
    }

    protected void clean() {
        ((PropertyNodeImpl) pNode).cleanFields();
    }

    public void addBias(Double d) {
        Double bias = getBias();
        ((PropertyEdgeImpl) pNode).setBias(bias + d);
    }

}
