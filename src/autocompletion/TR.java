/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autocompletion;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Евгений
 *
 * @param <String>
 */
public class TR implements IRedBlackTree<String>, Iterable<String>, Iterator<String> {

    /**
     * Перечисление цветов узла дерева.
     */
    enum NodeColor {
        
        RED,
        BLACK,
        NONE
    }

    /**
     * Класс реализующий узел дерева.
     */
    public class Node {

        /**
         * Значение узла дерева.
         */
        private String Word;
        private Integer CountUsedWord;
        /**
         * Цвет узла.
         */
        private NodeColor color;
        /**
         * Родительский узел.
         */
        private Node parent;
        /**
         * Левый дочерниый узел.
         */
        private Node left;
        /**
         * Правый дочерний узел.
         */
        private Node right;

        /**
         * Конструктор по-умолчанию.
         */
        public Node() {
            Word = null;
            color = NodeColor.NONE;
            parent = null;
            left = null;
            right = null;
            CountUsedWord = 0;
        }
        
        public void SetCountUsedWord(int Value) {
            CountUsedWord = Value;
        }

        /**
         * Конструктор с параметрами, позволящими задать цвет и значение узла.
         *
         * @param word - значение, которое будет сохранено в узле.
         * @param color - цвет узла.
         */
        public Node(String word, NodeColor color) {
            Word = word;
            this.color = color;
            parent = _nil;
            left = _nil;
            right = _nil;
            CountUsedWord = 0;
        }

        /**
         * Конструктор копий.
         *
         * @param node - другой узел.
         */
        public Node(Node node) {
            Word = node.Word;
            color = node.color;
            parent = node.parent;
            left = node.left;
            right = node.right;
            CountUsedWord = node.CountUsedWord;
        }
        
        public boolean isFree() {
            return Word == null;
        }
        
        public boolean isLeftFree() {
            return left == null || left == _nil;
        }
        
        public boolean isRightFree() {
            return right == null || right == _nil;
        }
        
        public boolean isParentFree() {
            return parent == null || parent == _nil;
        }
        
        public String getValue() {
            return Word;
        }
        
        public void setValue(String value) {
            Word = value;
        }
        
        public Node getParent() {
            return parent;
        }
        
        public void setParent(Node node) {
            parent = node;
        }
        
        public Node getLeft() {
            return left;
        }
        
        public void setLeft(Node node) {
            left = node;
            if (left != null && left != _nil) {
                left.parent = this;
            }
        }
        
        public Node getRight() {
            return right;
        }
        
        public void setRight(Node node) {
            right = node;
            if (right != null && right != _nil) {
                right.parent = this;
            }
        }
        
        public boolean isBlack() {
            return color == NodeColor.BLACK;
        }
        
        public void makeBlack() {
            color = NodeColor.BLACK;
        }
        
        public boolean isRed() {
            return color == NodeColor.RED;
        }
        
        public void makeRed() {
            color = NodeColor.RED;
        }
        
        public NodeColor getColor() {
            return color;
        }
        
        public void setColor(NodeColor color) {
            this.color = color;
        }
        
        public Integer getCountUsedWord() {
            return this.CountUsedWord;
        }

        /**
         * Возвращет "дедушку" узла дерева.
         */
        public Node getGrandfather() {
            if (parent != null && parent != _nil) {
                return parent.parent;
            }
            return null;
        }

        /**
         * Возвращает "дядю" узла дерева.
         */
        public Node getUncle() {
            Node grand = getGrandfather();
            if (grand != null) {
                if (grand.left == parent) {
                    return grand.right;
                } else if (grand.right == parent) {
                    return grand.left;
                }
            }
            return null;
        }

        /**
         * Возвращает следующий по значению узел дерева.
         */
        public Node getSuccessor() {
            Node temp = null;
            Node node = this;
            if (!node.isRightFree()) {
                temp = node.getRight();
                while (!temp.isLeftFree()) {
                    temp = temp.getLeft();
                }
                return temp;
            }
            temp = node.getParent();
            while (temp != _nil && node == temp.getRight()) {
                node = temp;
                temp = temp.getParent();
            }
            return temp;
        }
        
        public String getColorName() {
            
            if (isBlack()) {
                return (String) "B";
            } else {
                return (String) "R";
            }
        }
        
    }

    /**
     * Корень дерева.
     */
    private Node _root;
    /**
     * Ограничитель, который обозначает нулевую ссылку.
     */
    private Node _nil;

    /**
     * Ссылка на элемент на который указывает итератор.
     */
    private Node _current;

