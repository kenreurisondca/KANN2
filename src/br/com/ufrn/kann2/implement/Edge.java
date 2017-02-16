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
public class Edge implements Subject {

    public Node in = new Node();
    public Node out = new Node();
    private List<Observer> observers = new ArrayList();
    private Property p = new PropertyEdgeImpl();

    public Edge() {

    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(o);
        }
    }

    @Override
    public void notifyObserver() {
        for (Observer o : observers) {
            o.update();
        }
    }

    Double getPeso() {
        return p.getValue("weigth");
    }

}
