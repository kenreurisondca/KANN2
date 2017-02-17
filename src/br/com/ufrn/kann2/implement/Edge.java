/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.implement;

import br.com.ufrn.kann2.observer.Observer;
import br.com.ufrn.kann2.observer.Subject;
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

    Double getWeigth() {
        return ((PropertyEdgeImpl) p).getWeigth();
    }

    void setWeigth(Double w) {
        ((PropertyEdgeImpl) p).setWeigth(w);
    }

    public Node getIn() {
        return in;
    }

    public Node getOut() {
        return out;
    }

}
