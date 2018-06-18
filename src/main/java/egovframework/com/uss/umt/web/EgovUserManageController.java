package egovframework.com.uss.umt.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springmodules.validation.commons.DefaultBeanValidator;

/*import com.google.gson.Gson;*/


import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.umt.service.EgovUserManageService;
import egovframework.com.uss.umt.service.UserDefaultVO;
import egovframework.com.uss.umt.service.UserManageVO;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
/**
 * 업무사용자관련 요청을  비지니스 클래스로 전달하고 처리된결과를  해당   웹 화면으로 전달하는  Controller를 정의한다
 * @author 공통서비스 개발팀 조재영
 * @since 2009.04.10
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.04.10  조재영          최초 생성
 *   2011.08.26	 정진오			IncludedInfo annotation 추가
 *   2014.12.08	 이기하			암호화방식 변경(EgovFileScrty.encryptPassword)
 *   2015.06.16	 조정국			수정시 유효성체크 후 에러발생 시 목록으로 이동하여 에러메시지 표시
 *   2015.06.19	 조정국			미인증 사용자에 대한 보안처리 기준 수정 (!isAuthenticated)
 *
 * </pre>
 */

@Controller
public class EgovUserManageController {

	/** userManageService */
	@Resource(name = "userManageService")
	private EgovUserManageService userManageService;

	/** cmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "EgovFileMngUtil")
	protected EgovFileMngUtil fileUtil;

	// 첨부파일 관련
	@Resource(name="EgovFileMngService")
	private EgovFileMngService fileMngService;
	
	/** DefaultBeanValidator beanValidator */
	@Autowired
	private DefaultBeanValidator beanValidator;

	

