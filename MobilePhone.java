// fill Exchange base, status exceptions, update constructor for status

public class MobilePhone
{
	int id;
	Boolean status;
	Exchange base;

	MobilePhone(int number)
	{
		id = number;
                status = false;
	}
	public int number()
	{
		return id;
	}
	public boolean status()
	{
		return status;
	}
	public void switchOn()
	{
		status = true;
	}
	public void switchOff()
	{
		status = false;
	}
	public Exchange location() throws Exception
	{
		if (status==true)
			return base;
		else
			throw new Exception();
	}
}