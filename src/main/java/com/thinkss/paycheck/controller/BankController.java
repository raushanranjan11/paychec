package com.thinkss.paycheck.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thinkss.paycheck.bean.BankDetail;
import com.thinkss.paycheck.bean.BankDetail.LoanTypes;
import com.thinkss.paycheck.bean.LoanTypeForm;
import com.thinkss.paycheck.bean.MessageBean;
import com.thinkss.paycheck.bean.PaidLoanBean;
import com.thinkss.paycheck.entity.Bank;
import com.thinkss.paycheck.entity.BankInterestRate;
import com.thinkss.paycheck.entity.Country;
import com.thinkss.paycheck.entity.DocumentDetails;
import com.thinkss.paycheck.entity.DocumentSubType;
import com.thinkss.paycheck.entity.DocumentType;
import com.thinkss.paycheck.entity.LoanType;
import com.thinkss.paycheck.entity.User;
import com.thinkss.paycheck.entity.UserSignInToken;
import com.thinkss.paycheck.repository.BankRepository;
import com.thinkss.paycheck.repository.BankViewRepository;
import com.thinkss.paycheck.repository.CountryRepository;
import com.thinkss.paycheck.repository.LoanRepository;
import com.thinkss.paycheck.repository.LoanTypeRepository;
import com.thinkss.paycheck.response.model.DocumentMessageResponse;
import com.thinkss.paycheck.service.BankService;
import com.thinkss.paycheck.util.StaticIP;
import com.thinkss.paycheck.view.BankView;


@RestController
@RequestMapping(value = "/country", produces = MediaType.APPLICATION_JSON_VALUE)
public class BankController {

	
	private static final Logger logger = LoggerFactory.getLogger(BankController.class);
	
	@Autowired
	CountryRepository  countryRepository;
	@Autowired
	BankRepository bankRepository;
	
	@Autowired
	LoanTypeRepository loanTypeRepository;
	
	@Autowired
	BankViewRepository bankViewRepository;
	
	@Autowired
	BankService bankService;
	
	@RequestMapping(value = "/{code}/bank", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> bankLogo(@PathVariable("code") String code) {
		Country country = countryRepository.findByIsoCode(code);
		if(country==null)
			return ResponseEntity.ok(new MessageBean(false,"Country not found"));
//		System.out.println(country.getCountryName());
//		System.out.println(country.getBank().size());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", true);
		map.put("bankList", country.getBank());
		return  ResponseEntity.ok(map);
		
	
	}
	
	@RequestMapping(value = "/{code}/bank/{bankId}/loanType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getLoanType(@PathVariable("code") String code,@PathVariable("bankId") Long bankId) {
		Country country = countryRepository.findByIsoCode(code);
		if(country==null)
			return ResponseEntity.ok(new MessageBean(false,"Country not found"));
		
		Bank bank = bankRepository.findOne(bankId);
		System.out.println(country.getCountryName());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", true);
//		map.put("loneTypeList", loanTypeRepository.findAll());///findAllByOrderByLoanNameAsc
		map.put("loneTypeList", loanTypeRepository.findAllByOrderByLoanNameAsc());
		return  ResponseEntity.ok(map);
		
	
	}
	
	@RequestMapping(value = "/loanType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getLoanTypes() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", true);
		map.put("loneTypeList", loanTypeRepository.findAllByOrderByLoanNameAsc());
		return  ResponseEntity.ok(map);
		
	
	}
	
	
	@RequestMapping(value = "/{code}/bank/{bankId}/loanType/{loanTypeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getInterestRate(@PathVariable("code") String code,@PathVariable("bankId") Long bankId,@PathVariable("loanTypeId") Long loanTypeId) {
		Country country = countryRepository.findByIsoCode(code);
		Bank bank = bankRepository.findOne(bankId) ;
		LoanType loanType = loanTypeRepository.findOne(loanTypeId);

		Map<String, Object> map = new HashMap<String, Object>();
		
		if(country!=null && bank != null && loanType!= null ) {
			BankInterestRate bankInterestRate= bankService.findBankInterestRate(country, bank, loanType);
		
		map.put("status", true);
		map.put("interestRate", bankInterestRate.getRate());
		return  ResponseEntity.ok(map);
		}else {
			map.put("status", false);
			map.put("message", "You send in-correct data");
			return  ResponseEntity.ok(map);
		}
	}
		
