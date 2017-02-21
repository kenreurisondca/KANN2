/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.implement;

import br.com.ufrn.kann2.observer.Subject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kenreurison
 */
public class Graph extends Subject {

    private ArrayList<Edge> edges;
    private Map<String, Node> nodes;
    private Map<String, Node> inputMap = new HashMap<>();
    private Map<String, Node> outputMap = new HashMap<>();
    Property p = new PropertyGraphImpl();

    public Graph() {
        this.edges = new ArrayList<>();
        this.nodes = new HashMap<>();
    }

    public Graph(ArrayList<Rule> rules) {
        for (Rule r : rules) {
            List<String> antecedents = r.getAntecedents();
            String consequent = r.getConsequent();
            createEdges(antecedents, consequent);
        }
    }

    public Graph(ArrayList<Edge> edges, Map<String, Node> nodes) {
        this.edges = edges;
        this.nodes = nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public Map<String, Node> getNodes() {
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

    private boolean existEdge(String a, String b) {
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).getIn().getLabel().equals(a) && edges.get(i).getOut().getLabel().equals(b)) {
                return true;
            }
        }
        return false;
    }

    private void createEdges(List<String> antecedents, String consequent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
