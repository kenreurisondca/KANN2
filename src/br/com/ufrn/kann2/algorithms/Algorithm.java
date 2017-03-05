/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.algorithms;

import br.com.ufrn.kann2.implement.Graph;
import br.com.ufrn.kann2.padrao.Pattern;
import br.com.ufrn.kann2.padrao.OutputError;

/**
 *
 * @author kenreurison
 */
public abstract class Algorithm {

    protected Graph graph;
    protected Pattern pattern;
    private OutputError op;

    public Algorithm(Graph graph) {
        this.graph = graph;
    }

    public Algorithm() {

    }

    public abstract void forwardRec();

    public abstract void forwardIter();

    public abstract void backwardRec();

    public abstract void backwardIter();

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

}
