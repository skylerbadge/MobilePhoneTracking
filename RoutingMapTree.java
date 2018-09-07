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
    public void switchOn(MobilePhone a, Exchange b)//add exception
    {
        ///System.out.println("adding to "+b.isroot()+b.uid+a.status+a.id);
        if (!a.status())
        {
            
            b.addMobile(a);
            ///System.out.println("added to "+b.uid);
            while(!b.isroot())
            {
                b=b.parent();
                b.addMobile(a);
                ///System.out.println("added to "+b.uid);
            }
            a.switchOn();
        }
    }
    public void switchOff(MobilePhone a)//add exception check leaf
    {
        if (a.status())
        {
            a.switchOff();            
        }
    }
    public Exchange getExchange(int id)//add exception
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
    public Exchange print()//debug
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
       // System.out.println(actionMessage);
        //try {
        switch(action)
        {
            case "addExchange":
                a = sc.nextInt();
                b = sc.nextInt();
                ex = getExchange(a);
                //System.out.println(ex.isroot());
                /*if (ex==null)
                {
                    throw new Exception("1");
                }
                else{*/
                    ex2 = new Exchange(b);
                    ex.addChild(ex2);
                //}
                break;
            case "switchOnMobile":
                a = sc.nextInt();
                b = sc.nextInt();
                ex = getExchange(b);
                if (ex==null)
                {
                    //throw new Exception("2");
                }
                //check leaf
                else
                {
                    MobilePhone mob = ex.mobps.getMobilePhone(a);
                    
                    if (mob==null)// will add automatically so redundant
                    {
                        switchOn(new MobilePhone(a),ex);
                    }else{
                       switchOn(mob,ex);
                    }
                }
                break;
            case  "switchOffMobile":
                a = sc.nextInt();
                if(root.mobps.getMobilePhone(a)==null)
                {
                    //throw new Exception("3");
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
                ex2 = ex.child(b);
                
                if(ex == null|| ex2 == null)
                {
                    //throw new Exception("4");
                }
                else
                {
                    System.out.println(ex2.uid);
                }               
                break;
            case "queryMobilePhoneSet":
                a = sc.nextInt();
                ex = getExchange(a);
                
                ///System.out.println("boo " + ex.uid);
                if (ex == null)
                {
                    //throw new Exception("5");
                }
                else{
                    System.out.println(ex.mobps.displaymob());
                }
                break;
            default:
                System.out.println("Wrong Action Statement");
                break;
        }
        /*} catch (Exception e) {
            System.out.println("lol error"+ e);
        }
        */	
    }
}
