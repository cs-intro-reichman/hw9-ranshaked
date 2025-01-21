/**
 * Represents a list of Nodes.
 */
public class LinkedList {

    private Node first; // pointer to the first element of this list
    private Node last;  // pointer to the last element of this list
    private int size;   // number of elements in this list

    /**
     * Constructs a new list.
     */
    public LinkedList() {
        first = null;
        last = null;
        size = 0;
    }

    public Node getFirst() {
        return this.first;
    }

    public Node getLast() {
        return this.last;
    }

    public int getSize() {
        return this.size;
    }

    public Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index must be between 0 and size");
        }
        Node currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    public void add(int index, MemoryBlock block) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Index must be between 0 and size");
        }
        Node newNode = new Node(block);
        if (index == 0) {
            newNode.next = first;
            first = newNode;
            if (size == 0) {
                last = newNode;
            }
        } else if (index == size) {
            last.next = newNode;
            last = newNode;
        } else {
            Node prevNode = getNode(index - 1);
            newNode.next = prevNode.next;
            prevNode.next = newNode;
        }
        size++;
    }

    public void addLast(MemoryBlock block) {
        add(size, block);
    }

    public void addFirst(MemoryBlock block) {
        add(0, block);
    }

    public MemoryBlock getBlock(int index) {
        return getNode(index).block;
    }

    public int indexOf(MemoryBlock block) {
        Node currentNode = first;
        for (int i = 0; i < size; i++) {
            if (currentNode.block.equals(block)) {
                return i;
            }
            currentNode = currentNode.next;
        }
        return -1;
    }

    public void remove(Node node) {
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        remove(indexOf(node.block));
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index must be between 0 and size");
        }
        if (index == 0) {
            first = first.next;
            if (size == 1) {
                last = null;
            }
        } else {
            Node prevNode = getNode(index - 1);
            prevNode.next = prevNode.next.next;
            if (index == size - 1) {
                last = prevNode;
            }
        }
        size--;
    }

    public void remove(MemoryBlock block) {
        int index = indexOf(block);
        if (index == -1) {
            throw new IllegalArgumentException("Block not found in the list");
        }
        remove(index);
    }

    public String toString() {
        Node currentNode = first;
        StringBuilder result = new StringBuilder();
        while (currentNode != null) {
            result.append(currentNode.block.toString()).append(" ");
            currentNode = currentNode.next;
        }
        return result.toString();
    }
}
