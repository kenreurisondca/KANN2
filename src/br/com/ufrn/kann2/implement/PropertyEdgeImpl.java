/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.implement;

/**
 *
 * @author kenreurison
 */
public class PropertyEdgeImpl extends Property {

    public PropertyEdgeImpl() {
        super();
    }

    @Override
    public void createFields() {
        registerField("weigth");
        registerField("oldWeigth");
    }

}
