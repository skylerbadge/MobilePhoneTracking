public class Exchange
{
    int uid;
    boolean root;
    MobilePhoneSet mobps;// Resident Set
    ExchangeList childll;
    Exchange parent;
    
    public Exchange(int number)
    {
        uid = number;
        root=false;
        mobps = new MobilePhoneSet();
        childll=new ExchangeList();
    }
    public void makeRoot()
    {
        root = true;
    }
    public Exchange parent()
    {
        return parent;
    }
    public int numChildren()
    {
        return childll.numChildren();
    }
    public Exchange child(int i)
    {
        return childll.ithChild(i);
    }
    public boolean isroot()
    {
        return root;
    }
    public RoutingMapTree subtree(int i)
    {
        if(i>=0 && i < numChildren())
        {
            return new RoutingMapTree(child(i));
        }
        else
            return null;
    }
    public MobilePhoneSet residentSet()
    {
        return mobps;
    }
    
    public void addMobile(MobilePhone mp)
    {
        mobps.Store(mp);
        return;
    }
    public void delMobile(MobilePhone mp)
    {
        mobps.Delete(mp);
        return;
    }
    public void addChild(Exchange a)
    {
        a.parent=this;
        childll.Insert(a);
        return;
    }
    
//    public static void main(String[] args) 
//    {
//        Exchange ex = new Exchange(123);
//        Exchange ex2 = new Exchange(456);
//        ex.addChild(ex2);
//        
//        System.out.println(ex.childll.numChildren());
//        System.out.println(ex.childll.ithChild(0).uid);
//        MobilePhone mob = null;
//        
//    }
}