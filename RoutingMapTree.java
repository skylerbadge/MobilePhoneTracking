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
            throw new Exception("Error - Mobile phone with identifier "+a.id+" is already switched on");
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
            throw new Exception("Error - Mobile phone with identifier "+a.id+" is already switched off");            
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
            throw new Exception("Error - No mobile phone with identifier "+m.id+" found in the network");
        else if(!root.mobps.getMobilePhone(m.id).status())
            throw new Exception("Error - Mobile phone with identifier "+m.id+" is currently switched off");
        return root.mobps.getMobilePhone(m.id).base;
    }
    
    public Exchange lowestRouter(Exchange a, Exchange b) throws Exception
    {
        if(a.equals(b))
            return a;
        Exchange prta = a.parent;
        Exchange prtb = b.parent;
        while(prta!=null && prtb!=null)
        {
            if(prta==prtb)
                return prta;
            prta=prta.parent;
            prtb=prtb.parent;
        }
        throw new Exception("Error - Exchange does not exist in the network");  
    }
    
    public ExchangeList routeCall(MobilePhone a, MobilePhone b) throws Exception
    {
        if(!root.mobps.isMember(a))
            throw new Exception("Error - No mobile phone with identifier "+a.id+" found in the network");
        if(!root.mobps.isMember(b))
            throw new Exception("Error - No mobile phone with identifier "+b.id+" found in the network");
        else if(!a.status)
            throw new Exception("Error - Mobile phone with identifier "+a.id+" is currently switched off");
        else if(!b.status)
            throw new Exception("Error - Mobile phone with identifier "+b.id+" is currently switched off");
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
    
    public void movePhone(MobilePhone a, Exchange b) throws Exception
    {
        if (!root.mobps.isMember(a))
            throw new Exception("Error - No mobile phone with identifier "+a.id+" found in the network");
        else if (!containsNode(b))
            throw new Exception("Error - No exchange with identifier "+b.uid+" found in the network");
        else if (!a.status)
            throw new Exception("Error - Mobile phone with identifier "+a.id+" is currently switched off"); 
        else if (a.base.equals(b))
            throw new Exception("Error - Mobile phone already registered with Exchange with identifier "+b.uid);
        deleteMob(a);
        b.addMobile(a);
        a.base=b;
        while(!b.isroot())
        {
            b=b.parent();
            b.addMobile(a);
        }
    }
    public String performAction(String actionMessage) {
        Scanner sc = new Scanner(actionMessage);
        String action = sc.next();
        int a,b;
        Exchange ex,ex2,ex3;
        MobilePhone m,m1;
        String str = "";
            switch(action)
            {
                
                case "addExchange":
                    try{
                    a = sc.nextInt();
                    b = sc.nextInt();
                    ex = getExchange(a);
                    if (ex==null)
                    {
                        throw new Exception("Error - No exchange with identifier "+a+" found in the network");
                        
                    }
                    else{
                        ex2 = new Exchange(b);
                        ex.addChild(ex2);
                    }
                    } catch (Exception e) {
                    return (actionMessage+": "+e.getMessage());
                    }
                    break;
                case "switchOnMobile":
                    try{
                    a = sc.nextInt();
                    b = sc.nextInt();
                    ex = getExchange(b);
                    if (ex==null)
                    {
                        throw new Exception("Error - No exchange with identifier "+b+" found in the network");
                    }
                    MobilePhone mob = root.mobps.getMobilePhone(a);
                    if (mob==null)
                    {
                        if(ex.numChildren()>0)
                            throw new Exception("Error - Can only add mobile to a base exchange");
                        switchOn(new MobilePhone(a),ex);
                    }else{
                        if(!ex.mobps.isMember(mob))
                        {
                           if(mob.status())
                           {    
                               throw new Exception("Error - Mobile registered with another exchange");
                           }
                           else{
                               deleteMob(mob);
                               switchOn(mob,ex);
                           }
                        }
                        else
                        {
                            switchOn(mob, ex);
                        }
                    }
                    } catch (Exception e) {
                    return (actionMessage+": "+e.getMessage());
                    }
                    break;
                case  "switchOffMobile":
                    try{
                    a = sc.nextInt();
                    if(root.mobps.getMobilePhone(a)==null)
                    {
                        throw new Exception("Error - No mobile phone with identifier "+a+" found in the network");
                    }
                    else
                    {
                        switchOff(root.mobps.getMobilePhone(a));
                    }               
                    } catch (Exception e) {
                    return (actionMessage+": "+e.getMessage());
                    }
                    break;
                case "queryNthChild":
                    try{
                    a = sc.nextInt();
                    b = sc.nextInt();
                    ex = getExchange(a);
                    if(ex == null)
                    {
                        throw new Exception("Error - No exchange with identifier "+a+" found in the network");
                    }
                    else{
                        ex2 = ex.child(b);
                        if(ex2 == null)
                        {
                            throw new Exception("Error - Child does not exist at index = "+b);
                        }
                        else
                        {
                            str = actionMessage+": "+Integer.toString(ex2.uid);
                        }
                    }
                    } catch (Exception e) {
                    return (actionMessage+": "+e.getMessage());
                    }
                    break;
                case "queryMobilePhoneSet":
                    try{
                    a = sc.nextInt();
                    ex = getExchange(a);
                    if (ex == null)
                    {
                       throw new Exception("Error - No exchange with identifier "+a+" found in the network");
                    }
                    else{
                        str = actionMessage+": "+ex.mobps.displaymob();
                    }
                    } catch (Exception e) {
                    return (actionMessage+": "+e.getMessage());
                    }
                    break;
                case "findPhone":
                    try{
                    a = sc.nextInt();
                    m = root.mobps.getMobilePhone(a);
                    if(m==null)
                    {
                        throw new Exception("Error - No mobile phone with identifier "+a+" found in the network");
                    }
                    ex = findPhone(m);
                    str = "queryF"+actionMessage.substring(1)+": "+Integer.toString(ex.uid);
                    } catch (Exception e) {
                    return ("queryF"+actionMessage.substring(1)+": "+e.getMessage());
                    }
                    break;
                case "lowestRouter":
                    try{
                    a = sc.nextInt();
                    b = sc.nextInt();
                    ex = getExchange(a);
                    ex2 = getExchange(b);
                    if (ex == null)
                    {
                       throw new Exception("Error - No exchange with identifier "+a+" found in the network");
                    }
                    if (ex2==null)
                    {
                       throw new Exception("Error - No exchange with identifier "+b+" found in the network");
                    }
                    ex3 = lowestRouter(ex, ex2);
                    str = "queryL"+actionMessage.substring(1)+": "+Integer.toString(ex3.uid);
                    } catch (Exception e) {
                    return ("queryL"+actionMessage.substring(1)+": "+e.getMessage());
                    }
                    break;                    
                case "findCallPath":
                    try{
                    a = sc.nextInt();
                    b = sc.nextInt();
                    m = root.mobps.getMobilePhone(a);
                    m1 = root.mobps.getMobilePhone(b);
                    if(m==null)
                    {
                        throw new Exception("Error - No mobile phone with identifier "+a+" found in the network");
                    }
                    if(m1==null)
                    {
                        throw new Exception("Error - No mobile phone with identifier "+b+" found in the network");
                    }
                    ExchangeList el = routeCall(m,m1);
                    Node ptr = new Node();
                    ptr = el.ell.head;
                    String list = "";
                    if(ptr == null)
                        throw new Exception("Error - Cannot route call");
                    else
                    {
                        while(ptr!=null)
                        {
                            list = list +", "+ Integer.toString(((Exchange)ptr.data).uid);
                            ptr = ptr.next;
                        }
                        list = list.substring(2);
                    }
                    str = "queryF"+actionMessage.substring(1)+": "+list;
                    } catch (Exception e) {
                    return ("queryF"+actionMessage.substring(1)+": "+e.getMessage());
                    }
                    break;
                case "movePhone":
                    try{
                    a = sc.nextInt();
                    b = sc.nextInt();
                    m = root.mobps.getMobilePhone(a);
                    if(m==null)
                    {
                        throw new Exception("Error - No mobile phone with identifier "+a+" found in the network");
                    }
                    ex = getExchange(b);
                    if (ex == null)
                    {
                       throw new Exception("Error - No exchange with identifier "+b+" found in the network");
                    }
                    movePhone(m, ex);
                    } catch (Exception e) {
                    return ("queryM"+actionMessage.substring(1)+": "+e.getMessage());
                    }
                    break;                    
                default:
                    return ("Error - Wrong Action Statement");
            }
        return str;
    }
}
