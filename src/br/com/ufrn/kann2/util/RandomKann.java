/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.util;

import java.util.Random;

/**
 *
 * @author kenreurison
 */
public final class RandomKann extends Random {

    private static RandomKann uniqueInstance;

    private RandomKann() {
        super();
    }

    private RandomKann(long l) {
        super(l);
    }

    public static RandomKann getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new RandomKann();
        }
        return uniqueInstance;
    }

    public static RandomKann getInstance(long l) {
        if (uniqueInstance == null) {
            uniqueInstance = new RandomKann(l);
        }
        System.out.println("Não foi possível criar um novo numero aleatorio");
        return uniqueInstance;
    }

}
