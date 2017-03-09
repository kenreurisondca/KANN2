/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ufrn.kann2.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.shape.Shape;

/**
 *
 * @author kenreurison
 */
public class ReadFile {

    private String File;

    public ReadFile(String File) {
        this.File = File;
    }

    private String getContents() throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(File);
        BufferedReader txtReader = new BufferedReader(fr);
        String subsString;
        String res = "";
        while ((subsString = txtReader.readLine()) != null) {
            res += subsString + ".";
        }
        txtReader.close();
        fr.close();
        return res;
    }

    public List<String> getVocabulary() {
        List<String> res = new ArrayList<>();
        try {
            String contents = getContents();
            contents = contents.replace(" ", "");
            contents = contents.replace("\n", " ");
            contents = contents.replace(":", "");
            contents = contents.replace(",", " ");
            contents = contents.replace("-", " ");
            contents = contents.replace(".", " ");
            final String[] split = contents.split(" ");
            for (int i = 0; i < split.length; i++) {
                if (!res.contains(split[i])) {
                    res.add(split[i]);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ReadFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public static void main(String[] args) {
        ReadFile f = new ReadFile("src\\br\\com\\ufrn\\kann2\\resources\\xadrez.txt");
        List<String> vocabulary = f.getVocabulary();
        System.out.println(vocabulary.toString());

    }
}
