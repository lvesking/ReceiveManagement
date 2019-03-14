package cn.jihe.management.bean;

import java.io.Serializable;
/**
 * 定义数据库获取字段的实体类
 * @author Administrator
 *
 */
public class ReceiveList implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String RL_fnumPlanQty;
	private String RH_fvarVendorCode;
	private String RH_fvarInputTime;
	private String VM_fvarCDesc;
	public ReceiveList() {
	}
	public String getRL_fnumPlanQty() {
		return RL_fnumPlanQty;
	}
	public void setRL_fnumPlanQty(String rL_fnumPlanQty) {
		RL_fnumPlanQty = rL_fnumPlanQty;
	}
	public String getRH_fvarVendorCode() {
		return RH_fvarVendorCode;
	}
	public void setRH_fvarVendorCode(String rH_fvarVendorCode) {
		RH_fvarVendorCode = rH_fvarVendorCode;
	}
	public String getRH_fvarInputTime() {
		return RH_fvarInputTime;
	}
	public void setRH_fvarInputTime(String rH_fvarInputTime) {
		RH_fvarInputTime = rH_fvarInputTime;
	}
	public String getVM_fvarCDesc() {
		return VM_fvarCDesc;
	}
	public void setVM_fvarCDesc(String vM_fvarCDesc) {
		VM_fvarCDesc = vM_fvarCDesc;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((RH_fvarInputTime == null) ? 0 : RH_fvarInputTime.hashCode());
		result = prime * result + ((RH_fvarVendorCode == null) ? 0 : RH_fvarVendorCode.hashCode());
		result = prime * result + ((RL_fnumPlanQty == null) ? 0 : RL_fnumPlanQty.hashCode());
		result = prime * result + ((VM_fvarCDesc == null) ? 0 : VM_fvarCDesc.hashCode());
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
		ReceiveList other = (ReceiveList) obj;
		if (RH_fvarInputTime == null) {
			if (other.RH_fvarInputTime != null)
				return false;
		} else if (!RH_fvarInputTime.equals(other.RH_fvarInputTime))
			return false;
		if (RH_fvarVendorCode == null) {
			if (other.RH_fvarVendorCode != null)
				return false;
		} else if (!RH_fvarVendorCode.equals(other.RH_fvarVendorCode))
			return false;
		if (RL_fnumPlanQty == null) {
			if (other.RL_fnumPlanQty != null)
				return false;
		} else if (!RL_fnumPlanQty.equals(other.RL_fnumPlanQty))
			return false;
		if (VM_fvarCDesc == null) {
			if (other.VM_fvarCDesc != null)
				return false;
		} else if (!VM_fvarCDesc.equals(other.VM_fvarCDesc))
			return false;
		return true;
	}
	public ReceiveList(String rL_fnumPlanQty, String rH_fvarVendorCode, String rH_fvarInputTime, String vM_fvarCDesc) {
		super();
		RL_fnumPlanQty = rL_fnumPlanQty;
		RH_fvarVendorCode = rH_fvarVendorCode;
		RH_fvarInputTime = rH_fvarInputTime;
		VM_fvarCDesc = vM_fvarCDesc;
	}
	@Override
	public String toString() {
		return "ReceiveList [RL_fnumPlanQty=" + RL_fnumPlanQty + ", RH_fvarVendorCode=" + RH_fvarVendorCode
				+ ", RH_fvarInputTime=" + RH_fvarInputTime + ", VM_fvarCDesc=" + VM_fvarCDesc + "]";
	}

	
	
}
