/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.implement;

import br.com.ufrn.kann2.observer.Subject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kenreurison
 */
public class Graph extends Subject {

    private ArrayList<Edge> edges;
    private ArrayList<Node> nodes;
    private Map<String, Node> inputMap = new HashMap<>();
    private Map<String, Node> outputMap;
    Property p = new PropertyGraphImpl();

    public Graph() {
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

    public Map<String, Node> getinputs() {
        if (inputMap.isEmpty()) {
            performInputMap();
        }
        return inputMap;
    }

    public Map<String, Node> getOutput() {
        if (outputMap.isEmpty()) {
            performOutputMap();
        }
        return outputMap;
    }

    private void performInputMap() {
        boolean inputBoolean;
        for (int i = 0; i < edges.size(); i++) {
            Node source = edges.get(i).getIn();
            inputBoolean = true;
            for (int j = 0; j < edges.size(); j++) {
                if (edges.get(j).getOut().getLabel().equals(source.getLabel())) {
                    inputBoolean = false;
                }
                if (edges.get(j).getIn() == source) {
                    inputBoolean = false;
                }
            }
            if (inputBoolean) {
                inputMap.put(source.getLabel(), source);
            }
        }
    }

    private void performOutputMap() {
        boolean saida;
        for (int i = 0; i < edges.size(); i++) {
            Node destino = edges.get(i).getOut();
            saida = true;
            for (int j = 0; j < edges.size(); j++) {
                if (edges.get(j).getIn().getLabel().equals(destino.getLabel())) {
                    saida = false;
                }
            }
            if (saida) {
                outputMap.put(destino.getLabel(), destino);
            }
        }
    }

}
