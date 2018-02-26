package eg.edu.alexu.csd.filestructure.sort.cs34;

import eg.edu.alexu.csd.filestructure.sort.IHeap;
import eg.edu.alexu.csd.filestructure.sort.INode;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Binary heap implementation.
 *
 * @author H
 *
 */
public class Heap<T extends Comparable<T>> implements IHeap<T> {

    /**
     * The ArrayList that holds the nodes of the p
     */
    private ArrayList<INode<T>> array;

    /**
     * Default constructor.
     */
    public Heap() {
        array = new ArrayList<INode<T>>();
    }

    /**
     * Copy constructor.
     * @param arrayIn ArrayList assumed heapified.
     */
    public Heap(final ArrayList<INode<T>> arrayIn) {
        array = arrayIn;
    }

    /**
     * Constructor that takes any regular collection and
     * generates heap with its elements.
     * @param arrayIn
     */
    public Heap(final Collection<T> arrayIn) {
        build(arrayIn);
    }

    @Override
    public INode<T> getRoot() {
        if (array == null || array.size() == 0) {
            return null;
        }
        return array.get(0);
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public void heapify(INode<T> node) {
        if (node == null) {
            throw new IllegalArgumentException("Insertion of null.");
        } else {
            while (node.getParent() != null // Heapifying up.
                    && node.getValue().compareTo(
                            node.getParent().getValue()
                            ) > 0) { // Greater than parent
                INode<T> parent = node.getParent();
                swap(parent, node);
                node = parent;
            }

            while (node.getLeftChild() != null) { // Heapifying down.
                INode<T> higherChild = null;
                 // Find higher child.
                if (node.getRightChild() != null) {
                    higherChild =
                            node.getLeftChild().getValue().compareTo(
                                    node.getRightChild().getValue()
                            ) > 0 ? node.getLeftChild() : node.getRightChild();
                } else {
                    higherChild = node.getLeftChild();
                }

                if (node.getValue().compareTo(
                        higherChild.getValue()
                ) < 0) {
                    swap(higherChild, node);
                    node = higherChild;
                } else {
                    break;
                }
            }
        }
    }

    @Override
    public T extract() {
        if (array == null || array.size() == 0) {
            return null;
        }
        final INode<T> maxNode = array.get(0); // Always holds extremest value.
        if (maxNode == null) {
            return null;
        } else {
            int lastIndex = array.size() - 1;
            T returner = maxNode.getValue();
            swap(maxNode, array.get(lastIndex));
            array.remove(lastIndex--); // Size decreases due to removal.
            if (array.size() > 1) {
            	heapify(maxNode);
            }
            return returner;
        }
    }

    @Override
    public void insert(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Insertion of null.");
        } else {
            final int index = array.size();
            INode<T> node = new ArrayHeapNode<T>(array, index, element);
            array.add(node);
            heapify(node);
        }

    }

    @Override
    public void build(java.util.Collection<T> unordered) {
        if (unordered == null) {
            throw new IllegalArgumentException("Can't build heap with null.");
        } else {
            array = new ArrayList<INode<T>>();
            for (T element : unordered) {
                insert(element);
            }
        }

    }

    /**
     * Swapping algorithm for the values in nodes.
     * Simply swaps the values in the two nodes but keeps
     * the two nodes in place as they are position
     * holders and not value holders.
     * @param node1
     * @param node2
     */
    private void swap(INode<T> node1, INode<T> node2) {
        T swapper;
        swapper = node1.getValue();
        node1.setValue(node2.getValue());
        node2.setValue(swapper);
    }

}