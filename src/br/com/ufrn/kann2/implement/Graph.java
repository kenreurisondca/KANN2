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
public class Graph extends Subject {

    private ArrayList<Edge> edges;
    private ArrayList<Node> nodes;
    Property p = new PropertyGraphImpl();

    public Graph(){
        this.edges = new ArrayList<>();
        this.nodes = new ArrayList<>();
    }
    
    public Graph(ArrayList<Edge> edges, ArrayList<Node> nodes) {
        this.edges = edges;
        this.nodes = nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    
    
    
}
