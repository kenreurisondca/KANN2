/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.algorithms;

import br.com.ufrn.kann2.implement.Graph;
import br.com.ufrn.kann2.implement.Node;
import br.com.ufrn.kann2.padrao.Pattern;
import br.com.ufrn.kann2.padrao.OutputError;
import java.util.Map;

/**
 *
 * @author kenreurison
 */
public abstract class Algorithm {

    private Graph graph;
    private Pattern p;
    private OutputError op;

    public Algorithm(Graph graph) {
        this.graph = graph;
    }
    
    protected void forward(){
        Map<String, Node> inputs = graph.getinputs();
    }

    protected void backward(){
        Map<String, Node> output = graph.getOutput();
    }

}
