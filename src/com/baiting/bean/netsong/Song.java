package com.baiting.bean.netsong;

public class Song{
	private String queryId ;
	private int status ;
	private String songId ;
	private String songName ;
	private String artistId ;
	private String singerId ;
	private String artistName ;
	private String singerName ;
	private String albumId ;
	private String albumName ;
	private String songPicSmall ;
	private String songPicBig ;
	private String songPicRadio ;
	private String lrcLink ;
	private String version ;
	private String copyType ;
	private int time ;
	private String linkCode ;
	private String songLink ;
	private String showLink ;
	private String playlinkUrl ;
	private String format ;
	private int rate ;
	private int size ;
	private int relateStatus ;
	private int resourceType ;
	private int del_status ;
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getSongId() {
		return songId;
	}
	public void setSongId(String songId) {
		this.songId = songId;
	}
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public String getArtistId() {
		return artistId;
	}
	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getAlbumId() {
		return albumId;
	}
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public String getSongPicSmall() {
		return songPicSmall;
	}
	public void setSongPicSmall(String songPicSmall) {
		this.songPicSmall = songPicSmall;
	}
	public String getSongPicBig() {
		return songPicBig;
	}
	public void setSongPicBig(String songPicBig) {
		this.songPicBig = songPicBig;
	}
	public String getSongPicRadio() {
		return songPicRadio;
	}
	public void setSongPicRadio(String songPicRadio) {
		this.songPicRadio = songPicRadio;
	}
	public String getLrcLink() {
		return lrcLink;
	}
	public void setLrcLink(String lrcLink) {
		this.lrcLink = lrcLink;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCopyType() {
		return copyType;
	}
	public void setCopyType(String copyType) {
		this.copyType = copyType;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getLinkCode() {
		return linkCode;
	}
	public void setLinkCode(String linkCode) {
		this.linkCode = linkCode;
	}
	public String getSongLink() {
		return songLink;
	}
	public void setSongLink(String songLink) {
		this.songLink = songLink;
	}
	public String getShowLink() {
		return showLink;
	}
	public void setShowLink(String showLink) {
		this.showLink = showLink;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getRelateStatus() {
		return relateStatus;
	}
	public void setRelateStatus(int relateStatus) {
		this.relateStatus = relateStatus;
	}
	public int getResourceType() {
		return resourceType;
	}
	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}
	public int getDel_status() {
		return del_status;
	}
	public void setDel_status(int del_status) {
		this.del_status = del_status;
	}
	public String getSingerId() {
		return singerId;
	}
	public void setSingerId(String singerId) {
		this.singerId = singerId;
	}
	public String getSingerName() {
		return singerName;
	}
	public void setSingerName(String singerName) {
		this.singerName = singerName;
	}
	public String getPlaylinkUrl() {
		return playlinkUrl;
	}
	public void setPlaylinkUrl(String playlinkUrl) {
		this.playlinkUrl = playlinkUrl;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((albumId == null) ? 0 : albumId.hashCode());
		result = prime * result
				+ ((albumName == null) ? 0 : albumName.hashCode());
		result = prime * result
				+ ((artistId == null) ? 0 : artistId.hashCode());
		result = prime * result
				+ ((artistName == null) ? 0 : artistName.hashCode());
		result = prime * result
				+ ((copyType == null) ? 0 : copyType.hashCode());
		result = prime * result + del_status;
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result
				+ ((linkCode == null) ? 0 : linkCode.hashCode());
		result = prime * result + ((lrcLink == null) ? 0 : lrcLink.hashCode());
		result = prime * result
				+ ((playlinkUrl == null) ? 0 : playlinkUrl.hashCode());
		result = prime * result + ((queryId == null) ? 0 : queryId.hashCode());
		result = prime * result + rate;
		result = prime * result + relateStatus;
		result = prime * result + resourceType;
		result = prime * result
				+ ((showLink == null) ? 0 : showLink.hashCode());
		result = prime * result
				+ ((singerId == null) ? 0 : singerId.hashCode());
		result = prime * result
				+ ((singerName == null) ? 0 : singerName.hashCode());
		result = prime * result + size;
		result = prime * result + ((songId == null) ? 0 : songId.hashCode());
		result = prime * result
				+ ((songLink == null) ? 0 : songLink.hashCode());
		result = prime * result
				+ ((songName == null) ? 0 : songName.hashCode());
		result = prime * result
				+ ((songPicBig == null) ? 0 : songPicBig.hashCode());
		result = prime * result
				+ ((songPicRadio == null) ? 0 : songPicRadio.hashCode());
		result = prime * result
				+ ((songPicSmall == null) ? 0 : songPicSmall.hashCode());
		result = prime * result + status;
		result = prime * result + time;
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		Song other = (Song) obj;
		if (albumId == null) {
			if (other.albumId != null)
				return false;
		} else if (!albumId.equals(other.albumId))
			return false;
		if (albumName == null) {
			if (other.albumName != null)
				return false;
		} else if (!albumName.equals(other.albumName))
			return false;
		if (artistId == null) {
			if (other.artistId != null)
				return false;
		} else if (!artistId.equals(other.artistId))
			return false;
		if (artistName == null) {
			if (other.artistName != null)
				return false;
		} else if (!artistName.equals(other.artistName))
			return false;
		if (copyType == null) {
			if (other.copyType != null)
				return false;
		} else if (!copyType.equals(other.copyType))
			return false;
		if (del_status != other.del_status)
			return false;
		if (format == null) {
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		if (linkCode == null) {
			if (other.linkCode != null)
				return false;
		} else if (!linkCode.equals(other.linkCode))
			return false;
		if (lrcLink == null) {
			if (other.lrcLink != null)
				return false;
		} else if (!lrcLink.equals(other.lrcLink))
			return false;
		if (playlinkUrl == null) {
			if (other.playlinkUrl != null)
				return false;
		} else if (!playlinkUrl.equals(other.playlinkUrl))
			return false;
		if (queryId == null) {
			if (other.queryId != null)
				return false;
		} else if (!queryId.equals(other.queryId))
			return false;
		if (rate != other.rate)
			return false;
		if (relateStatus != other.relateStatus)
			return false;
		if (resourceType != other.resourceType)
			return false;
		if (showLink == null) {
			if (other.showLink != null)
				return false;
		} else if (!showLink.equals(other.showLink))
			return false;
		if (singerId == null) {
			if (other.singerId != null)
				return false;
		} else if (!singerId.equals(other.singerId))
			return false;
		if (singerName == null) {
			if (other.singerName != null)
				return false;
		} else if (!singerName.equals(other.singerName))
			return false;
		if (size != other.size)
			return false;
		if (songId == null) {
			if (other.songId != null)
				return false;
		} else if (!songId.equals(other.songId))
			return false;
		if (songLink == null) {
			if (other.songLink != null)
				return false;
		} else if (!songLink.equals(other.songLink))
			return false;
		if (songName == null) {
			if (other.songName != null)
				return false;
		} else if (!songName.equals(other.songName))
			return false;
		if (songPicBig == null) {
			if (other.songPicBig != null)
				return false;
		} else if (!songPicBig.equals(other.songPicBig))
			return false;
		if (songPicRadio == null) {
			if (other.songPicRadio != null)
				return false;
		} else if (!songPicRadio.equals(other.songPicRadio))
			return false;
		if (songPicSmall == null) {
			if (other.songPicSmall != null)
				return false;
		} else if (!songPicSmall.equals(other.songPicSmall))
			return false;
		if (status != other.status)
			return false;
		if (time != other.time)
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Song [queryId=" + queryId + ", status=" + status + ", songId="
				+ songId + ", songName=" + songName + ", artistId=" + artistId
				+ ", singerId=" + singerId + ", artistName=" + artistName
				+ ", singerName=" + singerName + ", albumId=" + albumId
				+ ", albumName=" + albumName + ", songPicSmall=" + songPicSmall
				+ ", songPicBig=" + songPicBig + ", songPicRadio="
				+ songPicRadio + ", lrcLink=" + lrcLink + ", version="
				+ version + ", copyType=" + copyType + ", time=" + time
				+ ", linkCode=" + linkCode + ", songLink=" + songLink
				+ ", showLink=" + showLink + ", playlinkUrl=" + playlinkUrl
				+ ", format=" + format + ", rate=" + rate + ", size=" + size
				+ ", relateStatus=" + relateStatus + ", resourceType="
				+ resourceType + ", del_status=" + del_status + "]";
	}
	
}
