/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.implement;

import br.com.ufrn.kann2.algorithms.Algorithm;
import br.com.ufrn.kann2.algorithms.Backpropagation;
import br.com.ufrn.kann2.algorithms.Validation;
import br.com.ufrn.kann2.padrao.PatternBoard;
import br.com.ufrn.kann2.util.ReadFile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kenreurison
 */
public class Main {

    public static void main(String[] args) {
        ReadFile r = new ReadFile("src\\br\\com\\ufrn\\kann2\\resources\\xadrez_-1Regra.txt");
        ArrayList<Rule> rules = r.getRules();
        Graph g = new Graph();
        g.rewrite(rules);//Passo 1
        g.mapping();//Passo 2
        g.labeling();//Passo 3
        g.addLinks_form3(); // Passo 6
        g.disturbEdges();//Passo 7

        //Treinamento
        Algorithm bp = new Backpropagation();
        PatternBoard board = new PatternBoard(g.getInputs().keySet(), g.getOutput().keySet());
        bp.setPattern(board);
        ((Backpropagation) bp).setMaxError(0.05);
        bp.setEta(0.5);
        bp.setMaxIter(1000.);
        bp.setSampleSize(7000.);
        g.setAlgorithm(bp);
        Double v;
        List<Double> trainningErrors = new ArrayList<>();
        List<Double> validationErrors = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bp.train();
            Double t = ((Backpropagation) bp).getTrainningError();
            trainningErrors.add(t);
            Validation val = new Validation(g, board);
            v = val.validate(3000, 1);
            validationErrors.add(v);
        }
        System.out.println(trainningErrors.toString() + validationErrors.toString());
    }
}
