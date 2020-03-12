package com.thinkss.paycheck.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.thinkss.paycheck.entity.Bank;
import com.thinkss.paycheck.entity.BankInterestRate;
import com.thinkss.paycheck.entity.Country;
import com.thinkss.paycheck.entity.LoanType;
import com.thinkss.paycheck.repository.BankInterestRateRepository;
import com.thinkss.paycheck.repository.BankRepository;
import com.thinkss.paycheck.repository.BankViewRepository;
import com.thinkss.paycheck.repository.CountryRepository;
import com.thinkss.paycheck.service.BankService;
import com.thinkss.paycheck.view.BankView;

@Service
public class BankServiceImpl implements BankService {

	@Autowired
	private BankInterestRateRepository rateRepository;

	@Autowired
	private BankViewRepository bankViewRepository;
	@Autowired
	private CountryRepository countryRepository;

	@PersistenceContext
	private EntityManager em;


	public BankInterestRate findBankInterestRate(Country country, Bank bank, LoanType loanType) {
		Query query = em
				.createQuery(
						"select rate from BankInterestRate rate where rate.country = :country and rate.bank = :bank and rate.loanType =:loanType  ORDER BY rate.rate_created_date DESC ")
				.setParameter("country", country).setParameter("bank", bank).setParameter("loanType", loanType);
//		query.setMaxResults(1);

		return (BankInterestRate) query.getSingleResult();
	}

	public LoanType findBankLoanType(Country country, Bank bank) {
		Query query = em.createQuery("select type from LoanType type where country = :country and bank = :bank  ")// limit
				.setParameter("country", country).setParameter("bank", bank);

		return null;

	}

	public void getBankWithRate() {
		em.createQuery("select b.id , b.name, country.id,country.name ,bir.rate from bank_interest_rate  bir "
				+ " join bank_name b on b.id = bir.bank_id"
				+ "join country on bir.country_id = country.id order by bir.rate_created_date desc LIMIT 1;");
	}

	public BankInterestRate save(BankInterestRate bankInterestRate) {

		return rateRepository.save(bankInterestRate);

	}

	public List findBankInterestRate(Country country, LoanType loanType) {

		Query query = em.createQuery(
				"select rate from BankInterestRate rate where country = :country and loanType =:loanType  ORDER BY rate_created_date DESC ")// limit
				.setParameter("country", country).setParameter("loanType", loanType);

		return query.getResultList();

	}

	public List<BankView> findAllBankRate() {
		Query query = em.createQuery("select view from BankView view  ");// limit
		return query.getResultList();
	}

	public List<BankView> findAllBankRate(int start, int limit, String dir, String sort) {
		// return bankViewRepository.findAll();
		Query query = em.createQuery("select view from BankView view order by " + sort + " " + dir);// limit
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Cacheable
	public Page<BankView> findAllBankView(Pageable pageable) {
		return bankViewRepository.findAll(pageable);
	}

	public List<Country> findAllCountry() {
		return countryRepository.findAll(sortByAlphaAsc());
	}

	private Sort sortByIdAsc() {
		return new Sort(Sort.Direction.ASC, "id");
	}

	private Sort sortByAlphaAsc() {
		return new Sort(Sort.Direction.ASC, "countryName");
	}

	public List<BankView> findLatestBankInterestRate(Country country, LoanType loanType) {

		Query query = em.createQuery(
				"select rate from BankView rate where countryId = :country and loanTypeId =:loanType  ORDER BY rate_created_date DESC ")// limit
				.setParameter("country", country.getId()).setParameter("loanType", loanType.getId());
		return query.getResultList();

	}

	@SuppressWarnings({ "unchecked", "unchecked" })
	public List<BankView> findAllBankRate(int start, int limit, String dir, String sort, JSONArray crit)
			throws JSONException {

		StringBuilder queryString = new StringBuilder();
		ArrayList<String> queryWhere = new ArrayList<String>();
		ArrayList<Object> queryParams = new ArrayList<Object>();
		queryString.append("select view from BankView view ");// limit
		for (int i = 0; i < crit.length(); i++) {
			JSONObject obj = crit.getJSONObject(i);
			if (obj.get("property").equals("bankName")) {
				queryWhere.add(" view.bankName LIKE  ? ");
				queryParams.add("%" + (String) obj.optString("value") + "%");
			}
			if (obj.get("property").equals("countryName")) {
				queryWhere.add(" view.countryName LIKE  ? ");
				queryParams.add("%" + (String) obj.optString("value") + "%");
			}
		}

		if (!queryWhere.isEmpty()) {
			queryString.append(" WHERE ");
			for (int i = 0; i < queryWhere.size(); i++) {
				if (i != 0) {
					queryString.append(" AND ");
				}
				queryString.append(queryWhere.get(i));
			}
		}

		queryString.append(" ORDER BY view." + sort + " " + dir);

		// create the query and apply the parameters
		Query query = em.createQuery(queryString.toString());
		for (int i = 0; i < queryParams.size(); i++) {
			query.setParameter(i + 1, queryParams.get(i));
		}
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();

	}

}
