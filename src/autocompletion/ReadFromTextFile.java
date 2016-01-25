/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autocompletion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 *
 * @author Евгений
 */
public class ReadFromTextFile implements ILoad {

    private static final Pattern NON_WORD_LETTERS_PATTERN = Pattern.compile("\\W+");
    private String PathToFile;
    private BufferedReader ReadFile;

    public ReadFromTextFile(String path) throws FileNotFoundException {

        ReadFile = new BufferedReader(new FileReader(path));
    }

    public String GetPath() {
        return PathToFile;
    }
    /*
     Загрузка в HashMap слов из текстового файла
     */

    @Override
    public HashMap<String, Integer> LoadValues() {

        HashMap<String, Integer> elements = new HashMap<>();
        try {
            String singleLine;
            while ((singleLine = ReadFile.readLine()) != null) {
                String[] words = NON_WORD_LETTERS_PATTERN.split(singleLine);
                addWords(words, elements);
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        return elements;
    }

    /*
     Дабавление слова в словарь,если такого слова нет в нем.Иначе добавляем +1 встречаемости слова
     */
    private void addWords(String[] words, HashMap<String, Integer> elements) {
        for (String word : words) {
            if (word != "" && word.length()>1) {
                if (elements.containsKey(word)) {
                    elements.put(word, elements.get(word) + 1);
                } else {
                    elements.put(word, 1);
                }
            }
        }
    }
}
