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
    public void switchOn(MobilePhone a, Exchange b)//add exception check leaf
    {
        ///System.out.println("adding to "+b.isroot()+b.uid+a.status+a.id);
        if (!a.status())
        {
            
            b.addMobile(a);
            a.base=b;
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
            Exchange b = a.base;
            ///System.out.println("boho"+b.uid);
            b.delMobile(a);
            ///System.out.println("added to "+b.uid);
            while(!b.isroot())
            {
                b=b.parent();
                b.delMobile(a);
                ///System.out.println("added to "+b.uid);
            }
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
        //System.out.println(actionMessage);
        try {
            switch(action)
            {
                case "addExchange":
                    a = sc.nextInt();
                    b = sc.nextInt();
                    ex = getExchange(a);
                    //System.out.println(ex.isroot());
                    if (ex==null)
                    {
                        throw new Exception("Exchange does not exist");
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
                        throw new Exception("Mobile Phone does not exist");
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

                    if(ex == null)
                    {
                        throw new Exception("Exchange does not exist");
                    }
                    else if(ex2 == null)
                    {
                        throw new Exception(b+"th Child does not exist");
                    }
                    else
                    {
                        System.out.println(actionMessage+": "+ex2.uid);
                    }               
                    break;
                case "queryMobilePhoneSet":
                    a = sc.nextInt();
                    ex = getExchange(a);

                    ///System.out.println("boo " + ex.uid);
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
            System.out.println("ERROR : "+ e);
        }      	
    }
}
