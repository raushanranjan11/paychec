package com.thinkss.paycheck.view.specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.thinkss.paycheck.view.BankView;

public class BankViewSpecification implements Specification<BankView>  {

	
	 private BankView filter;

	    public BankViewSpecification(BankView filter) {
	        super();
	        this.filter = filter;
	    }
	    
	    
//	    public static Specification<BankView> findAllGroupBy() {
//	        return new Specification<BankView>(){
	@Override
	public Predicate toPredicate(Root<BankView> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		
		List<Predicate> predicates = new ArrayList<>();

		// If designation is specified in filter, add equal where clause
		if (filter.getCountryName() != null) {
			predicates.add(cb.equal(root.get("countryName"), filter.getCountryName()));
		}

		// If firstName is specified in filter, add contains (lile)
		// filter to where clause with ignore case
		if (filter.getBankName() != null) {
			predicates.add(cb.like(cb.lower(root.get("bankName")), 
                                            "%" + filter.getBankName().toLowerCase() + "%"));
		}
		
		
		CriteriaQuery<BankView> cq = cb.createQuery(BankView.class);
//		Root<Pet> pet = cq.from(Pet.class);
		cq.select(root);
		cq.orderBy(cb.desc(root.get("createdDate")));
//		predicates.add(cb.)
//		List <Predicate> p = new ArrayList <Predicate> ();
//		predicates.add(cb.)
		 /*if(!predicates.isEmpty()){
		        Predicate[] pr = new Predicate[predicates.size()];
		        predicates.toArray(pr);
		        query.where(pr);   
//		        query..where(pr);
		    }*/
//		query.getOrderList().
//		 query.where(predicates.add(root.get("createdDate")));
//		cb.se
////		predicates.set(index, element)
//		// subquery to select count of installation request group by installation id
//        Subquery<Long> subQuery =
//                query.subquery(Date.class);

		// If lastName is specified in filter, add contains (lile)
		// filter to where clause with ignore case
	/**	if (filter.getLastName() != null) {
			pr.add(cb.like(cb.lower(root.get("lastName")), 
                                            "%" + filter.getLastName().toLowerCase() + "%"));
		}
*/
		return cb.and(predicates.toArray(new Predicate[0]));
		
		
		
//		return null;
	}
//	        }
	
	
	/*public static Specification<BankView> existsInstallationRequestLinkedToAnotherEPR(final Long EPRId) {
	    return new Specification<BankView>() {

	        @Override
	        public Predicate toPredicate(Root<BankView> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

	            // subquery to select installation ids
	            Subquery<Long> InstallationIdsLinkedToEPRsubquery = query.subquery(Long.class);
	            Root<BankView> selectIdSubQueryRoot =
	                    InstallationIdsLinkedToEPRsubquery.from(BankView.class);
	            InstallationIdsLinkedToEPRsubquery
	                    .select(selectIdSubQueryRoot.get(InstallationRequested_.installation).get(Installation_.id))
	                    .where(cb.equal(selectIdSubQueryRoot.get(InstallationRequested_.environmentPermitRequestId),
	                            EPRId));

	            // subquery to select count of installation request group by installation id
	            Subquery<Long> installationRequestForTheseInstallationIdsWhoAreLinkedToAnotherEPR =
	                    query.subquery(Long.class);
	            Root<BankView> groupBySubQueryRoot =
	                    installationRequestForTheseInstallationIdsWhoAreLinkedToAnotherEPR
	                            .from(InstallationRequested.class);
	            Path<Long> installationIdInGroupBySubQuery =
	                    groupBySubQueryRoot.get(InstallationRequested_.installation).get(Installation_.id);

	            installationRequestForTheseInstallationIdsWhoAreLinkedToAnotherEPR.select(cb.count(cb.literal(1)))
	                    .where(installationIdInGroupBySubQuery.in(InstallationIdsLinkedToEPRsubquery))
	                    .groupBy(installationIdInGroupBySubQuery)
	                    .having(cb.greaterThan(cb.count(cb.literal(1)), cb.literal(1l)));

	            // returning existing condition on result of the group by
	            return cb.exists(installationRequestForTheseInstallationIdsWhoAreLinkedToAnotherEPR);

	        }
	    };

	}*/

}
