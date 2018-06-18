package stask.com.task.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import stask.com.task.service.TaskService;
import stask.com.task.service.TaskVO;
import stask.com.user.service.UserDefaultVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("taskService")
public class TaskServiceimpl extends EgovAbstractServiceImpl implements TaskService{

	@Resource(name="taskDAO")
	private TaskDAO taskDAO;

	@Override
	public List<?> taskList(UserDefaultVO userSearchVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.taskList(userSearchVO);
	}

	@Override
	public int taskListTotCnt(UserDefaultVO userSearchVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.taskListTotCnt(userSearchVO);
	}

	@Override
	public void taskInsert(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		taskDAO.taskInsert(taskVO);
	}

	@Override
	public TaskVO taskSelectView(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		return taskDAO.taskSelectView(taskVO);
	}

	@Override
	public void taskUpdate(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		taskDAO.taskUpdate(taskVO);
	}

	@Override
	public void taskDelete(TaskVO taskVO) throws Exception {
		// TODO Auto-generated method stub
		taskDAO.taskDelete(taskVO);
	}


	

}
