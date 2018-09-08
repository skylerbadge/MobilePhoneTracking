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
    public boolean isMember(Exchange ex) // check
    {
        return ell.isMember(ex);
    }
    public void Insert(Exchange ex)
    {
        ///System.out.println(ex.uid+"call");
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
        ///System.out.println(index+" lol "+ell.ithNode(index));
        if (ell.ithNode(index)==null)
            return null;
        return (Exchange)ell.ithNode(index).data;
    }
}
