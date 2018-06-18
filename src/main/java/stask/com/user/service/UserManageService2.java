package stask.com.user.service;
import java.util.List;




/**
 * 사용자관리에 관한 인터페이스클래스를 정의한다.
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
 *
 * </pre>
 */
public interface UserManageService2  {

	/**
	 * 입력한 사용자아이디의 중복여부를 체크하여 사용가능여부를 확인
	 * @param checkId 중복여부 확인대상 아이디
	 * @return 사용가능여부(아이디 사용회수 int)
	 * @throws Exception
	 */
	//public int checkIdDplct(String checkId) throws Exception;
	
	public List<?> selectUserList(UserDefaultVO userSearchVO) throws Exception;
	
	public int selectUserListTotCnt(UserDefaultVO userSearchVO)  throws Exception;

	public void insertUser(UserManageVO userManageVO) throws Exception;

	public UserManageVO selectUser(UserManageVO userManageVO) throws Exception;

	public void updateUser(UserManageVO userManageVO) throws Exception;

	public void deleteUser(UserManageVO userManageVO) throws Exception;

	


	
	

	

}