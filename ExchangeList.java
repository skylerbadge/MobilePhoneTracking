public class ExchangeList
{
    LinkedList ell;
    
    public ExchangeList()
    {
        ell = new LinkedList();
    }
    public boolean isEmpty()
    {
        return ell.isEmpty();
    }
    public boolean isMember(Exchange ex)
    {
        return ell.isMember(ex);
    }
    public void Insert(Exchange ex)
    {
        ell.addAtTail(ex);
    }
    public void Delete(Exchange ex) throws Exception
    {
            if(ell.isMember(ex))
                    ell.deleteAtIndex(ell.findob(ex));
            else
                    throw new Exception();
    }
    public int numChildren()
    {
        return ell.getSize();
    }
    
    public Exchange ithChild(int index)
    {
        if (ell.ithNode(index)==null)
            return null;
        return (Exchange)ell.ithNode(index).data;
    }
    public void InsertRev(Exchange ex)
    {
        ell.addAtHead(ex);
    }
    public ExchangeList concat(ExchangeList newel)
    {
        ExchangeList join = new ExchangeList();
        Node ptr = new Node();
        ptr = ell.head;
        while(ptr!=null)
        {
            join.Insert(((Exchange)ptr.data)); 
            ptr=ptr.next;
        }
        ptr = newel.ell.head;
        while(ptr!=null)
        {
            join.Insert(((Exchange)ptr.data)); 
            ptr=ptr.next;
        }
        return join;
    }
}
