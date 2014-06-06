package com.baiting.bean.netsong;

public class SongItem{
	private SongItem songItem ;
	private String sid ;
	private String sname ;
	private String author ;

	public SongItem getSongItem() {
		return songItem;
	}

	public void setSongItem(SongItem songItem) {
		this.songItem = songItem;
	}
	
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((sid == null) ? 0 : sid.hashCode());
		result = prime * result + ((sname == null) ? 0 : sname.hashCode());
		result = prime * result
				+ ((songItem == null) ? 0 : songItem.hashCode());
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
		SongItem other = (SongItem) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (sid == null) {
			if (other.sid != null)
				return false;
		} else if (!sid.equals(other.sid))
			return false;
		if (sname == null) {
			if (other.sname != null)
				return false;
		} else if (!sname.equals(other.sname))
			return false;
		if (songItem == null) {
			if (other.songItem != null)
				return false;
		} else if (!songItem.equals(other.songItem))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SongItem [sid=" + sid + ", sname=" + sname + ", author="
				+ author + "]" ;
	}
}

