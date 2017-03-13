/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.implement;

import br.com.ufrn.kann2.algorithms.Algorithm;
import br.com.ufrn.kann2.algorithms.Backpropagation;
import br.com.ufrn.kann2.observer.Subject;
import br.com.ufrn.kann2.padrao.PatternExample;
import br.com.ufrn.kann2.util.ReadFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author kenreurison
 */
public class Graph extends Subject implements Cloneable {

    private List<Edge> edgeList;
    private Map<String, Node> nodeMap;
    private Map<String, Node> inputMap = new HashMap<>();
    private Map<String, Node> outputMap = new HashMap<>();
    private List<Rule> rules;
    private Property p = new PropertyGraphImpl();
    private Algorithm algorithm;

    public Graph() {
        this.edgeList = new ArrayList<>();
        this.nodeMap = new HashMap<>();
        this.rules = new ArrayList<>();

    }

    public static void main(String[] args) {

        ReadFile r = new ReadFile("src\\br\\com\\ufrn\\kann2\\resources\\xadrez_1.txt");
        ArrayList<Rule> rules = r.getRules();
        Graph g2 = new Graph();
        g2.rewrite(rules);//Passo 1
        g2.mapping();//Passo 2
        Map<String, Node> inputs = g2.getInputs();
        Set<String> keySet = g2.inputMap.keySet();
        //inputs.keySet().forEach((s) -> keyList.add(s));
        g2.labeling();//Passo 3
        g2.addLinks_form1(); // Passo 6
        g2.disturbEdges();//Passo 7
        //Graph clone = (Graph) g2.clone();

        //Treinamento
        Algorithm bp = new Backpropagation();
        bp.setPattern(new PatternExample());
        ((Backpropagation) bp).setMaxError(0.01);
        bp.setEta(0.1);
        bp.setMaxIter(1000.);
        g2.setAlgorithm(bp);
        bp.train();

    }

    public void mapping() {
        createNodes();
        createEdges();
        adjustBias();
        performInputMap();
        performOutputMap();
    }

    public Graph(List<Edge> edges, Map<String, Node> nodes) {
        this.edgeList = edges;
        this.nodeMap = nodes;
    }

    public List<Edge> getEdges() {
        return edgeList;
    }

    public Map<String, Node> getNodes() {
        return nodeMap;
    }

    public Map<String, Node> getInputs() {
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

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
        this.algorithm.setGraph(this);
    }

    private void performOutputMap() {
        Collection<Node> values = nodeMap.values();
        for (Node n : values) {
            if (n.getEdgesOut().isEmpty()) {
                String s = n.getLabel();
                outputMap.put(s, n);
            }
        }
    }

    private void performInputMap() {
        Collection<Node> values = nodeMap.values();
        for (Node n : values) {
            if (n.getEdgesIn().isEmpty()) {
                String s = n.getLabel();
                inputMap.put(s, n);
            }
        }
    }

    private Node getNode(String s) {
        return nodeMap.get(s.replace("¬", ""));
    }

