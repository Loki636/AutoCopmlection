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
public class Line {

    private int IndexEmpty;
    private String Line;

    public int GetIndexEmpty() {
        return IndexEmpty;
    }

    public void SetIndexEmpty(int index) {
        IndexEmpty = index;
    }

    public String GetString() {
        return Line.substring(IndexEmpty, Line.length() - 1);
    }

    public void SetLine(String line) {
        Line = line;
    }

    public String PartLine() {

        if (Line.lastIndexOf(" ") > 0) {
            String partLine = Line.substring(Line.lastIndexOf(" ") + 1);
            return partLine;
        } else {
            return GetLine();
        }
    }

    public String GetLine() {
        return Line;
    }

    public void FindLastEmpty() {
        SetIndexEmpty(GetLine().lastIndexOf(" "));
    }

    public void AddItem(String item) {
        if (GetLine().length() > 0) {
            SetLine(GetLine() + item);
        } else {
            SetLine(item);
        }
        FindLastEmpty();
    }

    public void RemoveItem() {

        if (GetLine().length() > 0) {
            SetLine(GetLine().substring(0, GetLine().length() - 1));
            FindLastEmpty();
        }
    }

    public void SetNewWord(String string) {
        SetLine(GetLine().substring(0, GetLine().lastIndexOf(PartLine())).concat(string + " "));
        FindLastEmpty();
        PartLine();
    }

    public Line() {
        SetLine("");
    }

    public void ReplaceString(String string) {
        if (GetIndexEmpty() > 0) {
            SetLine(GetLine().substring(0, IndexEmpty).concat(' '+string));
            FindLastEmpty();
        } else {
            if (GetIndexEmpty() > -1) {
                SetLine(' ' + string);
                FindLastEmpty();
            } else {
                SetLine(string);
                FindLastEmpty();
            }
        }
    }
}

