package com.thinkss.paycheck.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thinkss.paycheck.bean.FileUploadForm;
import com.thinkss.paycheck.bean.PreviousAppliedLoanBean;
import com.thinkss.paycheck.entity.LoanType;
import com.thinkss.paycheck.entity.PreviousAppliedLoan;
import com.thinkss.paycheck.entity.User;
import com.thinkss.paycheck.entity.UserSignInToken;
import com.thinkss.paycheck.repository.DocumentDetailRepository;
import com.thinkss.paycheck.repository.DocumentSubTypeRepository;
import com.thinkss.paycheck.repository.DocumentTypeRepository;
import com.thinkss.paycheck.repository.PreviousAppliedLoanRepository;
import com.thinkss.paycheck.response.model.DocumentMessageResponse;
import com.thinkss.paycheck.service.DocumentService;
import com.thinkss.paycheck.service.LoanService;
import com.thinkss.paycheck.service.UserService;
import com.thinkss.paycheck.util.StaticIP;

@RestController
@RequestMapping(value = "/oldLoan", produces = MediaType.APPLICATION_JSON_VALUE)
public class OldLoanController {

	@Autowired
	private UserService userService;

	@Autowired
	private DocumentDetailRepository documentDetailRepository;

	@Autowired
	private DocumentTypeRepository documentTypeRepository;
	@Autowired
	private DocumentSubTypeRepository documentSubTypeRepository;

	@Autowired
	private PreviousAppliedLoanRepository appliedLoanRepository;

	@Autowired
	private DocumentService docservice;

	@Autowired
	LoanService loanService;

	@RequestMapping(value = "/user/{id}/apply", method = RequestMethod.POST) // PreviousAppliedLoan
	public ResponseEntity<?> savePreviousLoan(@PathVariable("id") long id,
			@RequestBody PreviousAppliedLoanBean appliedLoan, HttpServletRequest request) throws IOException {
		User user = userService.findById(id);
		if (user != null) {
			LoanType loanType = loanService.findLoanTypeById(appliedLoan.getLoanTypeId());
			PreviousAppliedLoan previousAppliedLoan = new PreviousAppliedLoan();
			previousAppliedLoan.setLoanType(loanType);
			previousAppliedLoan.setLoanAmount(appliedLoan.getLoanAmount());
			previousAppliedLoan.setInterestRate(appliedLoan.getInterestRate());
			previousAppliedLoan.setNumberOfMonth(appliedLoan.getNumberOfMonth());
			previousAppliedLoan.setMonthlyPayments(appliedLoan.getMonthlyPayments());
			
			
//			previousAppliedLoan.setDocumentList(appliedLoan.getDocument());
			previousAppliedLoan.setUser(user);
			
			System.out.println(appliedLoan.getDocument());
			System.out.println(appliedLoan.getDocument().toString());
			System.out.println(appliedLoan.getDocument().length);
			System.out.println(Arrays.asList(appliedLoan.getDocument()));
			List lt = Arrays.asList(appliedLoan.getDocument());
			System.out.println(lt.toArray().toString());
			JSONArray mJSONArray = new JSONArray(lt);
			System.out.println(mJSONArray);
			previousAppliedLoan.setDocumentList(lt);
			
			 
//			array.optJSONObject(appliedLoan.getDocument());

			PreviousAppliedLoan savedOldLoan = appliedLoanRepository.save(previousAppliedLoan);
//			PreviousAppliedLoan savedOldLoan = null;

			if (savedOldLoan != null) {
				List<PreviousAppliedLoan> list = appliedLoanRepository.findOldLoanByUser(user);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("oldLoan", list);
				map.put("status", true);
				map.put("message", "Your records is  saved.");
//				return ResponseEntity.ok(new UserSignInToken(true, "Your records is  saved."));
				return ResponseEntity.ok(map);

			} else {
				return ResponseEntity.ok(new UserSignInToken(false, "Your records is not saved."));
			}

		} else {
			return ResponseEntity.ok(new UserSignInToken(false, "User Not found "));
		}

	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)  
	public ResponseEntity<?> getPreviousLoan(@PathVariable("id") long id, HttpServletRequest request)
			throws IOException {
		User user = userService.findById(id);
		if (user != null) {
			List<PreviousAppliedLoan> list = appliedLoanRepository.findOldLoanByUser(user);
			System.out.println(list.size());
			Map<String, Object> map = new HashMap<String, Object>();
			if(list.isEmpty()) {
				map.put("oldLoan", list);
				map.put("status", false);
			}else {
				map.put("oldLoan", list);
				map.put("status", true);
			}
			
			return ResponseEntity.ok(map);
		} else {
			return ResponseEntity.ok(new UserSignInToken(false, "User Not found "));
		}

	}

