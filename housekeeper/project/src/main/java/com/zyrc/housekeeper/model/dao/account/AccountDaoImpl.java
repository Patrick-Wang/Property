package com.zyrc.housekeeper.model.dao.account;


import java.util.List;

import com.speed.frame.model.dao.AbstractReadWriteDaoImpl;
import com.zyrc.housekeeper.model.entity.AccountEntity;
import com.zyrc.housekeeper.model.dao.account.AccountDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository(AccountDaoImpl.NAME)
@Transactional("transactionManager")
public class AccountDaoImpl extends AbstractReadWriteDaoImpl<AccountEntity> implements AccountDao {
	public final static String NAME = "AccountDaoImpl";

	@PersistenceContext(unitName = "local_DB")
	public void setEntityManager(EntityManager entityManager) {
		super.setEntityManager(entityManager);
	}

	@Override
	public AccountEntity getByName(String name) {
		Query q = this.getEntityManager().createQuery("from AccountEntity where name = :name");
		q.setParameter("name", name);
		List<AccountEntity> ret = q.getResultList();
		if (!ret.isEmpty()){
			return ret.get(0);
		}
		return null;
	}
}
