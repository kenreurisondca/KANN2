/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.padrao;

import br.com.ufrn.kann2.algorithms.Algorithm;
import br.com.ufrn.kann2.algorithms.Backpropagation;
import br.com.ufrn.kann2.algorithms.ForwardRules;
import br.com.ufrn.kann2.implement.Graph;
import br.com.ufrn.kann2.implement.Rule;
import br.com.ufrn.kann2.util.RandomKann;
import br.com.ufrn.kann2.util.ReadFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kenreurison
 */
public class PatternBoard extends Pattern {

    private List<String> pieces = new ArrayList(18);
    private Map<Integer, String> positionMap = new HashMap<>();
    private Map<String, Integer> reversePositionMap = new HashMap<>();

    public PatternBoard() {
        super();
        generatePieces();
        initMap();
        Collections.shuffle(pieces, RandomKann.getInstance());
        generateInputOutput();
    }

    private void initMap() {
        positionMap.put(0, "A1");
        positionMap.put(4, "A2");
        positionMap.put(8, "A3");
        positionMap.put(13, "A4");

        positionMap.put(1, "B1");
        positionMap.put(5, "B2");
        positionMap.put(9, "B3");
        positionMap.put(14, "B4");

        positionMap.put(10, "C3");
        positionMap.put(15, "C4");

        positionMap.put(2, "D1");
        positionMap.put(6, "D2");
        positionMap.put(11, "D3");
        positionMap.put(16, "D4");

        positionMap.put(3, "E1");
        positionMap.put(7, "E2");
        positionMap.put(12, "E3");
        positionMap.put(17, "E4");

        positionMap.forEach((k, v) -> reversePositionMap.put(v, k));
    }

    private void generatePieces() {
        pieces.add("Q");
        pieces.add("B");
        pieces.add("K");
        pieces.add("R");
        for (int i = 0; i < 10; i++) {
            pieces.add("E");
        }
        for (int i = 0; i < 4; i++) {
            pieces.add("F");
        }
    }

    public static void main(String[] args) {
        PatternBoard b = new PatternBoard();
        PatternBoard b2 = b.randomBoard();
    }

    @Override
    public void generateInputOutput() {
        //Mapeando entrada
        ReadFile f = new ReadFile("src\\br\\com\\ufrn\\kann2\\resources\\xadrez.txt");
        List<String> vocabulary = f.getVocabulary();
        for (String s : vocabulary) {
            inputs.put(s, evaluate(s));
        }
        //Gerando saidas
        generateOutput();
    }

    @Override
    protected void generateIntermediateConclusions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Double evaluate(String s) {
        int length = s.length();
        String strPosition = s.substring(length - 2);
        String strFunction = s.replaceAll(strPosition, "");
        Integer i = reversePositionMap.get(strPosition);
        if (i != null) {
            String p = pieces.get(i);
            if ("Q".equals(strFunction)) {
                return isQueen(strPosition);
            } else if ("OC".equals(strFunction)) {
                return isOpositeColor(strPosition);
            } else if ("R".equals(strFunction)) {
                return isRook(strPosition);
            } else if ("B".equals(strFunction)) {
                return isBishop(strPosition);
            } else if ("E".equals(strFunction)) {
                return isEmpty(strPosition);
            } else if ("K".equals(strFunction)) {
                return isKnigth(strPosition);
            }
        }
        return 0.;
    }

    private Double isQueen(String strPosition) {
        Integer i = reversePositionMap.get(strPosition);
        if ("Q".equals(pieces.get(i))) {
            return 1.;
        } else {
            return 0.;
        }
    }

    private Double isRook(String strPosition) {
        Integer i = reversePositionMap.get(strPosition);
        if ("R".equals(pieces.get(i))) {
            return 1.;
        } else {
            return 0.;
        }
    }

    private Double isBishop(String strPosition) {
        Integer i = reversePositionMap.get(strPosition);
        if ("B".equals(pieces.get(i))) {
            return 1.;
        } else {
            return 0.;
        }
    }

    private Double isKnigth(String strPosition) {
        Integer i = reversePositionMap.get(strPosition);
        if ("K".equals(pieces.get(i))) {
            return 1.;
        } else {
            return 0.;
        }
    }

    private Double isEmpty(String strPosition) {
        Integer i = reversePositionMap.get(strPosition);
        if ("E".equals(pieces.get(i))) {
            return 1.;
        } else {
            return 0.;
        }
    }

    private Double isOpositeColor(String strPosition) {
        Integer i = reversePositionMap.get(strPosition);
        if (!"F".equals(pieces.get(i)) && !"E".equals(pieces.get(i))) {
            return 1.;
        } else {
            return 0.;
        }
    }

    private PatternBoard randomBoard() {
        Collections.shuffle(pieces, RandomKann.getInstance());
        return this;
    }

    @Override
    protected void generateOutput() {
        Graph g = new Graph();
        ReadFile f = new ReadFile("src\\br\\com\\ufrn\\kann2\\resources\\xadrez.txt");
        ArrayList<Rule> rules = f.getRules();
        g.rewrite(rules);//Passo 1
        g.mapping();//Passo 2
        g.labeling();//Passo 3
        Algorithm fr = new ForwardRules();
        ((ForwardRules) fr).setPattern(new PatternBoard());
        g.setAlgorithm(fr);
        fr.train();
    }
}