	@RequestMapping(value = "/user/{id}/uploadDocuments", method = RequestMethod.POST)
	public ResponseEntity<?> uploadDocument(@PathVariable("id") long id, @RequestParam("file") MultipartFile file,
			HttpServletRequest request) throws IOException {
		User currentUser = userService.findById(id);

		if (currentUser == null) {
			return ResponseEntity.ok(new UserSignInToken(false, "User Not found "));
		}

		Map<String, Object> map = new HashMap<String, Object>();
		String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
		if (!file.isEmpty()) {
			if (fileType.equalsIgnoreCase("pdf") || fileType.equalsIgnoreCase("png")
					|| fileType.equalsIgnoreCase("jpg")) {
				String contextPath = request.getContextPath();
				try {
					String folder = "/opt/image/oldLoan/" ;//+ currentUser.getId() + "/";
					String accessfolder = "/resources/image/oldLoan/";

					String imageName = saveImage(file, request, folder, currentUser, fileType);

					map.put("status", true);
					map.put("path", accessfolder +imageName+"."+ fileType);
					return ResponseEntity.ok(map);
				} catch (Exception e) {
					return ResponseEntity.ok(new DocumentMessageResponse(false, e.getMessage()));
				}
			} else {
				return ResponseEntity.ok(new DocumentMessageResponse(false, "Not valid format"));

			}
		} else {
			return ResponseEntity.ok(new DocumentMessageResponse(false, "No file selected"));
		}

	}

	private String  saveImage(MultipartFile file, HttpServletRequest request, String folder, User currentUser,
			String fileType) throws IOException {
		HttpSession session = request.getSession();
		ServletContext sc = session.getServletContext();
		// File file1 = new File(sc.getRealPath("/") + folder);
		// String filePath = "/opt/image";
		File file1 = new File(folder);
		if (!file1.exists()) // {
			file1.mkdirs();
		// }
//		Date date = new Date();
		byte[] bytes = file.getBytes();
		// Instantiate a Date object
        Date date = new Date(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
        System.out.println(df.format(new Date()) );
        String fileName = df.format(new Date()).toString();
//        String dateString = new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date());
//		BufferedOutputStream stream = new BufferedOutputStream( new FileOutputStream(new File(file1 + "/" + file.getOriginalFilename() + "." + fileType)));
		BufferedOutputStream stream = new BufferedOutputStream( new FileOutputStream(new File(file1 + "/" + fileName + "." + fileType)));
		stream.write(bytes);
		stream.close();
		return fileName;
//		System.out.println("********************************************************");

	}
	
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	@RequestMapping(value = "/user/{id}/multiUpload", method = RequestMethod.POST)
	public ResponseEntity<?> uploadMultiDocument(@PathVariable("id") long id, @RequestParam("file")FileUploadForm file,
			HttpServletRequest request) throws IOException {
		User currentUser = userService.findById(id);

		if (currentUser == null) {
			return ResponseEntity.ok(new UserSignInToken(false, "User Not found "));
		}
		System.out.println(file.getFiles());

		Map<String, Object> map = new HashMap<String, Object>();
//		String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
		if (true) {
			/*if (fileType.equalsIgnoreCase("pdf") || fileType.equalsIgnoreCase("png")
					|| fileType.equalsIgnoreCase("jpg")) {
				String contextPath = request.getContextPath();
				try {
					String folder = "/opt/image/oldLoan/" ;//+ currentUser.getId() + "/";
					String accessfolder = "/resources/image/oldLoan/";

					String imageName = saveImage(file, request, folder, currentUser, fileType);

					map.put("status", true);
					map.put("path", accessfolder +imageName+"."+ fileType);
					return ResponseEntity.ok(map);
				} catch (Exception e) {
					return ResponseEntity.ok(new DocumentMessageResponse(false, e.getMessage()));
				}
			} else {
				return ResponseEntity.ok(new DocumentMessageResponse(false, "Not valid format"));

			}*/
			return null;
		} else {
			return ResponseEntity.ok(new DocumentMessageResponse(false, "No file selected"));
		}

	}
	
	@RequestMapping(value = "user/{id}/loan/{oldloanId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePreviousLoan(@PathVariable("id") long id,@PathVariable("oldloanId") long oldloanId){
		
		User currentUser = userService.findById(id);
		
//		loanService.deleteOldLoanByUser(currentUser,oldloanId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		PreviousAppliedLoan loan = appliedLoanRepository.findOne(oldloanId);
		if(loan != null) {
		
//		appliedLoanRepository.delete(oldloanId);
			loanService.deleteOldLoanByUser(currentUser,oldloanId);
		map.put("status", true);
		map.put("message", "Records is deleted");
		}else {
			map.put("status", false);
			map.put("message", "Records is not found");
		}
		
//		return null;
		return ResponseEntity.ok(map);
	}
//		return null;
//	}

}