    /**
     * Флаг удаления элемента через итератор, необходимый для того, чтобы
     * корректно работали {@link Iterator#hasNext()} и {@link Iterator#next()}
     */
    private boolean _isRemoved;

    /**
     * Конструктор по-умолчанию.
     */
    public TR() {
        _root = new Node();
        _nil = new Node();
        _nil.color = NodeColor.BLACK;
        _root.parent = _nil;
        _root.right = _nil;
        _root.left = _nil;
        _isRemoved = false;
    }

    /**
     * Статический метод, осуществляюший левый поворот дерева tree относительно
     * узла node.
     *
     * @param tree - дерево.
     * @param node - узел, относительно которого осущетвляется левый поворот.
     */
    private static void leftRotate(TR tree, TR.Node node) {
        TR.Node nodeParent = node.getParent();
        TR.Node nodeRight = node.getRight();
        if (nodeParent != tree._nil) {
            if (nodeParent.getLeft() == node) {
                nodeParent.setLeft(nodeRight);
            } else {
                nodeParent.setRight(nodeRight);
            }
        } else {
            tree._root = nodeRight;
            tree._root.setParent(tree._nil);
        }
        node.setRight(nodeRight.getLeft());
        nodeRight.setLeft(node);
    }

    /**
     * Статический метод, осуществляюший правый поворот дерева tree относительно
     * узла node.
     *
     *
     */
    private static void rightRotate(TR tree, TR.Node node) {
        TR.Node nodeParent = node.getParent();
        TR.Node nodeLeft = node.getLeft();
        if (nodeParent != tree._nil) {
            if (nodeParent.getLeft() == node) {
                nodeParent.setLeft(nodeLeft);
            } else {
                nodeParent.setRight(nodeLeft);
            }
        } else {
            tree._root = nodeLeft;
            tree._root.setParent(tree._nil);
        }
        node.setLeft(nodeLeft.getRight());
        nodeLeft.setRight(node);
    }

    /**
     * Печать дерева.
     *
     * @param tree - дерево.
     */
    public static void printTree(TR tree) {
        ArrayList<TR.Node> nodes = new ArrayList<TR.Node>();
        nodes.add(0, tree._root);
        printNodes(tree, nodes);
    }

    /**
     * Печать информации об узле дерева.
     *
     * @param tree - ссылка на дерево.
     * @param nodes - список узлов на уровне дерева.
     */
    private static void printNodes(TR tree, ArrayList<TR.Node> nodes) {
        int childsCounter = 0;
        int nodesAmount = nodes.size();
        ArrayList<TR.Node> childs = new ArrayList<TR.Node>();
        
        for (int i = 0; i < nodesAmount; i++) {
            if (nodes.get(i) != null && nodes.get(i) != tree._nil) {
                System.out.print("(" + nodes.get(i).getValue().toString() + "," + nodes.get(i).getColorName() + ")");
                if (!nodes.get(i).isLeftFree()) {
                    childs.add(nodes.get(i).getLeft());
                    childsCounter++;
                } else {
                    childs.add(null);
                }
                if (!nodes.get(i).isRightFree()) {
                    childs.add(nodes.get(i).getRight());
                    childsCounter++;
                } else {
                    childs.add(null);
                }
            } else {
                System.out.print("(nil)");
            }
        }
        System.out.print("\n");
        
        if (childsCounter != 0) {
            printNodes(tree, childs);
        }
    }

    /**
     * Реализация метода добавления элемента дарева. На основе добавляемого
     * значения создается узел дерева типа {@link Node} красного цвета.
     *
     * @param o - значение типа {@link Comparable} для вставки в дерево.
     */
    @Override
    public void add(String o) {
        
        Node object = FindObject(o);
        if (object == null) {
            Node node = _root, temp = _nil;
            Node newNode = new Node(o, NodeColor.RED);
            int CountUsedWord=1;
            newNode.SetCountUsedWord(CountUsedWord);
            while (node != null && node != _nil && !node.isFree()) {
                temp = node;
                if (newNode.getValue().compareTo(o) < 0) {
                    node = node.getLeft();
                } else {
                    node = node.getRight();
                }
            }
            newNode.setParent(temp);
            if (temp == _nil) {
                _root.setValue(newNode.getValue());
            } else {
                if (newNode.getValue().compareTo(temp.getValue()) < 0) {
                    temp.setLeft(newNode);
                } else {
                    temp.setRight(newNode);
                }
            }
            newNode.setLeft(_nil);
            newNode.setRight(_nil);
            fixInsert(newNode);
        }
        else
        {
            object.SetCountUsedWord(object.getCountUsedWord()+1);
        }
        
    }
    
