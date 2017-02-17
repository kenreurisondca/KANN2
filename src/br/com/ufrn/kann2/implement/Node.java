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
    Property pNode = new PropertyNodeImpl();
    
    public void setNet() {
        Double sum = ((PropertyNodeImpl) pNode).getBias();
        for (Edge e : edgesIn) {
            sum += e.getPeso();
        }
        ((PropertyNodeImpl) pNode).setNet(sum);
    }
    
    public Double getNet() {
        return ((PropertyNodeImpl) pNode).getNet();
    }
    
    public void activation() {
        ((PropertyNodeImpl) pNode).setActivation(logsig(getNet()));
    }
    
    public Double getActivation() {
        return ((PropertyNodeImpl) pNode).getActivation();
    }
    
    private Double logsig(Double net) {
        return 1. / (1. + Math.exp(-net));
    }
    
}
