/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.observer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kenreurison
 */
public abstract class Subject {

    private List<Observer> observers = new ArrayList();

    public void registerObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(o);
        }
    }

    public void notifyObserver() {
        for (Observer o : observers) {
            o.update();
        }
    }
}
