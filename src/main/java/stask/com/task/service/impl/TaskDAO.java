package stask.com.task.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import stask.com.task.service.TaskVO;
import stask.com.user.service.UserDefaultVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;


@Repository("taskDAO")
public class TaskDAO extends EgovComAbstractDAO{

	public List<?> taskList(UserDefaultVO userSearchVO) {
		// TODO Auto-generated method stub
		return list("staskTaskDAO.taskList",userSearchVO);
	}

	public int taskListTotCnt(UserDefaultVO userSearchVO) {
		// TODO Auto-generated method stub
		return (Integer)select("staskTaskDAO.taskListTotCnt",userSearchVO);
	}

	public void taskInsert(TaskVO taskVO) {
		// TODO Auto-generated method stub
		insert("staskTaskDAO.taskInsert",taskVO);
	}

	public TaskVO taskSelectView(TaskVO taskVO) {
		// TODO Auto-generated method stub
		return (TaskVO)select("staskTaskDAO.taskSelectView",taskVO);
	}

	public void taskUpdate(TaskVO taskVO) {
		// TODO Auto-generated method stub
		update("staskTaskDAO.taskUpdate",taskVO);
	}

	public void taskDelete(TaskVO taskVO) {
		// TODO Auto-generated method stub
		delete("staskTaskDAO.taskDelete",taskVO);
	}



}
