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
        Long d, y;
        Double dError, yError;
        Double sum = 0.;
        for (int i = 0; i < iterSize; i++) {
            for (int j = 1; j < sampleSize; j++) {
                pattern.generateInputOutput();
                this.train();
                for (Node n : graph.getOutput().values()) {
                    dError = pattern.getOutputs().get(n.getLabel());
                    yError = n.getActivation();
                    sum += Math.pow(dError - yError, 2);
//                    d = (pattern.getOutputs().get(n.getLabel())).longValue();
//                    y = Math.round(n.getActivation());
//                    if (!Objects.equals(y, d)) {
//                        if (y < d) {
//                            this.op.incFN();
//                        } else {
//                            this.op.incFP();
//                        }
//                    }
                }
            }
        }
        return (0.5 * sum / ((sampleSize) * (iterSize)));
    }
}
