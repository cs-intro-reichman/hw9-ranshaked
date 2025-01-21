/**
 * Represents a managed memory space. The memory space manages a list of allocated 
 * memory blocks, and a list free memory blocks.
 */
public class MemorySpace {
    
    // A list of the memory blocks that are presently allocated
    private LinkedList allocatedList;

    // A list of memory blocks that are presently free
    private LinkedList freeList;

    /**
     * Constructs a new managed memory space of a given maximal size.
     * 
     * @param maxSize
     *            the size of the memory space to be managed
     */
    public MemorySpace(int maxSize) {
        // Initialize an empty list of allocated blocks.
        allocatedList = new LinkedList();
        // Initialize a free list containing a single block representing the entire memory.
        freeList = new LinkedList();
        freeList.addLast(new MemoryBlock(0, maxSize));
    }

    /**
     * Allocates a memory block of a requested length (in words). 
     * Returns the base address of the allocated block, or -1 if unable to allocate.
     */
    public int malloc(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Requested size must be greater than 0");
        }

        for (int i = 0; i < freeList.getSize(); i++) {
            MemoryBlock block = (MemoryBlock) freeList.getBlock(i);
            if (block.length >= length) {
                int allocatedAddress = block.baseAddress;

                // Create a new memory block for the allocation
                MemoryBlock allocatedBlock = new MemoryBlock(allocatedAddress, length);
                allocatedList.addLast(allocatedBlock);

                // Update the free block
                block.baseAddress += length;
                block.length -= length;

                // Remove the free block if its length becomes 0
                if (block.length == 0) {
                    freeList.remove(i);
                }
                return allocatedAddress;
            }
        }
        return -1; // Allocation failed
    }

    /**
     * Frees the memory block whose base address equals the given address.
     */
    public void free(int address) {
        MemoryBlock blockToFree = null;

        // Find the allocated block with the given address
        for (int i = 0; i < allocatedList.getSize(); i++) {
            MemoryBlock block = (MemoryBlock) allocatedList.getBlock(i);
            if (block.baseAddress == address) {
                blockToFree = block;
                allocatedList.remove(i);
                break;
            }
        }

        if (blockToFree == null) {
            throw new IllegalArgumentException("Invalid address: no allocated block found");
        }

        // Add the block to the free list
        freeList.addLast(blockToFree);

        // Perform defragmentation
        defrag();
    }

    /**
     * Performs defragmentation of this memory space.
     */
    public void defrag() {
        for (int i = 0; i < freeList.getSize() - 1; i++) {
            MemoryBlock current = (MemoryBlock) freeList.getBlock(i);
            MemoryBlock next = (MemoryBlock) freeList.getBlock(i + 1);

            if (current.baseAddress + current.length == next.baseAddress) {
                // Merge adjacent blocks
                current.length += next.length;
                freeList.remove(i + 1);
                i--; // Adjust index after removal
            }
        }
    }

    /**
     * Returns a string representation of the free list and allocated list for debugging purposes.
     */
    @Override
    public String toString() {
        return "Free List: " + freeList.toString() + "\nAllocated List: " + allocatedList.toString();
    }
}
