package com.thinkss.paycheck.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.thinkss.paycheck.config.WebSecurityConfig;
import com.thinkss.paycheck.entity.BankDetails;
import com.thinkss.paycheck.entity.DocumentDetails;
import com.thinkss.paycheck.entity.DocumentSubType;
import com.thinkss.paycheck.entity.DocumentType;
import com.thinkss.paycheck.entity.User;
import com.thinkss.paycheck.entity.UserSignInToken;
import com.thinkss.paycheck.exception.FileExceedException;
import com.thinkss.paycheck.repository.DocumentDetailRepository;
import com.thinkss.paycheck.repository.DocumentSubTypeRepository;
import com.thinkss.paycheck.repository.DocumentTypeRepository;
import com.thinkss.paycheck.response.model.DocumentMessageResponse;
import com.thinkss.paycheck.security.TokenHelper;
import com.thinkss.paycheck.service.DocumentService;
import com.thinkss.paycheck.service.UserService;
import com.thinkss.paycheck.service.impl.CustomUserDetailsService;
import com.thinkss.paycheck.util.StaticIP;

/**
 * Created by raushan ranjan on 13-01-2018.
 */

@RestController
@RequestMapping(value = "/document", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentController {

	private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

	@Autowired
	TokenHelper tokenHelper;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
	private DocumentDetailRepository documentDetailRepository;

	@Autowired
	private DocumentTypeRepository documentTypeRepository;
	@Autowired
	private DocumentSubTypeRepository documentSubTypeRepository;

	@Autowired
	private DocumentService docservice;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/profilePic/user/{id}", method = RequestMethod.POST)
//	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> saveProfilePics(@PathVariable("id") long id, @RequestParam("file") MultipartFile file,
			HttpServletRequest request) throws IOException {
		System.out.println("$$$$$$$$$$$$$$$$");
		final long limit = 2 * 1024 * 1024;    // 2 MB
	    if (file.getSize() > limit) {
	        throw new MaxUploadSizeExceededException(limit);
	    }

		User currentUser = userService.findById(id);
		if (currentUser == null) {
			return ResponseEntity.ok(new UserSignInToken(false, "User Not found "));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
		// String folder = "/image/profile_pic/";
		String folder = "/opt/image/profile_pic/";
		if (!file.isEmpty()) {
			if (fileType.equalsIgnoreCase("png") || fileType.equalsIgnoreCase("jpg") ||
					 fileType.equalsIgnoreCase("jpeg")) {
				try {
					saveImage(file, request, folder, currentUser, fileType);
					String contextPath = request.getContextPath();
					String folder1 = "/resources/image/profile_pic/";
					currentUser.setProfilePic(StaticIP.IP
							.concat(contextPath.concat(folder1).concat(currentUser.getEmailId()) + "." + fileType));
					/*
					 * currentUser.setProfilePic(StaticIP.IP
					 * .concat(contextPath.concat(folder1).concat(file.getOriginalFilename())));
					 */
					userService.save(currentUser);
					map.put("status", true);
					map.put("profileImg", currentUser.getProfilePic());
					String message = "Profile Picture is updated";

					return ResponseEntity.ok(new UserSignInToken(true, currentUser.getProfilePic(), message));
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

	@RequestMapping(value = "/kycDocument/user/{id}", method = RequestMethod.POST)
//	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> saveDocuments(@PathVariable("id") long id, @RequestParam("file") MultipartFile file,
			HttpServletRequest request, @RequestParam("imageSide") String imageSide) throws IOException {
		User currentUser = userService.findById(id);
		if (currentUser == null) {
			return ResponseEntity.ok(new UserSignInToken(false, "User Not found "));
		}
		Map<String, String> map = new HashMap<String, String>();
		String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
		// String folder = "/image/kyc_document/" + imageSide + "/";
		String folder = "/opt/image/kyc_document/" + imageSide + "/";

		System.out.println(fileType);

		if (!file.isEmpty()) {
			if (fileType.equalsIgnoreCase("pdf") || fileType.equalsIgnoreCase("png") || fileType.equalsIgnoreCase("jpg")
					|| fileType.equalsIgnoreCase("docx") || fileType.equalsIgnoreCase("doc")) {// png doc //jpg
				try {
					String folder1 = "/resources/image/kyc_document/" + imageSide + "/";
					saveImage(file, request, folder, currentUser, fileType);
					String contextPath = request.getContextPath();
					StringBuilder path = new StringBuilder();
					if (imageSide.equals("front")) {

						currentUser.setKycFrontPic(StaticIP.IP
								.concat(contextPath.concat(folder1).concat(currentUser.getEmailId()) + "." + fileType));

						/*
						 * currentUser.setProfilePic(StaticIP.IP
						 * .concat(contextPath.concat(folder1).concat(file.getOriginalFilename())));
						 */
					}
					if (imageSide.equals("back")) {
						currentUser.setKycBackPic(StaticIP.IP
								.concat(contextPath.concat(folder1).concat(currentUser.getEmailId()) + "." + fileType));
					}
					// System.out.println(path);

					User u = userService.save(currentUser);
					return ResponseEntity.ok(new UserSignInToken(true, u.getKycFrontPic(), u.getKycBackPic(),
							imageSide + " side of KYC is Updated"));

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

	@RequestMapping(value = "/upload/user/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> uploadDocument(@PathVariable("id") long id, @RequestParam("file") MultipartFile file,
			HttpServletRequest request, @RequestParam("documentType") Long documentTypeId,
			@RequestParam("documentSubType") Long documentSubTypeId
	// ,@RequestParam("tokenId") String tokenId
	) throws IOException {
System.out.println("****************************8");
		User currentUser = userService.findById(id);

		if (currentUser == null) {
			return ResponseEntity.ok(new UserSignInToken(false, "User Not found "));
		}

		Map<String, Object> map = new HashMap<String, Object>();
		String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
		if (!file.isEmpty()) {
			if (fileType.equalsIgnoreCase("pdf") || fileType.equalsIgnoreCase("png") || fileType.equalsIgnoreCase("jpg")
			// || fileType.equals("docx") || fileType.equals("doc")
			) {
				String contextPath = request.getContextPath();
				try {
					System.out.println(documentTypeId);
					String folder = null;

					if (documentTypeId != null) {
						if (documentSubTypeId != null) {
							DocumentType documentTypes = documentTypeRepository.findOne(documentTypeId);
							DocumentSubType documentSubType = documentSubTypeRepository.findOne(documentSubTypeId);
							// folder = "/image/" + documentTypes.getName() + "/" +
							// documentSubType.getLocation() + "/";
							folder = "/opt/image/" + documentTypes.getName() + "/" + documentSubType.getLocation()
									+ "/";
							// String folder = "/opt/image/profile_pic/";

							DocumentDetails docDetail = docservice.findDocumentDetailByUser(currentUser, documentTypes,
									documentSubType);
							System.out.println(docDetail);
							String folder1 = "/resources/image/" + documentTypes.getName() + "/"
									+ documentSubType.getLocation() + "/";
							if (docDetail == null) {
								DocumentDetails di = new DocumentDetails();
								di.setDcumentType(documentTypes);
								di.setDocumentSubType(documentSubType);
								di.setUser(currentUser);
								di.setImageSource(StaticIP.IP.concat(
										contextPath.concat(folder1).concat(currentUser.getEmailId()) + "." + fileType));
								documentDetailRepository.save(di);
								// map.put(di.getDocumentSubType().getName(), di.getImageSource());
								map.put("documentSource", di.getImageSource());
								map.put("message", di.getDocumentSubType().getName() + " documents updated.");
							} else {
								docDetail.setImageSource(StaticIP.IP.concat(
										contextPath.concat(folder1).concat(currentUser.getEmailId()) + "." + fileType));
								documentDetailRepository.save(docDetail);
								// map.put(docDetail.getDocumentSubType().getName(),
								// docDetail.getImageSource());
								map.put("documentSource", docDetail.getImageSource());
								map.put("message", docDetail.getDocumentSubType().getName() + " documents updated.");
							}
						}
					}
					saveImage(file, request, folder, currentUser, fileType);

					// return ResponseEntity.ok(new UserSignInToken(true, u.getKycFrontPic(),
					// u.getKycBackPic(),
					// imageSide + " of KYC is Updated"));
					// map.put("message", "");
					map.put("status", true);
					return ResponseEntity.ok(map);
				} catch (Exception e) {
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^");
					return ResponseEntity.ok(new DocumentMessageResponse(false, e.getMessage()));
				}
			} else {
				return ResponseEntity.ok(new DocumentMessageResponse(false, "Not valid format"));

			}
		} else {
			return ResponseEntity.ok(new DocumentMessageResponse(false, "No file selected"));
		}

	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET) // method should get
	public ResponseEntity<?> getBankFinance(@PathVariable("id") long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Iterable<DocumentType> docList = documentTypeRepository.findAll();

		Collection<DocumentType> collection = new ArrayList<>();
		docList.forEach(collection::add);
		System.out.println(collection.size());

		List<DocumentType> docList1 = new ArrayList<DocumentType>();
		User currentUser = userService.findById(id);
		for (DocumentType docType : collection) {
			System.out.println(docType.getName());

			List<Object> items = new ArrayList<Object>();
			List<DocumentSubType> listOfSubType = docservice.findByDocumentType(docType);

			for (DocumentSubType subType : listOfSubType) {
				BankDetails bankDetails = new BankDetails();
				boolean status = false;
				DocumentDetails docDetail = docservice.findDocumentDetailByUser(currentUser, docType, subType);
				System.out.println(docDetail);
				if (docDetail != null) {
					if (docDetail.getImageSource() != null && !docDetail.getImageSource().trim().equals("")) {
						status = true;
					}
				}
				bankDetails.setId(subType.getId());
				bankDetails.setName(subType.getName());
				bankDetails.setStatus(status);
				items.add(bankDetails);
			}
			docType.setItems(items);
			docList1.add(docType);
		}
		map.put("root", docList1);
		return ResponseEntity.ok(map);
	}

	private void saveImage(MultipartFile file, HttpServletRequest request, String folder, User currentUser,
			String fileType) throws IOException {
		HttpSession session = request.getSession();
		ServletContext sc = session.getServletContext();
		// File file1 = new File(sc.getRealPath("/") + folder);
		// String filePath = "/opt/image";
		File file1 = new File(folder);
		if (!file1.exists()) // {
			file1.mkdirs();
		// }
		byte[] bytes = file.getBytes();
		BufferedOutputStream stream = new BufferedOutputStream(
				new FileOutputStream(new File(file1 + "/" + currentUser.getEmailId() + "." + fileType)));
		stream.write(bytes);
		stream.close();
		System.out.println("********************************************************");

	}
	
	
	 @ExceptionHandler(FileExceedException.class)
	    public ResponseEntity<?> handleStorageFileNotFound(FileExceedException exc) {
		 System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&7");
	        return ResponseEntity.notFound().build();
	    }
	
	
	@RequestMapping(value = "/user/{id}/test", method = RequestMethod.GET) // method should get
	public ResponseEntity<?> getException(@PathVariable("id") long id) {
		
		throw new FileExceedException("messageggggggggggg");
		
//		return null;
		
	}

}
