package cn.jihe.management.bean;
/**
 * 查询开始时间和结束时间的实体类
 * @author Administrator
 *
 */
public class SelectDate {

	private String beginDate;
	private String overDate;
	public SelectDate() {
		// TODO Auto-generated constructor stub
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getOverDate() {
		return overDate;
	}
	public void setOverDate(String overDate) {
		this.overDate = overDate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beginDate == null) ? 0 : beginDate.hashCode());
		result = prime * result + ((overDate == null) ? 0 : overDate.hashCode());
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
		SelectDate other = (SelectDate) obj;
		if (beginDate == null) {
			if (other.beginDate != null)
				return false;
		} else if (!beginDate.equals(other.beginDate))
			return false;
		if (overDate == null) {
			if (other.overDate != null)
				return false;
		} else if (!overDate.equals(other.overDate))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Date [beginDate=" + beginDate + ", overDate=" + overDate + "]";
	}
	public SelectDate(String beginDate, String overDate) {
		super();
		this.beginDate = beginDate;
		this.overDate = overDate;
	}
	
	
	
}
