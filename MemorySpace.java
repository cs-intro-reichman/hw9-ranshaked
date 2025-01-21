public class MemorySpace {

    private LinkedList allocatedList;
    private LinkedList freeList;

    public MemorySpace(int maxSize) {
        allocatedList = new LinkedList();
        freeList = new LinkedList();
        freeList.addLast(new MemoryBlock(0, maxSize));
    }

    public int malloc(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Requested size must be greater than 0");
        }
        for (int i = 0; i < freeList.getSize(); i++) {
            MemoryBlock block = freeList.getBlock(i);
            if (block.length >= length) {
                int allocatedAddress = block.baseAddress;
                MemoryBlock allocatedBlock = new MemoryBlock(allocatedAddress, length);
                allocatedList.addLast(allocatedBlock);
                block.baseAddress += length;
                block.length -= length;
                if (block.length == 0) {
                    freeList.remove(i);
                }
                return allocatedAddress;
            }
        }
        return -1;
    }

    public void free(int address) {
        MemoryBlock blockToFree = null;
        for (int i = 0; i < allocatedList.getSize(); i++) {
            MemoryBlock block = allocatedList.getBlock(i);
            if (block.baseAddress == address) {
                blockToFree = block;
                allocatedList.remove(i);
                break;
            }
        }
        if (blockToFree == null) {
            throw new IllegalArgumentException("Invalid address: no allocated block found");
        }
        freeList.addLast(blockToFree);
        defrag();
    }

    public void defrag() {
        for (int i = 0; i < freeList.getSize() - 1; i++) {
            MemoryBlock current = freeList.getBlock(i);
            MemoryBlock next = freeList.getBlock(i + 1);
            if (current.baseAddress + current.length == next.baseAddress) {
                current.length += next.length;
                freeList.remove(i + 1);
                i--;
            }
        }
    }

    public String toString() {
        return "Free List: " + freeList.toString() + "\nAllocated List: " + allocatedList.toString();
    }
}
