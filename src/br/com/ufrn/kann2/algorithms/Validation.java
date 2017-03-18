/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.algorithms;

import br.com.ufrn.kann2.implement.Graph;
import br.com.ufrn.kann2.implement.Node;
import br.com.ufrn.kann2.padrao.OutputError;
import br.com.ufrn.kann2.padrao.Pattern;
import static java.lang.Double.sum;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author kenreurison
 */
public final class Validation extends ForwardRules {

    private Graph g;
    private Pattern p;

    public Validation(Graph g, Pattern p) {
        super.op = new OutputError(0., 0.);
        this.g = g;
        this.pattern = p;

    }

    public Double validate(Integer sampleSize, Integer iterSize) {
        g.setAlgorithm(this);
        this.setPattern(this.pattern);
        Double d, y;
        Double dError, yError;
        Double sum = 0.;
        Double limiar = 12. / 13.;
        //Double limiar = 1. / 2.;
        ArrayList<Node> intermediateNodes = new ArrayList<>();
        intermediateNodes.add(graph.getNodes().get("PCK"));
        intermediateNodes.add(graph.getNodes().get("DCK"));
        intermediateNodes.add(graph.getNodes().get("KCK"));
        for (int i = 0; i < iterSize; i++) {
            for (int j = 1; j < sampleSize; j++) {
                pattern.generateInputOutput();
                this.train();
                for (Node n : graph.getOutput().values()) {
                    dError = pattern.getOutputs().get(n.getLabel());
                    yError = n.getActivation();
                    sum += Math.pow(dError - yError, 2);
                    d = (pattern.getOutputs().get(n.getLabel()));
                    y = n.getActivation();
                    if (y < limiar && d == 1.0) {
                        n.getOp().incFN();
                    } else if (y > limiar && d == 0.0) {
                        n.getOp().incFP();
                    }
                }
                for (Node n : intermediateNodes) {
                    d = (pattern.getIntermediate().get(n.getLabel()));
                    y = n.getActivation();
                    if (y < limiar && d == 1.0) {
                        n.getOp().incFN();
                    } else if (y > limiar && d == 0.0) {
                        n.getOp().incFP();
                    }

                }
            }
        }
        //return (0.5 * sum / ((sampleSize) * (iterSize)));
        Double outputErro = graph.getOutput().get("IM").getOp().getError();
        Double i1 = intermediateNodes.get(0).getOp().getError();
        Double i2 = intermediateNodes.get(1).getOp().getError();
        Double i3 = intermediateNodes.get(2).getOp().getError();
        return outputErro + i1 + i2 + i3;
    }
}
