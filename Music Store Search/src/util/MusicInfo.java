package util;

import java.io.Serializable;

public class MusicInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String songName;
	private int size;
	private String format;

	public MusicInfo() {
		setSongName("DEFAULT");
		setSize(0);
		setFormat("DEFAULT");
	}

	public MusicInfo(String songName, int size, String format) {
		this.setSongName(songName);
		this.setSize(size);
		this.setFormat(format);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Song Name: " + getSongName() + "\n");
		sb.append("Size: " + getSize() + "\n");
		sb.append("Format: " + getFormat() + "\n");
		return sb.toString();
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getSongName() {
		return songName;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getFormat() {
		return format;
	}
}