    public List<Rule> rewrite(ArrayList<Rule> rules) {
        this.rules = rules;
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
        this.rules = rewrite((ArrayList<Rule>) rules);
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
            antecedents.forEach((s) -> this.nodeMap.put(s.replace("¬", ""), new Node(s.replace("¬", ""))));
            this.nodeMap.put(consequents, new Node(consequents));
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        super.clone();
        Graph gClone = new Graph();
        ArrayList<Edge> copy = new ArrayList<Edge>(this.edgeList.size());
        Collections.copy(copy, this.edgeList);
        gClone.setEdgeList(copy);
        return gClone;
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
                    e.setOldWeigth(-4.);
                } else {
                    e = new Edge(nodeIn, nodeOut, 4.);
                    e.setOldWeigth(4.);
                }
                nodeIn.addEdgeOut(e);
                nodeOut.addEdgeIn(e);
                edgeList.add(e);
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
            Node nodeAdjust = nodeMap.get(consequent);
            nodeAdjust.setBias(bias);
            nodeAdjust.setOldBias(bias);
        }
    }

    private Map<String, Integer> statAntecedent(List<Rule> rules) {
        Map<String, Integer> res = new HashMap<>();
        Integer soma = 0;
        for (Rule r : rules) {
            if (r.getAntecedents().size() == 1) {
                nodeMap.get(r.getConsequent()).setBias(Double.NaN);
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

    public void labeling() {
        Collection<Node> values = inputMap.values();
        values.forEach((n) -> propagateLevel(n));
    }

    //Selectiona todos os Nodes que CHEGAM em a
    private ArrayList<Node> nodesIn(Node a) {
        ArrayList<Node> res = new ArrayList<>();
        a.getEdgesIn().forEach((e) -> res.add(e.getNodeIn()));
        return res;
    }

    //Seleciona todos os Nodes que SAEM de b
    private ArrayList<Node> nodesOut(Node b) {
        ArrayList<Node> res = new ArrayList<>();
        b.getEdgesOut().forEach((e) -> res.add(e.getNodeOut()));
        return res;
    }

    private void propagateLevel(Node n) {
        ArrayList<Node> nodesOut = this.nodesOut(n);
        for (Node nodeConseq : nodesOut) {
            Double level = nodeConseq.getLevel();
            if (level <= n.getLevel()) {
                nodeConseq.setLevel(n.getLevel() + 1);
                if (!outputMap.containsKey(nodeConseq.getLabel())) {
                    propagateLevel(nodeConseq);
                } else {
                    ((PropertyGraphImpl) p).updateMaxLevel(nodeConseq.getLevel());
                }
            }
        }
    }

    public boolean addHiddenUnit(String ha, int i) {
        Integer maxLevel = ((PropertyGraphImpl) p).getMaxLevel();
        if (i > 0 && i < maxLevel) {
            Node node = new Node(ha);
            node.setLevel(i);
            nodeMap.put(ha, node);
            return true;
        } else {
            return false;
        }
    }

    public void addInputUnit(String ia) {
        Node a = new Node(ia);
        a.setLevel(0.0);
        inputMap.put(ia, a);
        nodeMap.put(ia, a);
    }

    public List<Edge> addLinks_form1() {
        Integer maxLevel = ((PropertyGraphImpl) p).getMaxLevel();
        List<Edge> res = new ArrayList<>();
        for (int i = 0; i < maxLevel; i++) {
            res.addAll(connect(i, i + 1));
        }
        edgeList.addAll(res);
        return res;
    }

    public List<Edge> addLinks_form2() {
        Integer maxLevel = ((PropertyGraphImpl) p).getMaxLevel();
        List<Edge> res = new ArrayList<>();
        for (int i = 0; i < maxLevel; i++) {
            for (int j = i + 1; j <= maxLevel; j++) {
                res.addAll(connect(i, j));
            }
        }
        edgeList.addAll(res);
        return res;
    }

    public List<Edge> addLinks_form3() {
        Integer maxLevel = ((PropertyGraphImpl) p).getMaxLevel();
        List<Edge> res = new ArrayList<>();
        for (int j = 1; j <= maxLevel; j++) {
            res.addAll(connect(0, j));
        }
        edgeList.addAll(res);
        return res;
    }

    private List<Edge> connect(int i, int i0) {
        ArrayList<Node> ant = new ArrayList<>();
        ArrayList<Node> cons = new ArrayList<>();
        List<Edge> res = new ArrayList<>();
        nodeMap.forEach((k, v) -> {
            if (v.getLevel() == i) {
                ant.add(v);
            }
        });
        nodeMap.forEach((k, v) -> {
            if (v.getLevel() == i0) {
                cons.add(v);
            }
        });
        for (Node low : ant) {
            for (Node upper : cons) {
                if (!this.exists(low, upper)) {
                    res.add(new Edge(low, upper, 0.0));
                }
            }
        }
        return res;
    }

    private boolean exists(Node low, Node upper) {
        for (Edge e : edgeList) {
            if (e.exists(low, upper)) {
                return true;
            }
        }
        return false;
    }

    public void disturbEdges() {
        nodeMap.forEach((k, v) -> v.disturbBias());
        edgeList.forEach((e) -> e.disturbWeigth());
    }

    public void clean() {
        ((PropertyGraphImpl) p).cleanFields();
        edgeList.forEach((e) -> e.clean());
        nodeMap.forEach((k, v) -> v.clean());
    }

    @Override
    public String toString() {
        String res = "";
        for (Node n : nodeMap.values()) {
            res += n.toString() + "\n";
        }
        for (Edge e : edgeList) {
            res += e.toString() + "\n";
        }
        return res;
    }

    public void train() {
        algorithm.train();
    }

    private void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    private void setNodeMap(Map<String, Node> nodeMap) {
        this.nodeMap = nodeMap;
    }

    private void setInputMap(Map<String, Node> inputMap) {
        this.inputMap = inputMap;
    }

    private void setOutputMap(Map<String, Node> outputMap) {
        this.outputMap = outputMap;
    }

    private void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    private void setP(Property p) {
        this.p = p;
    }

}
