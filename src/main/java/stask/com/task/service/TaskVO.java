package stask.com.task.service;

import stask.com.user.service.UserDefaultVO;

public class TaskVO extends UserDefaultVO{
	
	private String tSeq="";
	private String tNm;
	private String tSdate;
	private String tEdate;
	public String gettSeq() {
		return tSeq;
	}
	public void settSeq(String tSeq) {
		this.tSeq = tSeq;
	}
	public String gettNm() {
		return tNm;
	}
	public void settNm(String tNm) {
		this.tNm = tNm;
	}
	public String gettSdate() {
		return tSdate;
	}
	public void settSdate(String tSdate) {
		this.tSdate = tSdate;
	}
	public String gettEdate() {
		return tEdate;
	}
	public void settEdate(String tEdate) {
		this.tEdate = tEdate;
	}

}
