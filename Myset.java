public class Myset
{
	LinkedList setobj;

	public Myset()
	{
            setobj = new LinkedList();
	}
        public boolean isEmpty()
	{
		return setobj.isEmpty();
	}
	public boolean isMember(Object o)
	{
		return setobj.isMember(o);
	}
	public void Insert(Object o) throws Exception
	{
		if(!setobj.isMember(o))
			setobj.addAtHead(o);
		else
			throw new Exception();
	}
	public void Delete(Object o) throws Exception
	{
		if(setobj.isMember(o))
			setobj.deleteAtIndex(setobj.findob(o));
		else
			throw new Exception();
	}
	public Myset Union(Myset a)
	{
		Myset unset = new Myset();
		Node t = setobj.head;
		while(t!=null)
		{
			try{
				unset.Insert(t.data);
			} catch(Exception e){}
			t=t.next;
		}

		t = a.setobj.head;
		while(t!=null)
		{
			try{
				unset.Insert(t.data);
			} catch(Exception e){}
			t=t.next;
		}
		return unset;
	}
	public Myset Intersection(Myset a)
	{
		Myset intset = new Myset();
		Node t = setobj.head;
		while(t!=null)
		{
			if(a.isMember(t.data))
			{
				try{
				intset.Insert(t.data);
				} catch(Exception e){}
			}
			t=t.next;
		}
		return intset;
	}
}

//Node and linked list
class LinkedList
{
    //Class variables for the Linked List
    Node head;
    int numNodes;
    
    public LinkedList()
    {
        head = null;
        numNodes=0; 

    }
    public LinkedList(Object dat)
    {
        head = new Node(dat);
        numNodes = 1;
    }
    
    public void addAtHead(Object dat)
    {
        Node temp = head;
        head = new Node(dat);
        head.next = temp;
        numNodes++;
    }
    
    public void addAtTail(Object dat)
    {
        ///System.out.println(dat);
        if (head==null)
        {
            head = new Node(dat);
        }
        else
        {
            Node temp = head;
            while(temp.next != null)
            {
                temp = temp.next;
            }

            temp.next = new Node(dat);
        }
        numNodes++;
    }
    
    public void addAtIndex(int index, Object dat)
    {
        Node temp = head;
        Node holder;
        for(int i=0; i < index-1 && temp.next != null; i++)
        {
            temp = temp.next;
        }
        holder = temp.next;
        temp.next = new Node(dat);
        temp.next.next = holder;
        numNodes++;
    }
    
    public void deleteAtIndex(int index)
    {
        Node temp = head;
        for(int i=0; i< index - 1 && temp.next != null; i++)
        {
            temp = temp.next;
        }
        temp.next = temp.next.next;
        numNodes--;
    }
    
    public int find(Node n)
    {
        Node t = head;
        int index = 0;
        while(t != n)
        {
            index++;
            t = t.next;
        }
        return index;
    }

    public int findob(Object o)
    {
        Node t = head;
        int index = 0;
        while(t.data != o && t != null)
        {
            index++;
            t = t.next;
        }
        if (t==null)
            return -1;
        else
            return index;       
    }
    
    public Node ithNode(int index)//starts from index 0...add exception later
    {
        if (index >= numNodes)
            return null;
        else
        {
            Node ptr = head;
            int ctr =0;
            while(ctr<index)
            {
                ptr=ptr.next;
                ctr++;
            }
            return ptr;
        }
        
    }

    public boolean isMember(Object o)
    {
        Node temp = head;
        while(temp != null)
        {
            if(o.equals(temp.data))
                return true;
            temp = temp.next;
        }
        return false;
    }

    public boolean isEmpty()
    {
        if (numNodes==0)
            return true;
        return false;
    }
        
    public void printList()
    {
        Node temp = head;
        while(temp != null)
        {
            System.out.println(temp.data);
            temp = temp.next;
        }
    }
    
    public int getSize()
    {
        return numNodes;
    }
}
class Node
    {
        //Declare class variables
        Node next;
        Object data;
        
        public Node(Object dat)
        {
            data = dat;
            next = null;
        }
        public Node()
        {
            data = null;
            next = null;
        }
        
        public Object getData()
        {
            return data;
        }
    }