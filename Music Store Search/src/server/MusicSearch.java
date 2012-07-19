package server;


import util.MusicInfo;

public interface MusicSearch extends java.rmi.Remote { 

    public MusicInfo searchForSong(String songName, int hops) 
        throws java.rmi.RemoteException; 
}
