package MyAndroid.socketclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SocketClient extends Activity
{
	private Socket theSocket = null;;
	private BufferedReader theReader = null;;
	private PrintWriter theWriter = null;;
	private String mesg;
	
	private String ipAddress = null;;
	private int portNumber = 0;
	
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final EditText IP = (EditText) findViewById(R.id.IP);
        final EditText Port = (EditText) findViewById(R.id.Port);
        final EditText Message = (EditText) findViewById(R.id.Message);
        
        Button Login = (Button) findViewById(R.id.Login);
        Button Logout = (Button) findViewById(R.id.Logout);
        Button Send = (Button) findViewById(R.id.Send);
        
        Login.setOnClickListener(new View.OnClickListener()
        {
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				ipAddress = IP.getText().toString();
				portNumber = Integer.parseInt(Port.getText().toString());
				System.out.println(ipAddress + ":" + portNumber);
				connection();
			}
		});
        
        Logout.setOnClickListener(new View.OnClickListener()
        {	
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				disconnection();
				ipAddress = null;;
	    		portNumber = 0;
			}
		});
        
        Send.setOnClickListener(new View.OnClickListener()
        {
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				sendMsg(Message.getText().toString());
				
				showDialog("Send Message", "Send: " + receiveMsg());
			}
		});
    }
    
    public void connection()
    {
    	try{
    		theSocket = new Socket();
    		InetSocketAddress isa = new InetSocketAddress(ipAddress, portNumber);
    		theSocket.connect(isa, 10000);
    		System.out.println("theSocket: " + theSocket);
    		
    		theReader = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
    		theWriter = new PrintWriter(new OutputStreamWriter(theSocket.getOutputStream()));
    		
    		showDialog("Login", ipAddress + ":" + portNumber + " Login!");
    	}
    	catch(IOException e){System.out.println(e);}
    	finally{};
    }
    
    public void disconnection()
    {
    	try{
    		String logoutMsg = "end";
    		sendMsg(logoutMsg);
    		theWriter.close();
    		theReader.close();
    		theSocket.close();
    		
    		showDialog("Logout", ipAddress + ":" + portNumber + " Logout!");
    	}
    	catch(IOException e){System.out.println(e);}
    }
    
    public String receiveMsg()
    {
    	try{
    		mesg = theReader.readLine();
    		return mesg;
    	}
    	catch(IOException e){
    		System.out.println(e);
    		return "Reading Error!";}
    }

	public void sendMsg(String message)
    {
    	try{
    	theWriter.println(message);
		theWriter.flush();
    
    	}
    	catch(Exception e){System.out.println(e);}
    }
	
	public void showDialog(String title, String message)
	{
		AlertDialog dialog = new AlertDialog.Builder(SocketClient.this).create();
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setButton("Close", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				// TODO Auto-generated method stub
			}
		});
		dialog.show();
	}
}