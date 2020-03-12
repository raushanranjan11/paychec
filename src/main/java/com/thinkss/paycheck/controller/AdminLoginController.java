package com.thinkss.paycheck.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.thinkss.paycheck.bean.DocumentSubTypeBean;
import com.thinkss.paycheck.bean.DocumentTypeBean;
import com.thinkss.paycheck.entity.DocumentDetails;
import com.thinkss.paycheck.entity.DocumentSubType;
import com.thinkss.paycheck.entity.DocumentType;
import com.thinkss.paycheck.entity.Loan;
import com.thinkss.paycheck.entity.LoanStatus;
import com.thinkss.paycheck.entity.LoanStatusHistory;
import com.thinkss.paycheck.entity.User;
import com.thinkss.paycheck.repository.BankRepository;
import com.thinkss.paycheck.repository.DocumentDetailRepository;
import com.thinkss.paycheck.repository.DocumentSubTypeRepository;
import com.thinkss.paycheck.repository.DocumentTypeRepository;
import com.thinkss.paycheck.repository.LoanStatusHistoryRepository;
import com.thinkss.paycheck.repository.LoanStatusRepositorty;
//import com.thinkss.paycheck.repository.UserDocumentDetailsRepository;
import com.thinkss.paycheck.repository.UserRepository;
import com.thinkss.paycheck.repository.UsersLoanRepository;
import com.thinkss.paycheck.service.DocumentService;
import com.thinkss.paycheck.service.LoanService;
import com.thinkss.paycheck.service.UserService;
import com.thinkss.paycheck.view.LoanAppliedUsersView;

@Controller
//@RestController
public class AdminLoginController {

	@Value("${welcome.message}")
	public String message;

	@Autowired
	private UserService userService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	LoanService loanService;
	@Autowired
	BankRepository bankRepository;
	@Autowired
	UsersLoanRepository userLoanRepository;

	@Autowired
	LoanStatusRepositorty loanStatusRepositorty;

	@Autowired
	LoanStatusHistoryRepository loanstatusHistRepo;

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private DocumentDetailRepository documentDetailRepository;

	@Autowired
	private DocumentTypeRepository documentTypeRepository;
	@Autowired
	private DocumentSubTypeRepository documentSubTypeRepository;

//	@Autowired
//	private UserDocumentDetailsRepository userDocumentDetailsRepository;

	@Autowired
	private DocumentService docservice;

