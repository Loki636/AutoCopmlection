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
public class SortedList {

    NodeLink item;

    public SortedList() {
        item = null;
    }

    public boolean isEmpty() {
        return item == null;
    }

    public void Insert(String word, Integer count) {
        NodeLink node = new NodeLink(word, count);
        NodeLink prev = null;
        NodeLink current = item;
        while (current != null && (current.GetCount().compareTo(count) > 0)) {
            prev = current;
            current = current.GetNext();
        }
        if (prev == null) {
            item = node;
        } else {
            prev.Next = node;

        }
        node.Next = current;
    }
}
