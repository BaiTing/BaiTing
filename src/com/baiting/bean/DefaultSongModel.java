package com.baiting.bean;

import java.io.Serializable;

/**
 * 定义一个默认的父类用于兼容其他自建列表
 * @author sunshine
 *
 */
public class DefaultSongModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String name ;

	public DefaultSongModel() {
		// TODO Auto-generated constructor stub
	}
	
	public DefaultSongModel(String name){
		this.name = name ;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		DefaultSongModel other = (DefaultSongModel) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DefaultSongModel [name=" + name + "]";
	}
	
}
