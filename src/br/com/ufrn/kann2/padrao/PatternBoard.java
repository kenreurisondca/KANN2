/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.padrao;

import br.com.ufrn.kann2.util.RandomKann;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    }

    public PatternBoard(Set<String> inputs, Set<String> outputs) {
        super(inputs, outputs);
        generatePieces();
        initMap();
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

    @Override
    protected void generateIntermediateConclusions() {
        if (diagonalCheck() > 0) {
            super.intermediate.put("DCK", 1.);
        } else {
            super.intermediate.put("DCK", 0.);
        }
        if (parallelalCheck() > 0) {
            super.intermediate.put("PCK", 1.);
        } else {
            super.intermediate.put("PCK", 0.);
        }
        if (knigthCheck() > 0) {
            super.intermediate.put("KCK", 1.);
        } else {
            super.intermediate.put("KCK", 0.);
        }
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

    public Double diagonalCheck() {
        Double res;
        res = (isQueen("A4") + isBishop("A4")) * isOpositeColor("A4") * isEmpty("B3");
        res += (isQueen("E4") + isBishop("E4")) * isOpositeColor("E4") * isEmpty("D3");
        res += (isQueen("B3") + isBishop("B3")) * isOpositeColor("B3");
        res += (isQueen("D3") + isBishop("D3")) * isOpositeColor("D3");
        res += (isQueen("B1") + isBishop("B1")) * isOpositeColor("B1");
        if (res > 0) {
            return 1.;
        } else {
            return 0.;
        }
    }

    public Double parallelalCheck() {
        Double res;
        res = (isQueen("C4") + isRook("C4")) * isOpositeColor("C4") * isEmpty("C3");
        res += (isQueen("C3") + isRook("C3")) * isOpositeColor("C3");
        res += (isQueen("A2") + isRook("A2")) * isOpositeColor("A2") * isEmpty("B2");
        res += (isQueen("B2") + isRook("B2")) * isOpositeColor("B2");
        res += (isQueen("D2") + isRook("D2")) * isOpositeColor("D2");
        res += (isQueen("E2") + isRook("E2")) * isOpositeColor("E2") * isEmpty("D2");
        if (res > 0) {
            return 1.;
        } else {
            return 0.;
        }
    }

    public Double knigthCheck() {
        Double res;
        res = isKnigth("B4") * isOpositeColor("B4");
        res += isKnigth("D4") * isOpositeColor("D4");
        res += isKnigth("A3") * isOpositeColor("A3");
        res += isKnigth("A1") * isOpositeColor("A1");
        res += isKnigth("E1") * isOpositeColor("E1");
        res += isKnigth("E3") * isOpositeColor("E3");
        if (res > 0) {
            return 1.;
        } else {
            return 0.;
        }
    }

    public Double IllegalMove() {
        String chkPiece = "";
        if ((knigthCheck() > 0)) {
            chkPiece = find("k");
            return 1.;
        } else if (diagonalCheck() > 0) {
            chkPiece = find("d");
            return 1.;
        } else if (parallelalCheck() > 0) {
            chkPiece = find("p");
            return 1.;
        } else {
            return 0.;
        }
    }

    private String find(String chkType) {
        if (null != chkType) {
            switch (chkType) {
                case "p":
                    for (int i = 0; i < this.pieces.size(); i++) {
                        if (pieces.get(i) == "Q") {
                            return (pieces.get(i) + this.positionMap.get(i));
                        } else if (pieces.get(i) == "R") {
                            return (pieces.get(i) + this.positionMap.get(i));
                        }
                    }
                    break;
                case "k":
                    for (int i = 0; i < this.pieces.size(); i++) {
                        if (pieces.get(i) == "K") {
                            return (pieces.get(i) + this.positionMap.get(i));
                        }
                    }
                    break;
                case "d":
                    for (int i = 0; i < this.pieces.size(); i++) {
                        if (pieces.get(i) == "Q") {
                            return (pieces.get(i) + this.positionMap.get(i));
                        } else if (pieces.get(i) == "B") {
                            return (pieces.get(i) + this.positionMap.get(i));
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return "";
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

    public void randomBoard() {
        Collections.shuffle(pieces, RandomKann.getInstance());
    }

    private void generateInput() {
        for (String str : this.inputs.keySet()) {
            this.inputs.put(str, evaluate(str));
        }
    }

    @Override
    public void generateOutput() {
        Double d1 = this.intermediate.get("PCK");
        Double d2 = this.intermediate.get("DCK");
        Double d3 = this.intermediate.get("KCK");
        if (d1 + d2 + d3 > 0) {
            this.outputs.put("IM", 1.);
        } else {
            this.outputs.put("IM", 0.);
        }
    }

    @Override
    public void generateInputOutput() {
        this.randomBoard();
        this.generateInput();
        this.generateIntermediateConclusions();
        this.generateOutput();
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < pieces.size(); i++) {
            res += pieces.get(i) + positionMap.get(i) + " ";
        }
        return res;
    }

    private Double evaluateOutput(String str) {
        if (IllegalMove() > 0) {
            return 1.;
        } else {
            return 0.;
        }
    }

}
