import java.io.IOException;

import net.tinyos.packet.PhoenixError;
import net.tinyos.packet.PhoenixSource;


public class MyPhoenixError implements PhoenixError {

	private PhoenixSource mySource;
	@Override
	public void error(IOException e) {
		System.out.println("There was a error: "+e.getMessage());
		if(mySource.isAlive())
		{
			System.out.println("Shoutdown PhoenixSource Thread");
			mySource.shutdown();
		}
	}
	
	public void setSource(PhoenixSource source)
	{
		this.mySource = source;
	}

}