	@RequestMapping(value = {  "/login" }, method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^666");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		user.setUsername(user.getEmailId());
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findByEmailId(user.getEmailId());
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
//			userService.saveAdminUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");

		}
		return modelAndView;
	}

	/*
	 * @RequestMapping(value="/admin", method = RequestMethod.GET) public
	 * ModelAndView home(){ ModelAndView modelAndView = new ModelAndView();
	 * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	 * User user1 = userRepository.findByUsername(auth.getName());
	 * System.out.println(user1); User user =
	 * userService.findUserByEmailId(auth.getName());
	 * modelAndView.addObject("userName", "Welcome " + user.getFirstName()+ " " +
	 * user.getLastName() + " (" + user.getEmailId() + ")"); modelAndView.addObject(
	 * "adminMessage","Content Available Only for Users with Admin Role");
	 * modelAndView.setViewName("admin/index"); return modelAndView; }
	 */

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/index");
		return modelAndView;
	}

	@RequestMapping(value = "/currentUser", method = RequestMethod.GET)
	public ResponseEntity<?> getCurrentUser() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> map = new HashMap<String, Object>();
		if (auth != null) {
			User currentUser = (User) auth.getPrincipal();
			map.put("success", true);
			map.put("user", currentUser);
			return ResponseEntity.ok(map);
		}
		map.put("success", false);
		return ResponseEntity.ok(map);
	}

	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public ResponseEntity<?> getUsersList(HttpServletRequest request, @RequestParam("start") int start,
			@RequestParam("limit") int limit, @RequestParam("page") int pageN, Pageable pageable,
			@RequestParam("sort") String sort, @RequestParam("dir") String dir, @RequestParam("filter") String filter) throws JSONException {
		List<User> userList = userRepository.findAll();
		JSONArray array = new JSONArray(filter);
		List<User> userListfilter = userService.findAllUser(start, limit, dir, sort, array);
		Map<String, Object> map = new HashMap<String, Object>();
		// if(!userList.isEmpty()) {
		map.put("success", true);
		map.put("userList", userListfilter);
		map.put("total", userList.size());
		return ResponseEntity.ok(map);
	}

	@RequestMapping(value = "/appliedLoan", method = RequestMethod.GET) // appliedLoan loanAppliedUser
	public ResponseEntity<?> getLoanAppliedUser(HttpServletRequest request, @RequestParam("start") int start,
			@RequestParam("limit") int limit, @RequestParam("page") int pageN, Pageable pageable,
			@RequestParam("sort") String sort, @RequestParam("dir") String dir, @RequestParam("filter") String filter) throws JSONException {
		List<Loan> totalLoanAppliedUser = loanService.findAll();
		JSONArray array = new JSONArray(filter);
//		List<LoanAppliedUsersView> userLoanfilter = loanService.findAllLoan(start, limit, dir, sort, array);
		List<LoanAppliedUsersView> userLoanfilter = loanService.findAllLoan(start, limit, dir, sort, array);

		Map<String, Object> map = new HashMap<String, Object>();
		// if(!userList.isEmpty()) {
		map.put("success", true);
		map.put("loanAppliedUser", userLoanfilter);
		map.put("total", totalLoanAppliedUser.size());
		return ResponseEntity.ok(map);
	}

	@RequestMapping(value = "/bank", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findAllBank() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", true);
		map.put("bankList", bankRepository.findAll());
		return ResponseEntity.ok(map);

	}

	@RequestMapping(value = "/loanStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findLoanStatus() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", true);
		map.put("loanStatusList", loanService.findLoanStatus());
		return ResponseEntity.ok(map);

	}

	// Not using
	@RequestMapping(value = "/saveStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveStatus(HttpServletRequest request, @RequestParam("status") Long statusId,
			@RequestParam("loanId") Long loanId) {
		System.out.println(statusId);
		loanId = Long.valueOf(1);
		LoanStatus ls = loanStatusRepositorty.findOne(statusId);
		Loan loan = loanService.findLoanById(loanId);
		loan.setLoanStatus(ls);
		Loan savedLoan = loanService.save(loan);
		if (savedLoan != null) {// loanstatusHistRepo
			LoanStatusHistory lsh = new LoanStatusHistory();
			lsh.setStatus(savedLoan.getLoanStatus());
			lsh.setUpdateDate(new Date());
			lsh.setLoan(savedLoan);
			loanstatusHistRepo.save(lsh);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", true);
//		map.put("loanStatusList", loanService.findLoanStatus());
		return ResponseEntity.ok(map);

	}

	@RequestMapping(value = "/statusHistory/{loanId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getStatusHistory(HttpServletRequest request, @PathVariable("loanId") Long loanId) {
		System.out.println("Loan status History");
//		Long loanId = Long.valueOf(request.getParameter("loanId"));
		System.out.println(request.getParameter("loanId"));
		Loan loan = loanService.findLoanById(loanId);
		List<LoanStatusHistory> lsh = loan.getLoanStatusHistory();
//		List<LoanStatusHistory> lsh = loanHistoryRepository.findLoanStatusHistoryByLoan(loan);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", true);
		map.put("lHistoryList", lsh);
		return ResponseEntity.ok(map);

	}

	@RequestMapping(value = "/appliedLoan/{loanId}", method = RequestMethod.PUT)
	public ResponseEntity<?> saveStatus1(HttpServletRequest request, @PathVariable("loanId") Long loanIds,
			@RequestBody LoanAppliedUsersView loanView) {
		LoanStatus ls = loanStatusRepositorty.findOne(loanView.getLoanStatus());
		Loan loan = loanService.findLoanById(loanView.getLoanId());
		loan.setLoanStatus(ls);
		Loan savedLoan = loanService.save(loan);
		if (savedLoan != null) {
			LoanStatusHistory lsh = new LoanStatusHistory();
			lsh.setStatus(savedLoan.getLoanStatus());
			lsh.setLoan(savedLoan);
			lsh.setUpdateDate(new Date());

			loanstatusHistRepo.save(lsh);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", true);
//		map.put("loanStatusList", loanService.findLoanStatus());
		return ResponseEntity.ok(map);

	}

	@RequestMapping(value = "/kyc", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getKYCDoc(HttpServletRequest request) {
		System.out.println("Docuent ");
		DocumentType documentTypes = documentTypeRepository.findOne(Long.valueOf(2));
		List<DocumentSubType> docSubTypeList = documentSubTypeRepository.findBydocumentType(documentTypes);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", true);
		map.put("kycList", docSubTypeList);
		return ResponseEntity.ok(map);

	}

	@RequestMapping(value = "/finance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getFinanceDoc(HttpServletRequest request) {
		System.out.println("Docuent ");
		DocumentType documentTypes = documentTypeRepository.findOne(Long.valueOf(3));
		List<DocumentSubType> docSubTypeList = documentSubTypeRepository.findBydocumentType(documentTypes);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", true);
		map.put("financeList", docSubTypeList);
		return ResponseEntity.ok(map);

	}

	@RequestMapping(value = "/kyc/user/{userId}/document/{documentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getDocument(@PathVariable("documentId") Long documentId,
			@PathVariable("userId") Long userId, HttpServletRequest request) {
		User user = userRepository.findOne(userId);
		DocumentSubType subType = documentSubTypeRepository.findOne(documentId);
		DocumentDetails docDetail = documentDetailRepository.findByDocumentSubTypeAndUser(subType, user);
//		List<LoanStatusHistory> lsh = loan.getLoanStatusHistory();
//		List<LoanStatusHistory> lsh = loanHistoryRepository.findLoanStatusHistoryByLoan(loan);
		Map<String, Object> map = new HashMap<String, Object>();

		if (docDetail != null) {
			map.put("status", true);
			map.put("docSource", docDetail.getImageSource());
//			return  ResponseEntity.ok(map);
		} else {
			map.put("status", false);
//			return  (ResponseEntity<?>) ResponseEntity.status(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(map);

	}

	@RequestMapping(value = "/users/{userId}/social", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getSocial(HttpServletRequest request, @PathVariable("userId") Long userId) {
		System.out.println("Docuent ");
		DocumentType documentTypes = documentTypeRepository.findOne(Long.valueOf(1));
		List<DocumentSubType> docSubTypeList = documentSubTypeRepository.findBydocumentType(documentTypes);
		User user = userRepository.findOne(userId);
//		DocumentSubType subType = documentSubTypeRepository.findOne(documentId);
		List<DocumentDetails> docDetail = documentDetailRepository.findByDcumentTypeAndUser(documentTypes, user);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", true);
		map.put("socialList", docDetail);
		return ResponseEntity.ok(map);

	}

	@RequestMapping(value = "/users/{userId}/documents/{documentId}/subDocument/{subDocumetId}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getDocumets(HttpServletRequest request, @PathVariable("userId") Long userId,
			@PathVariable("documentId") Long documentId, @PathVariable("subDocumetId") Long subDocumetId)
			throws IOException {
		DocumentType documentTypes = documentTypeRepository.findOne(documentId);
		DocumentSubType documentSubType = documentSubTypeRepository.findOne(subDocumetId);
//		User user = userRepository.findOne(userId);
		User user = userRepository.findOne(userId);
		DocumentDetails docDetail = documentDetailRepository.findByDcumentTypeAndDocumentSubTypeAndUser(documentTypes,
				documentSubType, user);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", true);
		map.put("socialList", docDetail);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);

		URL url = new URL(docDetail.getImageSource());
		InputStream in = new BufferedInputStream(url.openStream());

		byte[] media = IOUtils.toByteArray(in);
		headers.set("Content-Disposition", "attachment; filename=\"" + user.getFirstName() + "_" + user.getLastName()
				+ "_" + documentSubType.getName() + ".jpg\"");
		return new ResponseEntity<byte[]>(media, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/users/{userId}/document", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserDetails(HttpServletRequest request, @PathVariable("userId") Long userId) {
		User user = userRepository.findOne(userId);
//		List<DocumentDetails> docDetail = documentDetailRepository.findByUser(user);
		Iterable<DocumentType> listDocType = documentTypeRepository.findAll();
//		List<DocumentType> newList = new ArrayList<DocumentType>();
//		List<UserDocumentDetails > listOfDocs =  userDocumentDetailsRepository.findByUserId(userId);
		List<DocumentTypeBean> listOfDoc = new ArrayList<DocumentTypeBean>();
		for (DocumentType udds : listDocType) {
			DocumentTypeBean docBean = new DocumentTypeBean();
			docBean.setId(udds.getId());
			docBean.setName(udds.getName());
			List<DocumentSubType> listOfsubDocs = udds.getDocumentList();
			List<DocumentSubTypeBean> listOfsubDoc = new ArrayList<DocumentSubTypeBean>();
			for (DocumentSubType subType : listOfsubDocs) {
				DocumentDetails docDetail1 = documentDetailRepository.findByDcumentTypeAndDocumentSubTypeAndUser(udds,
						subType, user);
				DocumentSubTypeBean subBean = new DocumentSubTypeBean();
				subBean.setId(subType.getId());
				subBean.setName(subType.getName());
				if (docDetail1 != null)
					subBean.setValue(docDetail1.getImageSource());
				listOfsubDoc.add(subBean);
			}
			docBean.setDocumentSubTypeBean(listOfsubDoc);
			listOfDoc.add(docBean);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", true);
		map.put("documentList", listOfDoc);
		return ResponseEntity.ok(map);

	}
	
	@RequestMapping(value = "/users/{userId}/loan/{loanId}/status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateLoanStatus(HttpServletRequest request, @PathVariable("userId") Long userId,
			@PathVariable("loanId") Long loanId,
			@RequestBody Map<String, String> reqMap) {
		System.out.println(userId);
		System.out.println(loanId);
		System.out.println(reqMap.get("messageText"));
		String message = reqMap.get("messageText");
		Long statusId  = Long.valueOf(reqMap.get("loanstatus"));
		User user = userRepository.findOne(userId);
		LoanStatus ls = loanStatusRepositorty.findOne(statusId);
		Loan loan = loanService.findLoanById(loanId);
//		loan.setLoanStatus(ls);
		Map<String, Object> map = new HashMap<String, Object>();
//		Loan savedLoan = loanService.save(loan);
		if (loan != null) {
			LoanStatusHistory lsh = new LoanStatusHistory();
			lsh.setStatus(ls);
			lsh.setLoan(loan);
			lsh.setUpdateDate(new Date());
			lsh.setMessage(message);

			loanstatusHistRepo.save(lsh);
			map.put("status", true);
//			map.put("status", true);
			return ResponseEntity.ok(map);
		}
		else {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	
		
	}
	
	@RequestMapping(value = "/statusHistories/{loanId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> statusHistories(HttpServletRequest request, @PathVariable("loanId") Long loanId) {
		System.out.println("Loan status History");
//		Long loanId = Long.valueOf(request.getParameter("loanId"));
		System.out.println(request.getParameter("loanId"));
		Loan loan = loanService.findLoanById(loanId);
		List<LoanStatusHistory> lsh = loan.getLoanStatusHistory();
//		List<LoanStatusHistory> lsh = loanHistoryRepository.findLoanStatusHistoryByLoan(loan);
		for(LoanStatusHistory lsh1:lsh) {
//			lsh1.getStatus()
			
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", true);
		map.put("lHistoryList", lsh);
		return ResponseEntity.ok(map);

	}

}
