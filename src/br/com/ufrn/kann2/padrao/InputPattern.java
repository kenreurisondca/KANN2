/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.padrao;

import br.com.ufrn.kann2.implement.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kenreurison
 */
public abstract class InputPattern {

    protected List<Double> input = new ArrayList<>();
    protected List<Double> output = new ArrayList<>();

    public abstract Map<String, Double> mapping(List<Node> list);

    public abstract Map<String, Double> evaluate();

    public abstract ArrayList<Double> getOutput();

    public abstract ArrayList<Double> getInput();

    public abstract Double getError(Map<String, Double> desired);

}
