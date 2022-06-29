
public class Counting implements Runnable {
	public int num;
	
	public void run() {
		for(int i = 1;;i++) {
			try
			{
			    Thread.sleep(1000);
			}
			catch(InterruptedException ex)
			{
			    Thread.currentThread().interrupt();
			}
			num = i;
		}
	}
	public int value() {
		return num;
	}
}
