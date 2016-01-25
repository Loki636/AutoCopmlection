/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autocompletion;

import java.util.TreeSet;

/**
 *
 * @author Евгений
 */
public interface IGetSubStrings {
    /*
    Метод возвращает слова из словаря
    */
TreeSet<String> GetAllSubString(String substring);
}