    @Override
    public void add(String o, int count) {
        
        Node object = FindObject(o);
        if (object == null) {
            Node node = _root, temp = _nil;
            Node newNode = new Node(o, NodeColor.RED);
            newNode.SetCountUsedWord(count);
            while (node != null && node != _nil && !node.isFree()) {
                temp = node;
                if (newNode.getValue().compareTo(o) < 0) {
                    node = node.getLeft();
                } else {
                    node = node.getRight();
                }
            }
            newNode.setParent(temp);
            if (temp == _nil) {
                _root.setValue(newNode.getValue());
                _root.SetCountUsedWord(newNode.getCountUsedWord());
            } else {
                if (newNode.getValue().compareTo(temp.getValue()) < 0) {
                    temp.setLeft(newNode);
                } else {
                    temp.setRight(newNode);
                }
            }
            newNode.setLeft(_nil);
            newNode.setRight(_nil);
            fixInsert(newNode);
        } 
        else {
            object.SetCountUsedWord(count);
        }
    }

    /**
     * Исправление древа для сохранения свойств красно-черного дерева.
     *
     * @param node - добавленный узел.
     */
    private void fixInsert(Node node) {
        Node temp;
        while (!node.isParentFree() && node.getParent().isRed()) {
            if (node.getParent() == node.getGrandfather().getLeft()) {
                temp = node.getGrandfather().getRight();
                if (temp.isRed()) {
                    temp.makeBlack();
                    node.getParent().makeBlack();
                    node.getGrandfather().makeRed();
                    node = node.getGrandfather();
                } else {
                    if (node == node.getParent().getRight()) {
                        node = node.getParent();
                        leftRotate(this, node);
                    }
                    node.getParent().makeBlack();
                    node.getGrandfather().makeRed();
                    rightRotate(this, node.getGrandfather());
                }
            } else {
                temp = node.getGrandfather().getLeft();
                if (temp.isRed()) {
                    temp.makeBlack();
                    node.getParent().makeBlack();
                    node.getGrandfather().makeRed();
                    node = node.getGrandfather();
                } else {
                    if (node == node.getParent().getLeft()) {
                        node = node.getParent();
                        rightRotate(this, node);
                    }
                    node.getParent().makeBlack();
                    node.getGrandfather().makeRed();
                    leftRotate(this, node.getGrandfather());
                }
            }
        }
        _root.makeBlack();
    }

    /**
     * Реализация удаления элемента дерева.
     *
     * @param o - значение типа {@link Comparable} для удаления из дерева.
     * @return true - если элемент был удален; false - если элемента в дереве
     * нет и удаление его невозможно.
     */
    @Override
    public boolean remove(String o) {
        return remove(findNode(o));
    }

    /**
     *
     */
    private boolean remove(Node node) {
        Node temp = _nil, successor = _nil;
        
        if (node == null || node == _nil) {
            return false;
        }
        
        if (node.isLeftFree() || node.isRightFree()) {
            successor = node;
        } else {
            successor = node.getSuccessor();
        }
        
        if (!successor.isLeftFree()) {
            temp = successor.getLeft();
        } else {
            temp = successor.getRight();
        }
        temp.setParent(successor.getParent());
        
        if (successor.isParentFree()) {
            _root = temp;
        } else if (successor == successor.getParent().getLeft()) {
            successor.getParent().setLeft(temp);
        } else {
            successor.getParent().setRight(temp);
        }
        
        if (successor != node) {
            node.setValue(successor.getValue());
        }
        if (successor.isBlack()) {
            fixRemove(temp);
        }
        return true;
    }

    /**
     * Исправляет дерево после удаления элемента для сохранения красно-черных
     * свойств дерева.
     *
     * @param node - значение относительно которого необходимо производить
     * исправление дерева.
     */
    private void fixRemove(Node node) {
        Node temp;
        while (node != _root && node.isBlack()) {
            if (node == node.getParent().getLeft()) {
                temp = node.getParent().getRight();
                if (temp.isRed()) {
                    temp.makeBlack();
                    node.getParent().makeRed();
                    leftRotate(this, node.getParent());
                    temp = node.getParent().getRight();
                }
                if (temp.getLeft().isBlack() && temp.getRight().isBlack()) {
                    temp.makeRed();
                    node = node.getParent();
                } else {
                    if (temp.getRight().isBlack()) {
                        temp.getLeft().makeBlack();
                        temp.makeRed();
                        rightRotate(this, temp);
                        temp = node.getParent().getRight();
                    }
                    temp.setColor(node.getParent().getColor());
                    node.getParent().makeBlack();
                    temp.getRight().makeBlack();
                    leftRotate(this, node.getParent());
                    node = _root;
                }
            } else {
                temp = node.getParent().getLeft();
                if (temp.isRed()) {
                    temp.makeBlack();
                    node.getParent().makeRed();
                    rightRotate(this, node.getParent());
                    temp = node.getParent().getLeft();
                }
                if (temp.getLeft().isBlack() && temp.getRight().isBlack()) {
                    temp.makeRed();
                    node = node.getParent();
                } else {
                    if (temp.getLeft().isBlack()) {
                        temp.getRight().makeBlack();
                        temp.makeRed();
                        leftRotate(this, temp);
                        temp = node.getParent().getLeft();
                    }
                    temp.setColor(node.getParent().getColor());
                    node.getParent().makeBlack();
                    temp.getLeft().makeBlack();
                    rightRotate(this, node.getParent());
                    node = _root;
                }
            }
        }
        node.makeBlack();
    }

