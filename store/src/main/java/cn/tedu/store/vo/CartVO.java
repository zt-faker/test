package cn.tedu.store.vo;

import java.io.Serializable;

public class CartVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 461318594743947337L;
	private Integer cid;
	private Integer uid;
	private Integer pid;
	private Integer num;
	private Long price;
	private Long realPrice;
	private String title;
	private String image;
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Long getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(Long realPrice) {
		this.realPrice = realPrice;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "CartVO [cid=" + cid + ", uid=" + uid + ", pid=" + pid + ", num=" + num + ", price=" + price
				+ ", realPrice=" + realPrice + ", title=" + title + ", image=" + image + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
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
		CartVO other = (CartVO) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
