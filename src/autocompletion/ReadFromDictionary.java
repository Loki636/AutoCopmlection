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
public class ReadFromDictionary implements ILoad {

    private static final Pattern NON_WORD_LETTERS_PATTERN = Pattern.compile("\\W+");
    private String PathToFile;
    private BufferedReader ReadFile;

    public ReadFromDictionary(String path) throws FileNotFoundException {
        ReadFile = new BufferedReader(new FileReader(path));

    }

    public String GetPath() {
        return PathToFile;
    }

    /*
     Загрузка в HashMap слов из словаря
     */
    @Override
    public HashMap<String, Integer> LoadValues() {
        HashMap<String, Integer> Elements = new HashMap<>();
        try {
            String singleLine;
            while ((singleLine = ReadFile.readLine()) != null) {
                processLine(singleLine, Elements);
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        return Elements;
    }

    /*
     Процесс разбития строки на слово и частату встречаемости.Добавляем элемент в HashMap
     */
    private void processLine(String singleLine, HashMap<String, Integer> Elements) {
        String[] words = NON_WORD_LETTERS_PATTERN.split(singleLine);
        try {
            final Integer count = new Integer(words[1]);
            Elements.put(words[0], count);
        } catch (NumberFormatException e) {
            System.out.println("Невозможно приобразовать в число");
        }
    }

}
