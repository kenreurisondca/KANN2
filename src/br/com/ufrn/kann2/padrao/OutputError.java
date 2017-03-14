/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.padrao;

/**
 *
 * @author kenreurison
 */
public class OutputError {

    private Double FP;
    private Double FN;

    public OutputError(Double FP, Double FN) {
        this.FP = FP;
        this.FN = FN;
    }

    public Double getFP() {
        return FP;
    }

    public Double getFN() {
        return FN;
    }

    public Double getError() {
        return FP + FN;
    }

    public OutputError incFP() {
        FP++;
        return this;
    }

    public OutputError incFN() {
        FN++;
        return this;
    }

    @Override
    public String toString() {
        return "OutputError{" + "FP=" + FP + ", FN=" + FN + '}';
    }
    
    
}
