package com.huaruan.qhg.bean;

import java.util.List;
/**
 * ���ڷ�ҳ
 * @author yangjian
 *
 */
public class PageBean {

	private int currPage;  //��ǰҳ
	private int totalPage; //��ҳ��
	private int allCount;     //�ܼ�¼��
	private int pageSize;  //ÿҳ��ʾ����
	private List<User> userList; //���ڴ洢ÿҳ��ʾ��User�б�
	
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getAllCount() {
		return allCount;
	}
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	

}