	/**
	 * 사용자목록을 조회한다. (pageing)
	 * @param userSearchVO 검색조건정보
	 * @param model 화면모델
	 * @return cmm/uss/umt/EgovUserManage
	 * @throws Exception
	 */
	@IncludedInfo(name = "업무사용자관리", order = 460, gid = 50)
	@RequestMapping(value = "/uss/umt/EgovUserManage.do")
	public String selectUserList(@ModelAttribute("userSearchVO") UserDefaultVO userSearchVO, ModelMap model,
									HttpServletRequest request) throws Exception {

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
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		//부서 조회
		vo.setCodeId("COM101");
		List<?> depart_result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("depart_result", depart_result); //부서구분
		model.addAttribute("userSearchVO", userSearchVO); //부서구분
		
		if(userSearchVO.getReturnView() != null && !userSearchVO.getReturnView().isEmpty())
			return "forward:"+userSearchVO.getReturnView();
		else
			return "tis/com/manager/personnelAppointment/emplyrState";
	}

	
	/**
	 * 사용자등록화면으로 이동한다.
	 * @param userSearchVO 검색조건정보
	 * @param userManageVO 사용자초기화정보
	 * @param model 화면모델
	 * @return cmm/uss/umt/EgovUserInsert
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovUserInsertView.do")
	public String insertUserView(@ModelAttribute("userSearchVO") UserDefaultVO userSearchVO, @ModelAttribute("userManageVO") UserManageVO userManageVO, Model model,
			 					HttpServletRequest request)throws Exception {

		ComDefaultCodeVO vo = new ComDefaultCodeVO();

		//부서 조회
		vo.setCodeId("COM101");
		List<?> depart_result = cmmUseService.selectCmmCodeDetail(vo);
		//직위 조회
		vo.setCodeId("COM102");
		List<?> postion_result = cmmUseService.selectCmmCodeDetail(vo);
		
				
		//조직정보를 조회 - ORGNZT_ID정보
		vo.setTableNm("COMTNORGNZTINFO");
		List<?> orgnztId_result = cmmUseService.selectOgrnztIdDetail(vo);
		//그룹정보를 조회 - GROUP_ID정보
		vo.setTableNm("COMTNORGNZTINFO");
		List<?> groupId_result = cmmUseService.selectGroupIdDetail(vo);

		model.addAttribute("orgnztId_result", orgnztId_result); //조직정보 목록
		model.addAttribute("groupId_result", groupId_result); //그룹정보 목록

		model.addAttribute("depart_result", depart_result); //부서구분
		model.addAttribute("postion_result", postion_result); //직위구분

		return "egovframework/com/uss/umt/EgovUserInsert";
	}

	/**
	 * 사용자등록처리후 목록화면으로 이동한다.
	 * @param userManageVO 사용자등록정보
	 * @param bindingResult 입력값검증용 bindingResult
	 * @param model 화면모델
	 * @return forward:/uss/umt/EgovUserManage.do
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovUserInsert.do")
	public String insertUser(@ModelAttribute("userManageVO") UserManageVO userManageVO, BindingResult bindingResult, Model model,
			final MultipartHttpServletRequest multiRequest) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		//첨부파일 저장
		List<FileVO> result = null;
		String atchFileId = "";
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
				
		if (!files.isEmpty()) {
			result = fileUtil.parseFileInf(files, "PTR_", 0, "", "");
			atchFileId = fileMngService.insertFileInfs(result);
		}		
		
		userManageVO.setAtchFileId(atchFileId);
		MakeEmployerID(userManageVO);
		userManageService.insertUser(userManageVO);
		userManageVO.setRm("입사");
		userManageService.insertUserHistory(userManageVO);
	    //Exception 없이 진행시 등록성공메시지
		model.addAttribute("resultMsg", "success.common.insert");

		return "forward:/uss/umt/EgovUserManage.do";
	}

	
	//사원 번호 생성
	void MakeEmployerID(UserManageVO userManageVO) throws Exception{
		String empNo = "";
		empNo = userManageVO.getAffiliationId();
		int tmp = userManageVO.getEmplyrBngde().intValue();
		tmp = (tmp /10000)% 100;
		empNo = empNo + String.format("-%02d", tmp);
		tmp = userManageService.selectGetEmplNo(userManageVO);
		empNo = empNo + String.format("%02d", tmp);
		userManageVO.setEmplNo(empNo);
	}
	
	/*사용자 상세조회*/
	@RequestMapping("/uss/umt/EgovUserSelectView.do")
	public String EgovUserSelectView(@ModelAttribute("userManageVO") UserManageVO userManageVO, Model model, HttpServletRequest request ) throws Exception {
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated ) {
    		return "index";
    	}
    	
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		
		//부서 조회
		vo.setCodeId("COM101");
		List<?> depart_result = cmmUseService.selectCmmCodeDetail(vo);
		//직위 조회
		vo.setCodeId("COM102");
		List<?> postion_result = cmmUseService.selectCmmCodeDetail(vo);
		
		userManageVO = userManageService.selectUser(userManageVO.getUniqId());
		
