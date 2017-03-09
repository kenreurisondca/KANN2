/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.padrao;

import br.com.ufrn.kann2.util.RandomKann;
import br.com.ufrn.kann2.util.ReadFile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pesquisador
 */
public final class TabuleiroPattern extends Pattern {

    private Peça tabuleiro[][];

    public TabuleiroPattern(Peça[][] tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public TabuleiroPattern() {
        ReadFile f = new ReadFile("src\\br\\com\\ufrn\\kann2\\resources\\xadrez.txt");
        List<String> vocabulary = f.getVocabulary();
        for (String s : vocabulary) {
            if (!"DCK".equals(s) && !"KCK".equals(s) && !"PCK".equals(s) && !"IM".equals(s)) {
                inputs.put(s, 0.);
            }
        }
        gerarTabuleiro();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (tabuleiro[i][j] == null) {
                    
                }
            }
        }
    }

    public void gerarTabuleiro() {
        tabuleiro = new Peça[4][5];
        tabuleiro[1][2] = new Peça("Rei", true); //Rei Branco
        ArrayList<Peça> conjuntoPecas = gerarPecas();
        RandomKann rand = RandomKann.getInstance();
        while (!conjuntoPecas.isEmpty()) {
            Peça nova = conjuntoPecas.remove(0);
            int linha = rand.nextInt(4);
            int coluna = rand.nextInt(5);
            while (null != tabuleiro[linha][coluna] || (linha == 0 && coluna == 2)) {
                linha = rand.nextInt(4);
                coluna = rand.nextInt(5);
            }
            tabuleiro[linha][coluna] = nova;
        }
    }

    private double buscarPeca(String nome, int linha, int coluna) {
        if (tabuleiro[linha][coluna] == null) {
            return 0;
        } else if (tabuleiro[linha][coluna].getRotulo().equals(nome)) {
            return 1;
        }
        return 0;
    }

    private ArrayList<Peça> gerarPecas() {
        ArrayList<Peça> pecas = new ArrayList<>();
        pecas.add(new Peça("Queen", true));
        pecas.add(new Peça("Queen", false));
        pecas.add(new Peça("Knight", true));
        pecas.add(new Peça("Knight", false));
        pecas.add(new Peça("Bishop", true));
        pecas.add(new Peça("Bishop", false));
        pecas.add(new Peça("Rook", true));
        pecas.add(new Peça("Rook", false));

        return pecas;
    }

    private double queen(int linha, int coluna) {
        return (buscarPeca("Queen", linha, coluna));
    }

    private double rook(int linha, int coluna) {
        return (buscarPeca("Rook", linha, coluna));
    }

    private double bishop(int linha, int coluna) {
        return (buscarPeca("Bishop", linha, coluna));
    }

    private double knight(int linha, int coluna) {
        return (buscarPeca("Knight", linha, coluna));
    }

    private double oppositeColor(int linha, int coluna) {
        if (tabuleiro[linha][coluna] == null) {
            return 0;
        } else {
            if (!tabuleiro[linha][coluna].isCor()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private double isEmpty(int linha, int coluna) {
        if (tabuleiro[linha][coluna] == null) {
            return 1;
        } else {
            return 0;
        }
    }

    public double chequeDiagonal() {
        double b[] = new double[6];
        b[0] = ((queen(3, 0) + bishop(3, 0)) * oppositeColor(3, 0) * isEmpty(2, 1));
        b[1] = ((queen(3, 4) + bishop(3, 4)) * oppositeColor(3, 4) * isEmpty(2, 3));
        b[2] = ((queen(2, 1) + bishop(2, 1)) * oppositeColor(2, 1));
        b[3] = ((queen(2, 3) + bishop(2, 3)) * oppositeColor(2, 3));
        b[4] = ((queen(0, 1) + bishop(0, 1)) * oppositeColor(0, 1));
        b[5] = ((queen(0, 3) + bishop(0, 3)) * oppositeColor(0, 3));
        if ((b[0] + b[1] + b[2] + b[3] + b[4] + b[5]) > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public double chequeParalelo() {
        double b[] = new double[6];
        b[0] = ((queen(3, 2) + rook(3, 2)) * oppositeColor(3, 2) * isEmpty(2, 2));
        b[1] = ((queen(2, 2) + rook(2, 2)) * oppositeColor(2, 2));
        b[2] = ((queen(1, 0) + rook(1, 0)) * oppositeColor(1, 0) * isEmpty(1, 1));
        b[3] = ((queen(1, 1) + rook(1, 1)) * oppositeColor(1, 1));
        b[4] = ((queen(1, 3) + rook(1, 3)) * oppositeColor(1, 3));
        b[5] = ((queen(1, 4) + rook(1, 4)) * oppositeColor(1, 4) * isEmpty(1, 3));
        if ((b[0] + b[1] + b[2] + b[3] + b[4] + b[5]) > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public double chequeCavalo() {
        double b[] = new double[6];
        b[0] = (knight(3, 1) * oppositeColor(3, 1));
        b[1] = (knight(3, 3) * oppositeColor(3, 3));
        b[2] = (knight(2, 0) * oppositeColor(2, 0));
        b[3] = (knight(2, 4) * oppositeColor(2, 4));
        b[4] = (knight(0, 0) * oppositeColor(0, 0));
        b[5] = (knight(0, 4) * oppositeColor(0, 4));
        if ((b[0] + b[1] + b[2] + b[3] + b[4] + b[5]) > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public double movimentoIlegal() {
        if ((chequeCavalo() + chequeDiagonal() + chequeParalelo()) > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (tabuleiro[i][j] != null) {
                    s += "T[" + i + "][" + j + "] = " + tabuleiro[i][j].toString() + "\n";
                }
            }
        }
        return "\n" + s;
    }

    public double query(String rotulo) {
        int coluna = rotulo.charAt(1) - 65;
        int linha = rotulo.charAt(2) - 49;
        switch (rotulo.charAt(0)) {
            case 'Q':
                return queen(linha, coluna);
            case 'B':
                return bishop(linha, coluna);
            case 'K':
                return knight(linha, coluna);
            case 'O':
                coluna = rotulo.charAt(2) - 65;
                linha = rotulo.charAt(3) - 49;
                return oppositeColor(linha, coluna);
            case 'R':
                return rook(linha, coluna);
        }
        return -1;
    }

    @Override
    protected void generateIntermediateConclusions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void generateOutput() {

    }

}
