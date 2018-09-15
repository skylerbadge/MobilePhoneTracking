// for moodle with String return

import java.util.Scanner;

public class RoutingMapTree
{
    Exchange root;
    
    public RoutingMapTree()
    {
        root = new Exchange(0);
        root.makeRoot();
    }
    public RoutingMapTree(Exchange a)//subtree
    {
        root = a;
    }
    public boolean containsNode(Exchange a)
    {
        if (a.equals(root))
        {
            return true;
        }
        else
        {
            for(int i = 0; i<root.numChildren();i++)
                if(root.subtree(i).containsNode(a))
                    return true;                    
        }
        return false;
    }
    public void switchOn(MobilePhone a, Exchange b)throws Exception
    {
        if (!a.status())
        {
            
            b.addMobile(a);
            a.base=b;
            while(!b.isroot())
            {
                b=b.parent();
                b.addMobile(a);
            }
            a.switchOn();
        }
        else
            throw new Exception("Mobile is already on");
    }
    public void switchOff(MobilePhone a)throws Exception
    {
        if (a.status())
        {
            a.switchOff();
//          delete mobile phone
//          Exchange b = a.base;
//          
//            b.delMobile(a);
//            while(!b.isroot())
//            {
//                b=b.parent();
//                b.delMobile(a);
//            }
        }
        else
            throw new Exception("Mobile is already off");            
    }
    public void deleteMob(MobilePhone a)
    {
        Exchange b = a.base;
        b.delMobile(a);
        while(!b.isroot())
        {
            b=b.parent();
            b.delMobile(a);
        }
    }
    public Exchange getExchange(int id)
    {
        Exchange ptr = root;
        if(root.uid==id)
            return root;
        else
        {
            for(int i = 0; i<root.numChildren();i++)
            {   ptr = root.subtree(i).getExchange(id);
                if(ptr!=null)
                    return ptr;
            }
        }
        return null;
    }
    public Exchange print()//for debugging
    {
        Exchange ptr = root;
        System.out.println(ptr.uid);
        System.out.println(ptr.root);
        {
            for(int i = 0; i<root.numChildren();i++)
            {   ptr = root.subtree(i).print();
            }
        }
        return null;
    }
    
//    ASSIGNMENT 2 
    
    public Exchange findPhone(MobilePhone m) throws Exception
    {
        if(!root.mobps.isMember(m))
        {
            throw new Exception("Mobile Phone not registered");
        }
        if(!root.mobps.getMobilePhone(m.id).status())
        {
            throw new Exception("Mobile Phone is switched off");
        }
        return root.mobps.getMobilePhone(m.id).base;
    }
    
    public Exchange lowestRouter(Exchange a, Exchange b) throws Exception
    {
        if(a.equals(b))
            return a;
        Exchange ptr = a.parent;
        while(ptr != null)
        {
            if(ptr.childll.isMember(a)&&ptr.childll.isMember(b))
                return ptr;
            ptr=ptr.parent;
        }
        throw new Exception("Exchange does not exist");  
    }
    
    public ExchangeList routeCall(MobilePhone a, MobilePhone b) throws Exception
    {
        if(!root.mobps.isMember(a)||!root.mobps.isMember(b))
            throw new Exception("Mobile Phone is not registered");
        if(!a.status||!b.status)
            throw new Exception("Mobile Phone is switched off");
        ExchangeList el1 = new ExchangeList();
        Exchange lr = lowestRouter(a.base,b.base);
        Exchange ptr = a.base;
        while(ptr!=lr)
        {
            el1.Insert(ptr);
            ptr=ptr.parent;
        }
        el1.Insert(lr);
        ExchangeList el2 = new ExchangeList();
        ptr = b.base;
        while(ptr!=lr)
        {
            el2.InsertRev(ptr);
            ptr = ptr.parent;
        }
        return el1.concat(el2);
    }
    
    public String performAction(String actionMessage) {
        Scanner sc = new Scanner(actionMessage);
        String action = sc.next();
        int a,b;
        Exchange ex,ex2;
        String str = "";
        try {
            switch(action)
            {
                case "addExchange":
                    a = sc.nextInt();
                    b = sc.nextInt();
                    ex = getExchange(a);
                    if (ex==null)
                    {
                        throw new Exception("Parent exchange does not exist");
                        
                    }
                    else{
                        ex2 = new Exchange(b);
                        ex.addChild(ex2);
                    }
                    break;
                case "switchOnMobile":
                    a = sc.nextInt();
                    b = sc.nextInt();
                    ex = getExchange(b);
                    if (ex==null)
                    {
                        throw new Exception("Exchange does not exist");
                    }
                    else
                    {
                        MobilePhone mob = root.mobps.getMobilePhone(a);
                        if (mob==null)
                        {
                            if(ex.numChildren()>0)
                                throw new Exception("Can only add mobile to a base exchange");
                            switchOn(new MobilePhone(a),ex);
                        }else{
                            if(!ex.mobps.isMember(mob))
                               if(mob.status())
                               {    
                                   throw new Exception("Mobile registered with another exchange");
                               }
                               else{
                                   deleteMob(mob);
                                   switchOn(mob,ex);
                               }
                        }
                    }
                    break;
                case  "switchOffMobile":
                    a = sc.nextInt();
                    if(root.mobps.getMobilePhone(a)==null)
                    {
                        throw new Exception("Mobile does not exist");
                    }
                    else
                    {
                        switchOff(root.mobps.getMobilePhone(a));
                    }               
                    break;
                case "queryNthChild":
                    a = sc.nextInt();
                    b = sc.nextInt();
                    ex = getExchange(a);
                    if(ex == null)
                    {
                        throw new Exception("Parent exchange does not exist");
                    }
                    else{
                        ex2 = ex.child(b);
                        if(ex2 == null)
                        {
                            throw new Exception("Child does not exist at index = "+b);
                        }
                        else
                        {
                            str = actionMessage+": "+Integer.toString(ex2.uid);
                        }
                    }
                    break;
                case "queryMobilePhoneSet":
                    a = sc.nextInt();
                    ex = getExchange(a);
                    if (ex == null)
                    {
                       throw new Exception("Exchange does not exist");
                    }
                    else{
                        str = actionMessage+": "+ex.mobps.displaymob();
                    }
                    break;
                default:
                    System.out.println("Wrong Action Statement");
                    break;
            }
        } catch (Exception e) {
            System.out.println(actionMessage+": "+e.getMessage());
        }
        return str;
    }
}
