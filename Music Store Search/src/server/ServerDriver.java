package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Random;
import util.LocalMusicStore;

public class ServerDriver {
	
	// Constants
	public static final int NUM_CONNECTED_PEER = 2;
	public static final String DEFAULT_REGISTRY_HOSTNAME = "localhost";
	public static final int DEFAULT_REGISTRY_PORT = 1099;

	private String regHost;
	private int regPort;
	private int id;
	private MusicSearch peer1;
	private MusicSearch peer2;
	private LocalMusicStore localMusicStore;
	private MusicSearchImpl musicSerachImpl;
	
	public ServerDriver(String regHost, int regPort)
	{
		this.regHost = regHost;
		this.regPort = regPort;
		localMusicStore = new LocalMusicStore();
	}
	
	public void serve()
	{
		Random r = new Random();
		id = r.nextInt();
		if(id<0)id*=-1;
		String serviceName = "freeSongs_" + id; 
		
		System.out.println("service freeSongs_" + id + " is going to serve following songs:");
		localMusicStore.printMusicInfos();
		
		MusicSearch musicSearch = null;
		try {
			musicSearch = musicSerachImpl = new MusicSearchImpl(localMusicStore);
		} catch (RemoteException e1) {
			System.out.println("Cannot create music search object.");
			System.exit(-5);
		}
		String rmiUrl = "rmi://" + regHost + ":" + regPort + "/";
		String serviceUrl = rmiUrl + serviceName;
		try {
			Naming.rebind(serviceUrl, musicSearch);
			System.out.println("service freeSongs_" + id + " is bound to registry.");
			System.out.println("Sleeping 2 seconds...");
			Thread.sleep(2000);
			String[] peerList = Naming.list(rmiUrl);
			
			if(peerList.length < 3){
				System.out.println("There must be at least 2 peer except you registered in registry.");
				System.exit(-3);
			}
			
			int numPeer1, numPeer2;
			
			do{
				numPeer1 = r.nextInt(peerList.length);
			}while(peerList[numPeer1].contains(new Integer(id).toString()));
			
			do{
				numPeer2 = r.nextInt(peerList.length);
			}while(peerList[numPeer2].contains(new Integer(id).toString()) || numPeer1 == numPeer2);

			peer1 = (MusicSearch)Naming.lookup(peerList[numPeer1]);
			this.musicSerachImpl.setPeer1(peer1);
			System.out.println("Connected to " + peerList[numPeer1]);
			peer2 = (MusicSearch)Naming.lookup(peerList[numPeer2]);
			this.musicSerachImpl.setPeer2(peer2);
			System.out.println("Connected to " + peerList[numPeer2]);
			
		} catch (Exception e) {
			System.out.println("Error occured while connecting to registry.");
			System.exit(-4);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ensure that command line arguments are correct
		if (args.length > 2) {
		    System.err.println("usage: ServerDriver <registryHostname> <registryPortNumber>\n");
		    System.exit(-1);
		} 
		
		int registryPortNumber = DEFAULT_REGISTRY_PORT;
		String registryHostname = DEFAULT_REGISTRY_HOSTNAME;

		try {
			registryHostname  = args[0];
			try{
				registryPortNumber = Integer.parseInt(args[1]);
			} catch (NumberFormatException e){
			    System.err.println("2nd argument must be an integer");
			    System.exit(-2);
			}
		} catch (Exception e) {
		    System.err.println("1st argument must be a valid hostname");
		    System.exit(-2);
		}
		
		ServerDriver server = new ServerDriver(registryHostname, registryPortNumber);
		server.serve();
	}

}
