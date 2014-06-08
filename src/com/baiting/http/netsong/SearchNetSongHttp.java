package com.baiting.http.netsong;

import com.baiting.bean.NetSong;
import com.baiting.layout.MusicTable;

public abstract class SearchNetSongHttp extends NetSongHttp {

	protected NetSong netSong;
	protected MusicTable musicTable;
	
	public SearchNetSongHttp() {
		super();
	}
	
	public SearchNetSongHttp(NetSong netSong) {
		this.netSong = netSong;
	}

	public void setNetSong(NetSong netSong) {
		this.netSong = netSong;
	}
	
	public void setMusicTable(MusicTable musicTable){
		this.musicTable = musicTable ;
	}
	
	public abstract  MusicTable getMusicTable();
}
