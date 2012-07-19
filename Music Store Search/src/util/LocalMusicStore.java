package util;

import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

public class LocalMusicStore {
	
	public static final int NUM_MUSICINFO = 5;
	
	private Vector musicInfos;
	
	public LocalMusicStore()
	{
		musicInfos = new Vector();
		Random r = new Random();
		
		int n = NUM_MUSICINFO;
		while(n-- > 0){
			int inc = r.nextInt(26);
			int song_char_int = (int)'a';
			char song_char = (char)(song_char_int + inc);
			StringBuffer sb = new StringBuffer(2);
			sb.append(song_char);
			sb.append(song_char);
			MusicInfo musicInfo = new MusicInfo(sb.toString(), r.nextInt(10000000), "mp3");
			musicInfos.add(musicInfo);
		}
	}
	
	public void printMusicInfos()
	{
		for(Enumeration e = musicInfos.elements(); e.hasMoreElements(); ){
			System.out.println(e.nextElement());
		}
	}
	
	public MusicInfo[] getMusicInfos()
	{
		Object[] oa = musicInfos.toArray();
		MusicInfo[] ret = new MusicInfo[oa.length];
		System.arraycopy(oa, 0, ret, 0, oa.length);
		return ret;
	}
}
