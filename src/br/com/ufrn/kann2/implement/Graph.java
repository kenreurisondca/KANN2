/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.implement;

import br.com.ufrn.kann2.observer.Subject;
import com.sun.org.apache.xpath.internal.axes.WalkerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 *
 * @author kenreurison
 */
public class Graph extends Subject {

    private List<Edge> edges = new ArrayList<>();
    private Map<String, Node> nodes = new HashMap<>();
    private Map<String, Node> inputMap = new HashMap<>();
    private Map<String, Node> outputMap = new HashMap<>();
    private List<Rule> rules = new ArrayList<>();
    private Property p = new PropertyGraphImpl();

    public Graph() {
        this.edges = new ArrayList<>();
        this.nodes = new HashMap<>();
    }

    public Graph(ArrayList<Rule> rules) {
        this.rules = rewrite(rules);
    }

    public void mapping() {
        createNodes();
        createEdges();
        adjustBias();
    }

    public Graph(ArrayList<Edge> edges, Map<String, Node> nodes) {
        this.edges = edges;
        this.nodes = nodes;
    }

    public List<Edge> getEdges() {
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

    private Node getNode(String s) {
        return nodes.get(s.replace("¬", ""));
    }

    private ArrayList<Rule> rewrite(ArrayList<Rule> rules) {
        Map<String, Integer> statConsequent = statConsequent(rules);
        Map<String, Integer> aux = new HashMap<>(statConsequent);
        ArrayList<Rule> newRules = new ArrayList<>();
        ArrayList<Rule> oldRules = new ArrayList<>();
        for (Rule r : rules) {
            String consequent = r.getConsequent();
            Integer sizeConsequent = statConsequent.get(consequent);
            Integer sizeAntecedent = r.getAntecedents().size();
            if (sizeAntecedent > 1 && sizeConsequent > 1) {
                newRules.addAll(r.rewriteRule(aux.get(consequent)));
                oldRules.add(r);
                aux.put(r.getConsequent(), aux.get(consequent) - 1);
            }
        }
        rules.addAll(newRules);
        rules.removeAll(oldRules);
        return rules;
    }

    public void rewrite() {
        rules = rewrite((ArrayList<Rule>) rules);
    }

    private Map<String, Integer> statConsequent(List<Rule> rules) {
        Map<String, Integer> countConsec = new HashMap<>();
        for (Rule r : rules) {
            String c = r.getConsequent();
            if (countConsec.get(c) == null) {
                countConsec.put(c, 1);
            } else {
                countConsec.put(c, countConsec.get(c) + 1);
            }
        }
        return countConsec;
    }

    private void createNodes() {
        for (Rule r : rules) {
            List<String> antecedents = r.getAntecedents();
            String consequents = r.getConsequent();
            antecedents.forEach((s) -> this.nodes.put(s.replace("¬", ""), new Node(s.replace("¬", ""))));
            this.nodes.put(consequents, new Node(consequents));
        }
    }

    private void createEdges() {
        Edge e = null;
        Node nodeOut = null;
        Node nodeIn = null;
        for (Rule r : rules) {
            for (String s : r.getAntecedents()) {
                nodeOut = this.getNode(r.getConsequent());
                nodeIn = this.getNode(s);
                if (s.startsWith("¬")) {
                    e = new Edge(nodeIn, nodeOut, -4.);
                } else {
                    e = new Edge(nodeIn, nodeOut, 4.);
                }
                nodeIn.addEdgeOut(e);
                nodeOut.addEdgeIn(e);
                edges.add(e);
            }
        }
    }

    private void adjustBias() {
        Double bias = 0.;
        for (Rule r : rules) {
            if (r.getAntecedents().size() == 1) {
                bias = -2.;
            } else if (r.getAntecedents().size() > 1) {
                Integer N = countPositiveAntecedents(r);
                bias = 2. - 4. * N;
            }
            String consequent = r.getConsequent();
            Node nodeAdjust = nodes.get(consequent);
            nodeAdjust.setBias(bias);
        }
    }

    private Map<String, Integer> statAntecedent(List<Rule> rules) {
        Map<String, Integer> res = new HashMap<>();
        Integer soma = 0;
        for (Rule r : rules) {
            if (r.getAntecedents().size() == 1) {
                nodes.get(r.getConsequent()).setBias(Double.NaN);
            }
        }
        return res;
    }

    private Integer countPositiveAntecedents(Rule r) {
        Integer N = 0;
        for (String s : r.getAntecedents()) {
            if (!s.startsWith("¬")) {
                N = N + 1;
            }
        }
        return N;
    }

    public static void main(String[] args) {
        ArrayList<Rule> rules = new ArrayList();
        rules.add(new Rule("A :- B, Z"));
        rules.add(new Rule("B :- C, D"));
        rules.add(new Rule("B :- E, F, G"));
        rules.add(new Rule("Z :- Y, ¬X"));
        rules.add(new Rule("Y :- S, T"));
        Graph g = new Graph(rules);//Passo 1: Rewrite
        g.mapping();//Passo 2
    }

}
