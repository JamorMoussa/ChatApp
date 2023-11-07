package chatapp;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class User extends Socket {

	public User(String IP, int port) throws UnknownHostException, IOException {
		super(IP, port);
	}

	public  BufferedReader getReader() throws IOException {
		
		InputStream is= getInputStream();
		return  new BufferedReader(new InputStreamReader(is));			
	}

	
	public  PrintWriter getWriter() throws IOException {
		OutputStream os= getOutputStream();
		return new PrintWriter(os,true);
	}

	
	public void RecieverMessage() {
		
		new Thread( new Runnable(){
			String msg;			

			public void run() 
			{
				try 
				{
					BufferedReader reader = getReader();
					while ( true ) 
					{
						
							msg=reader.readLine();
							if(msg!=null) System.out.println(msg);
					}
				}catch (IOException e){
			
				}
			}
		}).start();
	}


	public void SendMessage() {

		new Thread(){
 
			private Scanner sc;

			public void run() {
				try { 
					PrintWriter writer =  getWriter();
					
					while(true){
						
						sc = new Scanner(System.in);
						String str = sc.nextLine() ;
						
						writer.println(str); 
					}
				
				}catch (IOException e){
					
				}
			}
		}.start();
		
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		User user = new User("",12345);
		user.RecieverMessage();
		user.SendMessage();
		
	}

}
