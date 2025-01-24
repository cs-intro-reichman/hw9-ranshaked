
public class LinkedList {

    private Node first; // pointer to the first element of this list
    private Node last;  // pointer to the last element of this list
    private int size;   // number of elements in this list

    /**
     * Constructs a new list.
     */
    public LinkedList() {
        first = null;
        last = first;
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
      if(index < 0 || index > size)
      {
        throw new IllegalArgumentException("index must be between 0 and size");
      }
        Node currentNode = first;
        while (index > 0) {
            if(currentNode == null)
            {
              return  currentNode;
            }
            currentNode = currentNode.next;
            index--;
        }
        return currentNode;
      }

    public void add(int index, MemoryBlock block) {
        Node newNode = new Node(block);
        if(index == 0)
        {
            newNode.next = first;   
            first = newNode;
        }
        if(index == size)
        {
          if(last != null)
          {
            last.next = newNode;
          }
          last = newNode;
        }
        if(index != 0 && index != size)
        {
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
        if(this.first == null)
        {
          throw new IllegalArgumentException("index must be between 0 and size");
        }
        return getNode(index).block;
    }
    public int indexOf(MemoryBlock block) {
        for (int i = 0; i < size; i++) {
            if (getNode(i).block==block) {
                return i;
            }
        }
        return -1;
    }
    public void remove(Node node) {
        if (first == node) {
            first = first.next;
            if (first == null)
             {
                last = first;
                size --;
            }
            for(int i = 1 ; i < size ; i++)
            {
                Node currentNode = getNode(i);
                if(currentNode == node)
                {
                    Node prevNode = getNode(i - 1);
                    if(currentNode == last)
                    {
                        last = prevNode;
                    }
                    prevNode.next = currentNode.next;
                    currentNode.next = null;
                    size--;
                    break;
                }
            }
        }
    }
    public void remove(int index) {
        remove(getNode(index));
    }

    public void remove(MemoryBlock block) {
       remove(getNode(indexOf(block)));
    }

    public ListIterator iterator() {
        return new ListIterator(first);
    }

    public String toString() {
       StringBuilder sb = new StringBuilder("");
       ListIterator ls = this.iterator();
       while(ls.hasNext())
       {
        sb.append(ls.next() + " ");
       }
       return sb.toString();
     
    }
}