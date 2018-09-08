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
    public void performAction(String actionMessage) {
        Scanner sc = new Scanner(actionMessage);
        String action = sc.next();
        int a,b;
        Exchange ex,ex2;
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
                               throw new Exception("Mobile registered with another exchange");
                           switchOn(mob,ex);
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
                            System.out.println(actionMessage+": "+ex2.uid);
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
                        System.out.println(actionMessage+": "+ex.mobps.displaymob());
                    }
                    break;
                default:
                    System.out.println("Wrong Action Statement");
                    break;
            }
        } catch (Exception e) {
            System.out.println(actionMessage+": "+e.getMessage());
        }      	
    }
}