    /**
     * Реализует функцию проверки на содержание элемента в дереве.
     *
     * @param o - значение типа {@link Comparable} для поиска в дерева.
     * @return true - если элемент найден; false - если элемент не найда.
     */
    @Override
    public boolean contains(String o) {
        return (findNode(o) != _nil);
    }

    /**
     * Поиск узла дерева со значением o.
     *
     * @param o - значение типа {@link Comparable} для поиска в дерева.
     * @return узел дерева; если не найден - возвращает {@value #_nil}
     */
    public Node findNode(String o) {
        Node node = _root;
        while (node != null && node != _nil && node.getValue().compareTo(o) != 0) {
            if (node.getValue().compareTo(o) > 0) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }
        return node;
    }

    /**
     * Метод для получения первого(наименьшего) элемента структуры.
     *
     * @return наименьший элемент дерева
     */
    private Node first() {
        Node node = _root;
        while (node.getLeft() != null && node.getLeft() != _nil) {
            if (!node.isLeftFree()) {
                node = node.getLeft();
            }
        }
        return node;
    }
    
    @Override
    public Iterator<String> iterator() {
        _current = null;
        _isRemoved = false;
        return this;
    }
    
    @Override
    public boolean hasNext() {
        if (_current != null) {
            if (!_isRemoved) {
                TR.Node node = _current.getSuccessor();
                return (node != null && node != _nil);
            }
            return (_current != null && _current != _nil);
        } else {
            return (_root != null && !_root.isFree());
        }
    }
    
    @Override
    public String next() {
        if (_current != null) {
            if (!_isRemoved) {
                _current = _current.getSuccessor();
            } else {
                _isRemoved = false;
            }
        } else {
            _current = first();
        }
        if (_current == null || _current == _nil) {
            throw new NoSuchElementException();
        }
        return _current.getValue();
    }
    
    @Override
    public void remove() {
        if (_current != null && !_isRemoved) {
            remove(_current);
            _isRemoved = true;
        } else {
            throw new IllegalStateException();
        }
    }
    
    public List<Node> FindAllContains(String word) {
        Node root = _root;
        if (_root != null) {
            ArrayList<Node> WriteNote = new ArrayList<>();
            if (EqualsNodeWord(_root, word)) {
                WriteNote.add(_root);
            }
            FindAllContains(word, _root, WriteNote);
            return WriteNote;
        } else {
            return null;
        }
    }
    
    private void FindAllContains(String word, Node _root, ArrayList<Node> WriteList) {
        if (_root != null) {
            if (EqualsNodeWord(_root, word)) {
                WriteList.add(_root);
            }
            if (_root.left != null && _root.left.getValue() != null) {
                FindAllContains(word, _root.left, WriteList);
            }
            if (_root.right != null && _root.right.getValue() != null) {
                FindAllContains(word, _root.right, WriteList);
            }
        }
    }
    
    private boolean EqualsNodeWord(Node node, String word) {
        
        if (node != null) {
            if ((node.getValue()).toLowerCase().compareTo(word) > 1) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    /**
     * Ищем определенное слово
     * @param O
     * @return 
     */
    public Node FindObject(String O) {
        Node object = null;
        return FindObject(O, object, _root);
    }
    
    private Node FindObject(String O, Node object, Node root) {
        if (root != null) {
            if (root.getValue() != null && root.getValue().equals(O)) {
                object = root;
                
            } else if (root.left != null && root.left.getValue() != null) {
                object = FindObject(O, object, root.left);
                
            } else if (root.right != null && root.right.getValue() != null) {
                object = FindObject(O, object, root.right);
            }
        }
        return object;
    }
    
}
