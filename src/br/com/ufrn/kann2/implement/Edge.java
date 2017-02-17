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

    public Node in = new Node();
    public Node out = new Node();
    private List<Observer> observers = new ArrayList();
    private Property p = new PropertyEdgeImpl();

    public Edge() {

    }

    Double getPeso() {
        return p.getMapValue("weigth");
    }

}
