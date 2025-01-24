

public class MemorySpace {

    private LinkedList allocatedList;
    private LinkedList freeList;

    public MemorySpace(int maxSize) {
        allocatedList = new LinkedList();
        freeList = new LinkedList();
        freeList.addLast(new MemoryBlock(0, maxSize));
    }
    public int malloc(int length) {
     ListIterator free = freeList.iterator();
     while(free.hasNext())
     {
         MemoryBlock currentBlock = free.next();
         if(currentBlock.length == length)
         {
           int address = currentBlock.baseAddress;
           allocatedList.addLast(currentBlock);
           freeList.remove(currentBlock);
           return address;
         }
         if(currentBlock.length > length)
         {
           allocatedList.addLast(new MemoryBlock(currentBlock.baseAddress, length));
           int address = currentBlock.baseAddress;
           currentBlock.length -= length;
           currentBlock.baseAddress += length;
           return address;
         }
     }
        return -1;
    }

    public void free(int address) {
        if(allocatedList.getSize() == 0)
        {
            throw new IllegalArgumentException("index must be between 0 and size");
        }
        ListIterator allocated = allocatedList.iterator();
        while(allocated.hasNext())
        {
            MemoryBlock currentBlock = allocated.next();
            if(currentBlock.baseAddress == address)
            {
                freeList.addLast(currentBlock);
                allocatedList.remove(currentBlock);
            }
        }
    }
    public void defrag() {
        boolean meged;
        do{
            meged = false;
         ListIterator FreeQuter = freeList.iterator();  
         while(FreeQuter.hasNext())
         {
                MemoryBlock currentBlock = FreeQuter.next();
                ListIterator FreeInner = freeList.iterator();
                while(FreeInner.hasNext())
                {
                    MemoryBlock candidaterBlock = FreeInner.next();
                    if(currentBlock != candidaterBlock && currentBlock.baseAddress + currentBlock.length == candidaterBlock.baseAddress)
                    {
                        currentBlock.length += candidaterBlock.length;
                        freeList.remove(candidaterBlock);
                        meged = true;
                    }
                }
            }  
    }while(meged);
}

    public String toString() {
        return freeList.toString() + "\n" + allocatedList.toString();
    }
}
