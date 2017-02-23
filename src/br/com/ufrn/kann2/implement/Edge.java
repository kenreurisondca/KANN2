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
        this.setWeigth(weigth);
    }

    public Edge(Double weigth) {
        this.setWeigth(weigth);
    }

    public void disturbWeigth() {
        Double weigth = getWeigth();
        RandomKann r = RandomKann.getInstance();
        ((PropertyEdgeImpl) p).setWeigth(weigth + r.nextDouble() - 0.5);
    }

    public Double getWeigth() {
        return ((PropertyEdgeImpl) p).getWeigth();
    }

    public void setWeigth(Double w) {
        ((PropertyEdgeImpl) p).setWeigth(w);
    }

    public void addWeigth(Double d) {
        Double weigth = getWeigth();
        ((PropertyEdgeImpl) p).setWeigth(weigth + d);
    }

    public Node getIn() {
        return in;
    }

    public Node getOut() {
        return out;
    }

    @Override
    public String toString() {
        return "Edge{ " + in.getLabel() + " -> " + out.getLabel() + " }";
    }

    public boolean exists(Node a, Node b) {
        return a.getLabel().equals(this.in.getLabel()) && b.getLabel().equals(this.out.getLabel());
    }
}
