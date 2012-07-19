package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import server.MusicSearch;
import util.MusicInfo;

public class ClientDriver {

	public static final String DEFAULT_REGISTRY_HOSTNAME = "localhost";
	public static final int DEFAULT_REGISTRY_PORT = 1099;
	public static final int DEFAULT_NUM_HOPS = 3;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ensure that command line arguments are correct
		if (args.length > 4) {
		    System.err.println("usage: ClientDriver <registryHostname> <registryPortNumber> <songName> <numHops>\n");
		    System.exit(-1);
		} 
		
		int registryPortNumber = DEFAULT_REGISTRY_PORT;
		String registryHostname = DEFAULT_REGISTRY_HOSTNAME;
		String songName;
		int numHops = DEFAULT_NUM_HOPS;

		registryHostname = args[0];
		try{
			registryPortNumber = Integer.parseInt(args[1]);
		}catch(NumberFormatException e){
			System.out.println("Registry port number must be an integer.");
			System.exit(-3);
		}
		songName = args[2];
		try{
			numHops = Integer.parseInt(args[3]);
		}catch(NumberFormatException e){
			System.out.println("Hop count must be an integer.");
			System.exit(-4);
		}
		
		String[] urls;
		try {
			urls = Naming.list("rmi://" + registryHostname + ":" + registryPortNumber + "/");
			
			Vector v = new Vector(Arrays.asList(urls));
			Iterator i = v.iterator();
			while(i.hasNext()){
				String url = (String)i.next();
				if(!url.contains("freeSongs")){
					i.remove();
				}
			}
			
			try {
				Random r = new Random();
				MusicSearch musicSearch = (MusicSearch)Naming.lookup(urls[r.nextInt(v.size())]);
				MusicInfo response = musicSearch.searchForSong(songName, numHops);
				System.out.println(response.toString());
			} catch (NotBoundException e) {
				System.out.println("Cannot find peer.");
				System.exit(-5);
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
			System.out.println("Cannot connect to registry.");
			System.exit(-1);
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL.");
			System.exit(-2);
		}
	}

}
