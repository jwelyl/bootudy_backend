package com.ssafy.bootudy.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.ssafy.bootudy.model.dto.Member;
import com.ssafy.bootudy.model.service.JwtServiceImpl;
import com.ssafy.bootudy.model.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/member")
@CrossOrigin("*")
@Api("사용자 컨트롤러 API V1")
public class MemberController {
	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	private MemberService memberService;

	/* 로그인 */
	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메시지를 반환한다.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) Member member) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			Member loginMember = memberService.login(member);

			if (loginMember != null) {
				String accessToken = jwtService.createAccessToken("memberId", loginMember.getMemberId()); // key, data
				String refreshToken = jwtService.createRefreshToken("memberId", loginMember.getMemberId()); // key, data
				memberService.saveRefreshToken(member.getMemberId(), refreshToken);
				logger.debug("로그인 accessToken 정보 : {}", accessToken);
				logger.debug("로그인 refreshToken 정보 : {}", refreshToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				
				if(!loginMember.isWaiting())
					resultMap.put("message", SUCCESS);
				else
					resultMap.put("message", FAIL);
				
				
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/* 로그아웃 */
	@ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
	@GetMapping("/logout/{memberId}")
	public ResponseEntity<?> removeToken(@PathVariable("memberId") String memberId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;

		try {
			memberService.deleteRefreshToken(memberId);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/* 토큰 재발급 */
	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody Member member, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token");
		logger.debug("token : {}, memberDto : {}", token, member);
		if (jwtService.checkToken(token)) {
			if (token.equals(memberService.getRefreshToken(member.getMemberId()))) {
				String accessToken = jwtService.createAccessToken("memberId", member.getMemberId());
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/* 회원 가입 신청 */
	@PostMapping("/join")
	public ResponseEntity<Map<String, Object>> insert(@RequestBody Member member) {
		int res = memberService.insert(member);
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		
		if(res != 0) {
			resMap.put("message", "success");
			status = HttpStatus.OK;
		}
		
		return new ResponseEntity<Map<String, Object>>(resMap, status);
	}

	/* 회원 1명 정보 가져오기 */
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{memberId}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("memberId") @ApiParam(value = "인증할 회원의 아이디.", required = true) String memberId) {

		logger.debug("memberId : {} ", memberId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;

//		로그인 사용자 정보.
		Member member = memberService.memberInfo(memberId);
		resultMap.put("memberInfo", member);
		resultMap.put("message", SUCCESS);
		status = HttpStatus.ACCEPTED;

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/* 가입한 모든 회원 정보 */
	@GetMapping("/allinfo")
	public ResponseEntity<Page<Member>> allMembersInfo() {
		HttpStatus status = HttpStatus.UNAUTHORIZED;

		Page<Member> res = memberService.allMembersInfo();
		logger.info("res : {}", res);
		status = HttpStatus.ACCEPTED;

		return new ResponseEntity<Page<Member>>(res, status);
	}

	/* memberId가 관리자의 memberId여야 하고 token 또한 해당 관리자의 token과 일치해야 함. */
	/* 가입 대기 중인 회원 정보 */
	@GetMapping("/waitinginfo")
	public ResponseEntity<Page<Member>> waitingMembersInfo() {
		Page<Member> res = null;
		HttpStatus status = HttpStatus.UNAUTHORIZED;

		// 로그인 사용자 정보.
		res = memberService.waitingMembersInfo();
		status = HttpStatus.ACCEPTED;

		return new ResponseEntity<Page<Member>>(res, status);
	}

	// 회원 역할은 관리자만 수정할 수 있음
	/* memberId가 관리자의 memberId여야 하고 token 또한 해당 관리자의 token과 일치해야 함. */
	/* 회원 역할 수정 */
	@PutMapping("/update/role/{memberId}")
	public ResponseEntity<Page<Member>> updateRole(@RequestBody Member member, HttpServletRequest request,
			@PathVariable("memberId") String memberId) throws Exception {

		Member adminMember = memberService.memberInfo(memberId);
		String loginToken = request.getHeader("access-token");
		Page<Member> res = null;
		HttpStatus status = HttpStatus.UNAUTHORIZED;

		// 로그인한 아이디의 역할이 관리자가 아닐경우
		if (!adminMember.getRole().equals("administrator")) {
			logger.info("관리자 id가 아님!!!");
			return new ResponseEntity<Page<Member>>(res, status);
		}

		if (jwtService.checkToken(loginToken)) {
			try {
				if (loginToken.equals(adminMember.getToken())) {
					// 로그인 사용자 정보.
					memberService.update(member);
					res = memberService.allMembersInfo();
					status = HttpStatus.ACCEPTED;
				} else {
					logger.info("토큰 불일치!!!");
				}
			} catch (Exception e) {
				logger.error("회원 역할 수정 실패 : {}", e);
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Page<Member>>(res, status);
	}

	// 회원 정보는 회원이 자기 정보만 수정할 수 있음
	/* member의 token이 header의 토큰과도 일치하는지 확인 */
	/* 회원 정보 수정 */
	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Member member) throws Exception {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;

		// 로그인 사용자 정보.
		int res = memberService.update(member);
		if(res != 0) {
			resMap.put("message", "success");
		}
			
		status = HttpStatus.ACCEPTED;

		return new ResponseEntity<Map<String, Object>>(resMap, status);
	}

	// 회원가입 승인은 관리자가 할 수 있음
	/* adminId가 회원 id인지 확인 */
	/* 헤더의 토큰이 유효한지, adminId에 해당하는 회원 토큰과 일치하는 지 확인 */
	/* 회원 가입 승인 */
	@PutMapping("/approve/{memberId}")
	public ResponseEntity<Page<Member>> update(@PathVariable("memberId") String memberId) throws Exception {
		Page<Member> res = null;
		HttpStatus status = HttpStatus.UNAUTHORIZED;

		memberService.approve(memberId);
		res = memberService.allMembersInfo();
		status = HttpStatus.ACCEPTED;
		
		return new ResponseEntity<Page<Member>>(res, status);
	}

	// 회원삭제는 본인이 본인만을 삭제하거나 관리자만 할 수 있음
	/* deleterId가 일반 회원의 id일 경우 targetId가 deleterId와 같은지 확인 */
	/* */
	/* 회원 삭제 */
	@DeleteMapping("/delete/{memberId}")
	public ResponseEntity<Page<Member>> delete(@PathVariable("memberId") String memberId) throws Exception {
		Page<Member> res = null;
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		
		memberService.delete(memberId);
		res = memberService.allMembersInfo();
		status = HttpStatus.ACCEPTED;

		return new ResponseEntity<Page<Member>>(res, status);
	}
	
	@GetMapping("/memberWithId/{memberId}")
	public ResponseEntity<Page<Member>> memberWithId(@PathVariable("memberId") String memberId) {
		Page<Member> res = null;
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		
		if(memberId != null && !memberId.equals("") && !memberId.equals("none"))
			res = memberService.memberWithId(memberId);
		else
			res = memberService.allMembersInfo();
		status = HttpStatus.ACCEPTED;
		
		return new ResponseEntity<Page<Member>>(res, status);
	}
	
	@GetMapping("/waitingMemberWithId/{memberId}")
	public ResponseEntity<Page<Member>> waitingMemberWithId(@PathVariable("memberId") String memberId) {
		Page<Member> res = null;
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		
		if(memberId != null && !memberId.equals("") && !memberId.equals("none"))
			res = memberService.waitingMemberWithId(memberId);
		else
			res = memberService.waitingMembersInfo();
	
		status = HttpStatus.ACCEPTED;
		
		return new ResponseEntity<Page<Member>>(res, status);
	}
}
