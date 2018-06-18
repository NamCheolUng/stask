package stask.com.task.service;

import java.util.List;

import stask.com.user.service.UserDefaultVO;

public interface TaskService {


	public List<?> taskList(UserDefaultVO userSearchVO) throws Exception;

	public int taskListTotCnt(UserDefaultVO userSearchVO) throws Exception;

	public void taskInsert(TaskVO taskVO) throws Exception;

	public TaskVO taskSelectView(TaskVO taskVO) throws Exception;

	public void taskUpdate(TaskVO taskVO)throws Exception;

	public void taskDelete(TaskVO taskVO) throws Exception;


	


	

}
