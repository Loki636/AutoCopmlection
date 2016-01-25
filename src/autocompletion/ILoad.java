/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autocompletion;

import java.util.HashMap;

/**
 *
 * @author Евгений
 */

   /*Интерфейс реализующий загрузку в дерево RedBlackTree*/
public interface ILoad {
    /*Передаем данныем в словарь*/
    
   HashMap<String,Integer> LoadValues();
    
}
