//Add exceptions for insert registered and delete not registered
public class MobilePhoneSet
{
    Myset mobset;

    public MobilePhoneSet()
    {
            mobset = new Myset();
    }

    public void Store(MobilePhone mob)
    {
            try
            {	
                mobset.Insert(mob);
            } catch(Exception e){}
    }

    public void Delete(MobilePhone mob)
    {
            try
            {	
                mobset.Delete(mob);
            } catch(Exception e){}
    }

    public boolean isEmpty()
    {
            return mobset.isEmpty();
    }

    public boolean isMember(MobilePhone mob)
    {
            return mobset.isMember(mob);
    }

    public MobilePhoneSet Union(MobilePhoneSet mps)
    {
            MobilePhoneSet t = new MobilePhoneSet();
            t.mobset = mobset.Union(mps.mobset);
            return t;
    }

    public MobilePhoneSet Intersection(MobilePhoneSet mps)
    {
        MobilePhoneSet t = new MobilePhoneSet();
        t.mobset = mobset.Intersection(mps.mobset);
            return t;
    }
    public MobilePhone getMobilePhone(int uid)
    {
        LinkedList mll = mobset.setobj;
        Node ptr = mll.head;
        ///System.out.println(ptr);
        if(ptr==null)
            return null;
        ///System.out.println(ptr+" "+((MobilePhone)ptr.data).id);
        while(ptr!=null)
        {
            ///System.out.println(((MobilePhone)ptr.data).id);
            ///System.out.println(ptr+" "+((MobilePhone)ptr.data).id);
            if(((MobilePhone)ptr.data).id==uid)
                return ((MobilePhone)ptr.data);
            ptr=ptr.next;
        }
        return null;
    }
    public String displaymob()
    {
        ///System.out.println("hey");
        LinkedList ll = mobset.setobj;
        Node ptr = ll.head;
        String str="";
        while(ptr!=null)
        {   
            if(((MobilePhone)ptr.data).status)
                str=Integer.toString(((MobilePhone)ptr.data).id)+", "+str;
            ptr = ptr.next;
        }
        if (str.equals(""))
            return str;
        return str.substring(0,str.length()-2);
    }
}