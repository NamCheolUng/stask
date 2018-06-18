 package stask.com.login.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import stask.com.login.service.UserLoginService2;
import stask.com.user.service.UserDefaultVO;
import stask.com.user.service.UserManageService2;



@Controller
public class UserLoginController {
	/** userManageService */
	@Resource(name = "userManageService2")
	private UserManageService2 userManageService;
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	
	@Resource(name = "loginService2")
	private UserLoginService2 loginService;

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	@RequestMapping(value = "/com/login/login.do")
	public String login(ModelMap model, @RequestParam(value="loginStatus", required=false, defaultValue="0") int loginStatus ) throws Exception {

		model.addAttribute("msg", loginStatus);
		return "stask/com/login/uLogin";
	}
	
	@RequestMapping(value = "/com/login/uLogin.do")
	public String actionLogin(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request, ModelMap model) throws Exception {
		UserDefaultVO userSearchVO = new UserDefaultVO();
		/** EgovPropertyService */
		userSearchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		userSearchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(userSearchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(userSearchVO.getPageUnit());
		paginationInfo.setPageSize(userSearchVO.getPageSize());

		userSearchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		userSearchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		userSearchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<?> userList = userManageService.selectUserList(userSearchVO);
		model.addAttribute("resultList", userList);
		
		int totCnt = userManageService.selectUserListTotCnt(userSearchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("userSearchVO", userSearchVO);
		
		// 1. 일반 로그인 처리
		LoginVO resultVO = loginService.actionLogin(loginVO);
		if (resultVO != null && resultVO.getuId() != null && !resultVO.getuId().equals("")) {
			// 2-1. 로그인 정보를 세션에 저장			
			/*request.getSession().setAttribute("loginVO", resultVO);*/
//		return "redirect:/com/user/uList.do";
		
		
			
			return "stask/com/user/uList";
		}else{			
			return "forward:/com/login/login.do?loginStatus=1";	
		}

	}
	
/*	@RequestMapping(value = "/com/inc/actionLogout.do")
	public String actionLogout(HttpServletRequest request, ModelMap model) throws Exception {

		request.getSession().setAttribute("loginVO", null);

		return "tis/com/inc/login";
	}*/
}