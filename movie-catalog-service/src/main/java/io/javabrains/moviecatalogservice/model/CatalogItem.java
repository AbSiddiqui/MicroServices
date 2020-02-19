package io.javabrains.moviecatalogservice.model;

public class CatalogItem {

	private String name;
	private String desc;
	private int raring;
	
	
	
	public CatalogItem(String name, String desc, int raring) {
		super();
		this.name = name;
		this.desc = desc;
		this.raring = raring;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getRaring() {
		return raring;
	}
	public void setRaring(int raring) {
		this.raring = raring;
	}
	
}
