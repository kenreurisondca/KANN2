/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.implement;

import br.com.ufrn.kann2.observer.Observer;
import br.com.ufrn.kann2.observer.Subject;
import br.com.ufrn.kann2.util.RandomKann;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kenreurison
 */
public class Edge extends Subject {

    private Node in;
    private Node out;
    private List<Observer> observers = new ArrayList();
    private Property p = new PropertyEdgeImpl();

    public Edge(Node a, Node b) {
        this.in = a;
        this.out = b;
    }

    public Edge(String a, String b) {
        this.in = new Node(a);
        this.out = new Node(b);
    }

    public Edge(Node a, Node b, Double weigth) {
        this.in = a;
        this.out = b;
        a.addEdgeOut(this);
        b.addEdgeIn(this);
        this.setWeigth(weigth);
    }

    public Edge(Double weigth) {
        this.setWeigth(weigth);
    }

    public void disturbWeigth() {
        Double weigth = getWeigth();
        RandomKann r = RandomKann.getInstance();
        ((PropertyEdgeImpl) p).setWeigth(weigth + r.disturbBiasWeigth());
    }

    public Double getWeigth() {
        return ((PropertyEdgeImpl) p).getWeigth();
    }

    public Double getOldWeigth() {
        return ((PropertyEdgeImpl) p).getOldWeigth();
    }

    public void setWeigth(Double w) {
        ((PropertyEdgeImpl) p).setWeigth(w);
    }

    public void addWeigth(Double d) {
        Double weigth = getWeigth();
        ((PropertyEdgeImpl) p).setWeigth(weigth + d);
    }

    public Node getNodeIn() {
        return in;
    }

    public Node getNodeOut() {
        return out;
    }

    @Override
    public String toString() {
        //return "Edge{ " + in.getLabel() + " -> " + out.getLabel() + " } w= + " + getWeigth() + " ow=" + getOldWeigth();
        return "w" + in.getLabel() + out.getLabel() + "=" + getWeigth() + " input=" + in.getActivation() + "output=" + out.getActivation();
    }

    public void clean() {
        ((PropertyEdgeImpl) p).cleanFields();
    }

    public boolean exists(Node a, Node b) {
        String aLabel = a.getLabel();
        String bLabel = b.getLabel();
        String thisaLabel = this.in.getLabel();
        String thisbLabel = this.out.getLabel();
        if (aLabel.equals(thisaLabel) && bLabel.equals(thisbLabel)) {
            return true;
        } else {
            return false;
        }
    }

    public Double getInValue() {
        if (this.in.getEdgesIn().isEmpty()) {
            return this.in.getValue();
        } else {
            return this.in.getActivation();
        }
    }

    void setOldWeigth(Double w) {
        ((PropertyEdgeImpl) p).setOldWeigth(w);
    }
}
