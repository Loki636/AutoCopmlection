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
public final class NodeLink {

    String word;
    Integer count;
    NodeLink Next;

    public NodeLink() {
        word = null;
        count = 0;
    }

    public NodeLink(String word, Integer count) {
        SetWord(word);
        SetCount(count);
    }

    public void SetWord(String word) {
        this.word = word;
    }

    public String GetWord() {
        return this.word;
    }

    public void SetCount(Integer count) {
        this.count = count;
    }

    public Integer GetCount() {
        return count;
    }

    public void SetNext(NodeLink node) {
        Next = node;
    }

    public NodeLink GetNext() {
        return Next;
    }
}
