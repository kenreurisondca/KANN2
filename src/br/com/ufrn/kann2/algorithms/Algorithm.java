/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.algorithms;

import br.com.ufrn.kann2.implement.Graph;
import br.com.ufrn.kann2.implement.Property;
import br.com.ufrn.kann2.padrao.Pattern;
import br.com.ufrn.kann2.padrao.OutputError;

/**
 *
 * @author kenreurison
 */
public abstract class Algorithm {

    protected Graph graph;
    protected Pattern pattern;
    protected OutputError op;
    protected Property p = new PropertyAlgorithmImpl();

    public Algorithm(Graph graph) {
        this.graph = graph;
    }

    public Algorithm() {

    }

    public abstract void forwardRec();

    public abstract void forwardIter();

    public abstract Double backwardRec();

    public abstract Double backwardIter();

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public abstract void train();

}
