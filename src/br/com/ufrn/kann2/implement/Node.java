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
public class Node implements Subject {
    
    public ArrayList<Edge> edgesIn = new ArrayList<>();
    public ArrayList<Edge> edgesOut = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();
    Property p = new PropertyNodeImpl();
    
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
    
    public Double net() {
        Double sum = p.getValue("bias");
        for (Edge e : edgesIn) {
            sum += e.getPeso();
        }
        return sum;
    }
}
