package com.whty.euicc.common.base;

/**
 * 构建zTree数据
 * @ClassName: ZTree  
 * @author liyang
 * @date 2015-4-17
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class ZTree {
	
	/**
	 * 树节点
	 */
	private String id;
	
	/**
	 * 父节点
	 */
	private String pId;
	
	/**
	 *名称
	 */
	private String name;
	
	/**
	 * 是否选中
	 */
	private boolean checked;
	
	/**
	 * 是否展开
	 */
	private boolean open;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the pId
	 */
	public String getpId() {
		return pId;
	}

	/**
	 * @param pId the pId to set
	 */
	public void setpId(String pId) {
		this.pId = pId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * @return the open
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * @param open the open to set
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}

}