		@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<?> getAllCountry(HttpServletRequest request,HttpServletResponse response) {
			System.out.println("^^^^^^^^^^^^^^^^^");
			Iterable<Country> country = countryRepository.findAllByOrderByCountryNameAsc();
//			Iterable<Country> country = bankService.findAllCountry();
			
			
			
			
			
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", true);
			map.put("country", country);
//			response.setContentType(type);
			return  ResponseEntity.ok(map);
			
//			return null;
			
		
	}
		
		/*@RequestMapping(value = "/{code}/bank/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<?> getAllCountry(HttpServletRequest request,HttpServletResponse response) {
			System.out.println("^^^^^^^^^^^^^^^^^");
			Iterable<Country> country = countryRepository.findAll();
			
			
			
			
			
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", true);
			map.put("country", country);
//			response.setContentType(type);
			return  ResponseEntity.ok(map);
			
//			return null;
			
		
	}*/
	
		
		
		@RequestMapping(value = "/saveBank", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<?> saveBank( @RequestParam("bankLogo") MultipartFile file, HttpServletRequest request ){		
	System.out.println("****************************");
	String isoCode =  request.getParameter("country");
	System.out.println(isoCode   +"_____________-----------");
	String bankName =  request.getParameter("bankName");
	Country country = countryRepository.findByIsoCode(isoCode);
//	Country country = countryRepository.findByIsoCode(code);
	Map<String, Object> map = new HashMap<String, Object>();

			if (country == null) {
				return ResponseEntity.ok(new DocumentMessageResponse(false, "No Country Available"));
			}
			System.out.println("      countryName       "+country.getCountryName());
			Set<Country> set = new HashSet<Country>();
			set.add(country);
			Bank bank = new Bank();
			bank.setName(bankName);
			bank.setCountry(set);
//			System.out.println();
			
			String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
			if (!file.isEmpty()) {
				if ( fileType.equalsIgnoreCase("png") || fileType.equalsIgnoreCase("jpg") ) {
					String contextPath = request.getContextPath();
					try {
						String folder = null;
						folder = "/opt/image/bank/" +isoCode.toLowerCase() + "/";

						saveBankImage(file, request, folder, fileType);
						
						String folder1 = "/resources/image/bank/" + isoCode.toLowerCase()+"/";
						bank.setBanklogo(folder1.concat(file.getOriginalFilename()));
						
						bankRepository.save(bank);
						map.put("success", true);//" Bank Details Successfully saved."
						map.put("message", "Bank Details Successfully saved");
						return ResponseEntity.ok(map);
//						   return new ResponseEntity<>( HttpStatus.OK);
					} catch (Exception e) {
						return ResponseEntity.ok(new DocumentMessageResponse(false, e.getMessage()));
					}
					
					
				}else {
					return ResponseEntity.ok(new DocumentMessageResponse(false, "Not valid format"));
				}
			}else {
				return ResponseEntity.ok(new DocumentMessageResponse(false, "No file selected"));
			}
			
//			return null;

		}
		
		
		private void saveBankImage(MultipartFile file, HttpServletRequest request, String folder, 
				String fileType) throws IOException {
			HttpSession session = request.getSession();
			ServletContext sc = session.getServletContext();
			File file1 = new File(folder);
			if (!file1.exists()) 
				file1.mkdirs();
			byte[] bytes = file.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(file1 + "/" + file.getOriginalFilename())));
			stream.write(bytes);
			stream.close();
			System.out.println("********************************************************");

		}
		@RequestMapping(value = "/linkToBank", method = RequestMethod.POST)
		public ResponseEntity<?> linkLoanToBank( HttpServletRequest request){	
			
			String isoCode =  request.getParameter("country");
			Long bankId =  Long.valueOf(request.getParameter("bank"));
			//String loanTypeName = 
			Long loanId =  Long.valueOf(request.getParameter("loanType"));
			Country country = countryRepository.findByIsoCode(isoCode);
			Bank bank = bankRepository.findOne(bankId);
			LoanType loanType = loanTypeRepository.findOne(loanId);
			Bank b = bankRepository.findByLoanTypeAndId(loanType,bankId);
			Map<String, Object> map = new HashMap<String, Object>();
			if(b==null) {
				Set<Bank> set  = new HashSet<Bank>();
				set.add(bank);
				loanType.setBank(set);
			LoanType l = 	loanTypeRepository.save(loanType);
			if(l!=null) {
				
				map.put("success", true);//" Bank Details Successfully saved."
				map.put("message", "Loan Type Successfully saved");
				return ResponseEntity.ok(map);
			}
				
				
			}else {
				map.put("status", false);
				map.put("message", bank.getName() + "have already linked with "+loanType.getLoanName());
				return  ResponseEntity.ok(map);
			}
			
			
			return null;
		}

		@RequestMapping(value = "/saveLoanType", method = RequestMethod.POST)
		public ResponseEntity<?> saveLoanType(@RequestParam("loanImages") MultipartFile file, HttpServletRequest request){	
			
			String loanName = 		  request.getParameter("loanName");
			String loanDescription =  request.getParameter("loanDescription");
			BigDecimal loanAmountLimit =  BigDecimal.valueOf(Long.valueOf(request.getParameter("loanAmountLimit")));
			
			LoanType loanType = new LoanType();
			loanType.setLoanName(loanName);
			loanType.setLoanDescription(loanDescription);
			loanType.setLoanAmountLimit(loanAmountLimit);
			Map<String, Object> map = new HashMap<String, Object>();
			String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
			if (!file.isEmpty()) {
				if ( fileType.equalsIgnoreCase("png") || fileType.equalsIgnoreCase("jpg") ) {
					String contextPath = request.getContextPath();
					try {
						String folder = null;
						folder = "/opt/image/loanType/"+loanType.getLoanName().toLowerCase()+"/";// +isoCode.toLowerCase() + "/";

						saveBankImage(file, request, folder, fileType);
						
						String folder1 = "/resources/image/loanType/" + loanType.getLoanName().toLowerCase()+"/";
						loanType.setLoanImages(folder1.concat(file.getOriginalFilename()));
						
						loanTypeRepository.save(loanType);
						map.put("success", true);//" Bank Details Successfully saved."
						map.put("message", "Loan Details Successfully saved");
						return ResponseEntity.ok(map);
//						   return new ResponseEntity<>( HttpStatus.OK);
					} catch (Exception e) {
						return ResponseEntity.ok(new DocumentMessageResponse(false, e.getMessage()));
					}
					
					
				}else {
					return ResponseEntity.ok(new DocumentMessageResponse(false, "Not valid format"));
				}
			}else {
				return ResponseEntity.ok(new DocumentMessageResponse(false, "No file selected"));
			}
			
			
		}
		
		
		@RequestMapping(value = "/saveRate", method = RequestMethod.POST)
		public ResponseEntity<?> saveRate( HttpServletRequest request){	
			
			String isoCode =  request.getParameter("countryName");
			Long bankId =  Long.valueOf(request.getParameter("bankId"));
			Long loanTypeId =  Long.valueOf(request.getParameter("loanType"));
			
			System.out.println(request.getParameter("processingFee"));
			System.out.println(request.getParameter("rate"));
			
			
			BigDecimal processingFee = BigDecimal.valueOf(Double.valueOf (request.getParameter("processingFee")) );
			BigDecimal rate =  BigDecimal.valueOf(Double.valueOf (request.getParameter("rate")));
			
			Country country = countryRepository.findByIsoCode(isoCode);
			Bank bank = bankRepository.findOne(bankId);
			LoanType loanType  = loanTypeRepository.findOne(loanTypeId);
			BankInterestRate bir = new BankInterestRate();
			bir.setBank(bank);
			bir.setLoanType(loanType);
			
			bir.setCountry(country);
			bir.setProcessingFee(processingFee);
			bir.setRate(rate);
			BankInterestRate savedRate = bankService.save(bir);
			Map<String, Object> map = new HashMap<String, Object>();
			
			if(savedRate != null) {
				bank.setIsDefaultRate(true);
				bankRepository.save(bank);
				map.put("success", true);//" Bank Details Successfully saved."
				map.put("message", "Interest Rate Successfully saved");
				return ResponseEntity.ok(map);
			}
			else {
				map.put("status", false);
				map.put("message", "You send in-correct data");
				return  ResponseEntity.ok(map);
			}
		}
		
		
		@RequestMapping(value = "/allRate", method = RequestMethod.GET)
		public ResponseEntity<?> findAllRate( HttpServletRequest request,
				@RequestParam("start") int start,
				@RequestParam("limit") int limit,
				@RequestParam("page") int pageN,
				@RequestParam("sort") String sort,@RequestParam("dir") String dir,
				@RequestParam("filter") String filter) throws JSONException{	
			Map<String, Object> map = new HashMap<String, Object>();
//		Iterable<BankView>	rateList  = bankService.findAllBankRate();			
			Direction direction = null;
			if(dir.equals("ASC")) {
				direction = 	Direction.ASC;
			}
			else if(dir.equals("DESC")) {
				direction = 	Direction.DESC;
			}
			JSONArray array = new JSONArray(filter);
			
			List<BankView>   bb = bankService.findAllBankRate(start, limit, dir,sort ,array);
			List<BankView>	rateList1  = bankService.findAllBankRate();	
		
		map.put("status", true);
		map.put("rateList", bb);
		map.put("total",rateList1.size());
		return  ResponseEntity.ok(map);
		}
		
		
		
		

		@RequestMapping(value = "/{code}/loanType/{loanTypeId}", method = RequestMethod.GET)
		public ResponseEntity<?> getAllBankWithLoanType( @PathVariable("code") String code,@PathVariable("loanTypeId") Long loanTypeId,HttpServletRequest request){
			
			Country country = countryRepository.findByIsoCode(code);
			if(country==null)
				return ResponseEntity.ok(new MessageBean(false,"Country not found"));
			
			LoanType loanType = loanTypeRepository.findOne(loanTypeId);
			if(loanType==null)
				return ResponseEntity.ok(new MessageBean(false,"Loan Type not found"));
			
			Map<String, Object> map = new HashMap<String, Object>();
			Iterable<BankView>	rateList  = bankService.findLatestBankInterestRate(country,loanType);	
		map.put("status", true);
		map.put("bankList", rateList);
		return  ResponseEntity.ok(map);
		}
		
		/****************************************Testing*******************************************/
		
		@RequestMapping(value = "/allRates", method = RequestMethod.GET)
		public ResponseEntity<?> findAllBankRates( HttpServletRequest request,
				@RequestParam("start") int start,
				@RequestParam("limit") int limit,
				@RequestParam("page") int pageN,
//				@PageableDefault(value=10, page=0)
				Pageable pageable,
				@RequestParam("sort") String sort,@RequestParam("dir") String dir,
				@RequestParam("filter") String filter
				) throws JSONException{	
			
//			System.out.println(pageable.getSort());
			Sort sorts = pageable.getSort();
//			System.out.println(sorts.toString());
//			System.out.println(pageable.getPageNumber());
//			System.out.println(pageable.getPageSize());
			System.out.println(sorts.getOrderFor("dir"));
//			 Pageable pageableq = new PageRequest(0, 4);
			Direction direction = null;
			if(dir.equals("ASC")) {
				direction = 	Direction.ASC;
			}
			else if(dir.equals("DESC")) {
				direction = 	Direction.DESC;
			}
			System.out.println(sort);
			System.out.println(direction);
			Pageable pageableq = new PageRequest(pageN-1, limit, direction, sort);
//			Page<BankView>page  = bankViewRepository.findAll(pageableq);
			Page<BankView>page  = bankViewRepository.findAll(pageableq);
			System.out.println(page.getTotalPages());
			String arr[] = request.getParameterValues(filter);
//			System.out.println(arr);
			JSONArray array = new JSONArray(filter);
//			System.out.println(array);
//		    JSONObject obj = array.getJSONObject(0);
//		    String operator = (String) obj.get("operator");
//		    String value = (String) obj.get("value");
//		    String property = (String) obj.get("property");
//			System.out.println("    "+start  +"  "+limit  + "  "+sort);
//			System.out.println("    "+operator  +"  "+value  + "  "+property);
			Map<String, Object> map = new HashMap<String, Object>();
//			Page<BankView>	rateList2  = bankService.findAllBankView(pageable);	
//			System.out.println("***********************");
//			System.out.println(rateList2);
//			System.out.println(rateList2.getContent());
//			System.out.println(rateList2.getSize());
			List<BankView>   bb = bankService.findAllBankRate(start, limit, dir,sort ,array);
//			System.out.println(bb.size());
//			
			List<BankView>	rateList1  = bankService.findAllBankRate();	
//		List<BankView>	rateList  = bankService.findAllBankRate(start, limit, "asc","bankName");
		
//		Properties p = new Properties();
//		p.setProperty(key, value)
		map.put("status", true);
		map.put("rateList", bb);
		map.put("total",rateList1.size());
//		map.put("rateList", page.getContent());
//		map.put("total",page.getTotalElements());
//		map.put("total", rateList1.getSize());
		return  ResponseEntity.ok(map);
		}
}