		model.addAttribute("userManageVO", userManageVO);
		model.addAttribute("depart_result", depart_result); //부서구분
		model.addAttribute("postion_result", postion_result); //직위구분
		return "egovframework/com/uss/umt/EgovUserSelect";
	}

	/**
	 * 사용자정보 수정을 위해 사용자정보를 상세조회한다.
	 * @param uniqId 상세조회대상 사용자아이디
	 * @param userSearchVO 검색조건
	 * @param model 화면모델
	 * @return uss/umt/EgovUserSelectUpdt
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovUserSelectUpdtView.do")
	public String updateUserView(@ModelAttribute("userManageVO") UserManageVO userManageVO, Model model ) throws Exception {

		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated ) {
    		return "index";
    	}

		String returnView = userManageVO.getReturnView();
    	
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		
		//부서 조회
		vo.setCodeId("COM101");
		List<?> depart_result = cmmUseService.selectCmmCodeDetail(vo);
		//직위 조회
		vo.setCodeId("COM102");
		List<?> postion_result = cmmUseService.selectCmmCodeDetail(vo);
		
		userManageVO = userManageService.selectUser(userManageVO.getUniqId());
		userManageVO.setReturnView(returnView); 
		model.addAttribute("userManageVO", userManageVO);
		model.addAttribute("depart_result", depart_result); //부서구분
		model.addAttribute("postion_result", postion_result); //직위구분
		
		return "egovframework/com/uss/umt/EgovUserSelectUpdt";
	}

	/**
	 * 사용자정보 수정후 목록조회 화면으로 이동한다.
	 * @param userManageVO 사용자수정정보
	 * @param bindingResult 입력값검증용 bindingResult
	 * @param model 화면모델
	 * @return forward:/uss/umt/EgovUserManage.do
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovUserSelectUpdt.do")
	public String updateUser(@ModelAttribute("userManageVO") UserManageVO userManageVO, BindingResult bindingResult, Model model,
			final MultipartHttpServletRequest multiRequest) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}
		
		List<FileVO> result = null;
		String atchFileId = userManageVO.getAtchFileId();
		
		final Map<String, MultipartFile> files = multiRequest.getFileMap();

		// 첨부 파일이 있을 때만 동작
				if (!files.isEmpty()) {
					String tmpFileId="";
					// 기존 첨부 파일에 대한 처리
					if("".equals(atchFileId)) {
						result = fileUtil.parseFileInf(files, "PTR_", 0, atchFileId, "");
						tmpFileId = fileMngService.insertFileInfs(result);
						userManageVO.setAtchFileId(tmpFileId);
					} else {
						FileVO fvo = new FileVO();
						fvo.setAtchFileId(atchFileId);
						int cnt = fileMngService.getMaxFileSN(fvo);
						result = fileUtil.parseFileInf(files, "PTR_", cnt, atchFileId, "");
						fileMngService.updateFileInfs(result);
					}
				}
		
		if(!userManageVO.getManageAt().equals("Y")){
			UserManageVO tmpVO = userManageService.selectUser(userManageVO.getUniqId());
			userManageVO.setManageAt(tmpVO.getManageAt());
		}
				
		userManageService.updateUser(userManageVO);
		
		if(userManageVO.getEmplyrEndde() != null){
			userManageVO.setRm("퇴사");
			userManageService.insertLeavingUserHistory(userManageVO);
		}
			
		
		if(userManageVO.getReturnView() != null && !userManageVO.getReturnView().isEmpty())
			return "forward:"+userManageVO.getReturnView();
		else
			return "forward:/uss/umt/EgovUserManage.do";
	}

	/**
	 * 사용자정보삭제후 목록조회 화면으로 이동한다.
	 * @param checkedIdForDel 삭제대상아이디 정보
	 * @param userSearchVO 검색조건
	 * @param model 화면모델
	 * @return forward:/uss/umt/EgovUserManage.do
	 * @throws Exception
	 */
	@RequestMapping("/uss/umt/EgovUserDelete.do")
	public String deleteUser(@RequestParam("checkedIdForDel") String checkedIdForDel, @ModelAttribute("searchVO") UserDefaultVO userSearchVO, Model model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		userManageService.deleteUser(checkedIdForDel);
		//Exception 없이 진행시 등록성공메시지
		model.addAttribute("resultMsg", "success.common.delete");
		return "forward:/uss/umt/EgovUserManage.do";
	}

	/**
	 * 입력한 사용자아이디의 중복확인화면 이동
	 * @param model 화면모델
	 * @return uss/umt/EgovIdDplctCnfirm
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/umt/EgovIdDplctCnfirmView.do")
	public String checkIdDplct(ModelMap model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		model.addAttribute("checkId", "");
		model.addAttribute("usedCnt", "-1");
		return "egovframework/com/uss/umt/EgovIdDplctCnfirm";
	}

	/**
	 * 입력한 사용자아이디의 중복여부를 체크하여 사용가능여부를 확인
	 * @param commandMap 파라메터전달용 commandMap
	 * @param model 화면모델
	 * @return uss/umt/EgovIdDplctCnfirm
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/umt/EgovIdDplctCnfirm.do")
	public String checkIdDplct(@RequestParam Map<String, Object> commandMap, ModelMap model) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		String checkId = (String) commandMap.get("checkId");
		checkId = new String(checkId.getBytes("ISO-8859-1"), "UTF-8");

		if (checkId == null || checkId.equals(""))
			return "forward:/uss/umt/EgovIdDplctCnfirmView.do";

		int usedCnt = userManageService.checkIdDplct(checkId);
		model.addAttribute("usedCnt", usedCnt);
		model.addAttribute("checkId", checkId);

		return "egovframework/com/uss/umt/EgovIdDplctCnfirm";
	}

	/**
	 * 업무사용자 암호 수정처리 후 화면 이동
	 * @param model 화면모델
	 * @param commandMap 파라메터전달용 commandMap
	 * @param userSearchVO 검색조 건
	 * @param userManageVO 사용자수정정보(비밀번호)
	 * @return uss/umt/EgovUserPasswordUpdt
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/umt/EgovUserPasswordUpdt.do")
	public String updatePassword(ModelMap model, @RequestParam Map<String, Object> commandMap, @ModelAttribute("searchVO") UserDefaultVO userSearchVO,
			@ModelAttribute("userManageVO") UserManageVO userManageVO) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		String oldPassword = (String) commandMap.get("oldPassword");
		String newPassword = (String) commandMap.get("newPassword");
		String newPassword2 = (String) commandMap.get("newPassword2");
		String uniqId = (String) commandMap.get("uniqId");

		boolean isCorrectPassword = false;
		UserManageVO resultVO = new UserManageVO();
		userManageVO.setPassword(newPassword);
		userManageVO.setOldPassword(oldPassword);
		userManageVO.setUniqId(uniqId);

		String resultMsg = "";
		resultVO = userManageService.selectPassword(userManageVO);
		//패스워드 암호화
		String encryptPass = EgovFileScrty.encryptPassword(oldPassword, userManageVO.getEmplyrId());
		if (encryptPass.equals(resultVO.getPassword())) {
			if (newPassword.equals(newPassword2)) {
				isCorrectPassword = true;
			} else {
				isCorrectPassword = false;
				resultMsg = "fail.user.passwordUpdate2";
			}
		} else {
			isCorrectPassword = false;
			resultMsg = "fail.user.passwordUpdate1";
		}

		if (isCorrectPassword) {
			userManageVO.setPassword(EgovFileScrty.encryptPassword(newPassword, userManageVO.getEmplyrId()));
			userManageService.updatePassword(userManageVO);
			model.addAttribute("userManageVO", userManageVO);
			resultMsg = "success.common.update";
		} else {
			model.addAttribute("userManageVO", userManageVO);
		}
		model.addAttribute("userSearchVO", userSearchVO);
		model.addAttribute("resultMsg", resultMsg);

		return "egovframework/com/uss/umt/EgovUserPasswordUpdt";
	}

	/**
	 * 업무사용자 암호 수정  화면 이동
	 * @param model 화면모델
	 * @param commandMap 파라메터전달용 commandMap
	 * @param userSearchVO 검색조건
	 * @param userManageVO 사용자수정정보(비밀번호)
	 * @return uss/umt/EgovUserPasswordUpdt
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/umt/EgovUserPasswordUpdtView.do")
	public String updatePasswordView(ModelMap model, @RequestParam Map<String, Object> commandMap, @ModelAttribute("searchVO") UserDefaultVO userSearchVO,
			@ModelAttribute("userManageVO") UserManageVO userManageVO) throws Exception {

		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "index";
		}

		String userTyForPassword = (String) commandMap.get("userTyForPassword");
		userManageVO.setUserTy(userTyForPassword);

		model.addAttribute("userManageVO", userManageVO);
		model.addAttribute("userSearchVO", userSearchVO);
		return "egovframework/com/uss/umt/EgovUserPasswordUpdt";
	}
	/*사용자 ID중복확인*/
/*	@RequestMapping(value = "/uss/umt/userIdChk.do", produces = "application/json; charset=utf8")
	public @ResponseBody String userIdChk(ModelMap model, @RequestParam("checkId") String checkId) throws Exception {
		return new Gson().toJson(userManageService.searchId(checkId));
	}*/
}
