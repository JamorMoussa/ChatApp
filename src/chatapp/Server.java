package chatapp;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server extends ServerSocket{
	
	private ArrayList<Socket> ConnectedUsersList = new ArrayList<Socket>();
	
	public Server(int port) throws IOException {
		super(port);
	}
	
	private void add(Socket user) {
		ConnectedUsersList.add(user);
	}
	
	public BufferedReader getReader(Socket user) throws IOException {
		InputStream is = user.getInputStream();
		InputStreamReader ipsr=new InputStreamReader(is);		
		return new BufferedReader(ipsr);
	}
	
	public PrintWriter getPrinter(Socket user) throws IOException {
		OutputStream os = user.getOutputStream();
		return new PrintWriter(os,true);
	}
	
	public void sendMessageToAllUsers(Socket sender) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					BufferedReader reader = getReader(sender);
					String msg;
					PrintWriter printer; 
				
					while(true) {
						
						msg = reader.readLine();
						
						if(msg!=null) {
						
							for(var user : ConnectedUsersList) {
									
								if(sender != user) {
									
									printer = getPrinter(user);
									
									printer.println(msg);
								}
									
							}
						}						
					}
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();
		
	}
	
	public void acceptUsers() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				while(true) {
					
					try {
						Socket user = accept();
						add(user);
						sendMessageToAllUsers(user);						
						
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}	
			}
		}).start();
		
	}

	public static void main(String[] args) throws IOException {
		
		Server server = new Server(12345);
		
		System.out.println("server is waiting !!!!");
		
		server.acceptUsers();
	
	}

}








