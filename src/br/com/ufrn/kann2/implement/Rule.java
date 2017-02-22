/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.implement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author André
 */
public class Rule {

    private String consequent;
    private List<String> antecedents = new ArrayList<>();

    public Rule(String r) {
        int auxPosicao = r.indexOf("-");
        this.consequent = r.substring(0, auxPosicao - 2);
        String auxRegra = r.substring(consequent.length() + 4);
        while (auxRegra.contains(",")) {
            auxPosicao = auxRegra.indexOf(",");
            antecedents.add(auxRegra.substring(0, auxPosicao));
            auxRegra = auxRegra.substring(auxPosicao + 2);
        }
        antecedents.add(auxRegra);
    }

    public Rule(String c, String a) {
        this.consequent = c;
        antecedents.add(a);
    }

    public Rule(String c, List<String> a) {
        this.consequent = c;
        antecedents.addAll(a);
    }

    public Rule() {

    }

    public void addAtendecente(String a) {
        antecedents.add(a);
    }

    public String getConsequente() {
        return consequent;
    }

    public void setConsequente(String consequente) {
        this.consequent = consequente;
    }

    public String getAntecedente(int i) {
        return antecedents.get(i);
    }

    public String getConsequent() {
        return consequent;
    }

    public List<String> getAntecedents() {
        return antecedents;
    }

    public void setAntecedente(int i, String r) {
        antecedents.set(i, r);
    }

    public int tamanhoAntecedentes() {
        return antecedents.size();
    }

    public void addAntecedente(String r) {
        antecedents.add(r);
    }

    public ArrayList<Rule> rewriteRule(Integer labelInt) {
        String c = this.getConsequent() + Integer.toString(labelInt);
        Rule r1 = new Rule(c, this.getAntecedents());
        Rule old = new Rule(this.getConsequent(), c);
        ArrayList<Rule> rules = new ArrayList<>();
        rules.add(old);
        rules.add(r1);
        return rules;
    }

    @Override
    public String toString() {
        return "Rule{" + "consequent=" + consequent + ", antecedents=" + antecedents + '}';
    }
    
    
}
