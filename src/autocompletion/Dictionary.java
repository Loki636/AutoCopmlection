/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autocompletion;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Евгений
 */
public class Dictionary implements IGetSubStrings {

    public RedBlackTree tree;
    private ILoad loader;

    public Dictionary() {
        tree = new RedBlackTree();
        loader = null;
    }

    public void SetLoader(ILoad loader) throws Exception {
        if (loader != null) {
            this.loader = loader;
            SetLoadValues();
        } else {
            throw new Exception();
        }
    }

    private void SetLoadValues() {
        Set<Map.Entry<String, Integer>> Set = loader.LoadValues().entrySet();
        Set.stream().forEach((Map.Entry<String, Integer> element) -> {
            tree.put(element.getKey(), element.getValue());
        });
    }

    @Override
    public TreeSet<String> GetAllSubString(String substring) {

        TreeSet<String> GetAllSubWords = new TreeSet<String>();
        GetAllSubWords = tree.GetAllStrings(substring);
        return GetAllSubWords;
    }

}
