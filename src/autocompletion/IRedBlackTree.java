/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autocompletion;

/**
 *
 * @author Евгений
 */
public interface IRedBlackTree<T> {
    /**
    * Добавить элемент в дерево
    * @param o
    */
    void add(T o);
    /**
     * Удалить элемент из дерева
     * @param o
     */
    boolean remove(T o);
    /**
     * Возвращает true, если элемент содержится в дереве
     * @param o
     */
    boolean contains(T o);
    
    /**
     * Добавляем слово с указанной частатой
     * @param o
     * @param count 
     */
    void add(T o,int count);
}
