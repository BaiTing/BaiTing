package com.baiting.bean.netsong;

import java.util.List;

import com.baiting.bean.Song;

public class SongXCode{
	private String xcode ;
	private List<Song> songList ;
	public String getXcode() {
		return xcode;
	}
	public void setXcode(String xcode) {
		this.xcode = xcode;
	}
	public List<Song> getSongList() {
		return songList;
	}
	public void setSongList(List<Song> songList) {
		this.songList = songList;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((songList == null) ? 0 : songList.hashCode());
		result = prime * result + ((xcode == null) ? 0 : xcode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SongXCode other = (SongXCode) obj;
		if (songList == null) {
			if (other.songList != null)
				return false;
		} else if (!songList.equals(other.songList))
			return false;
		if (xcode == null) {
			if (other.xcode != null)
				return false;
		} else if (!xcode.equals(other.xcode))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SongXCode [xcode=" + xcode + ", songList=" + songList + "]";
	}
	
}
