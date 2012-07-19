package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import util.LocalMusicStore;
import util.MusicInfo;

public class MusicSearchImpl extends UnicastRemoteObject implements
		MusicSearch {

	private LocalMusicStore localMusicStore;
	private MusicSearch peer1, peer2; 
	private static final long serialVersionUID = 1L;

	protected MusicSearchImpl(LocalMusicStore localMusicStore) throws RemoteException {
		super();
		this.localMusicStore = localMusicStore;
	}

	public MusicInfo searchForSong(String songName, int hops) throws RemoteException {
		System.out.println("Search request came: " + songName + " hops: " + hops);
		MusicInfo[] localMusicInfos = localMusicStore.getMusicInfos();
		for(int i=0; i<localMusicInfos.length; i++){
			if(localMusicInfos[i].getSongName().equalsIgnoreCase(songName)){
				return localMusicInfos[i];
			}
		}
		
		if(hops > 0){
			hops--;
			if (peer1 != null) {
				MusicInfo peer1response = peer1.searchForSong(songName, hops);
				if (!peer1response.getSongName().equals("NOT_FOUND")) {
					return peer1response;
				} else {
					if (peer2 != null) {
						MusicInfo peer2response = peer2.searchForSong(songName,
								hops);
						if (!peer2response.getSongName().equals("NOT_FOUND")) {
							return peer2response;
						}
					}
				}
			}
		}
		
		return new MusicInfo("NOT_FOUND", 0, "NOT_FOUND");
	}

	public void setPeer1(MusicSearch peer1) {
		this.peer1 = peer1;
	}

	public MusicSearch getPeer1() {
		return peer1;
	}

	public void setPeer2(MusicSearch peer2) {
		this.peer2 = peer2;
	}

	public MusicSearch getPeer2() {
		return peer2;
	}

}
