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
}
