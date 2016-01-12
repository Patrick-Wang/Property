package com.zyrc.housekeeper.service.account;

import javax.annotation.Resource;

import com.zyrc.housekeeper.model.dao.account.AccountDaoImpl;
import com.zyrc.housekeeper.model.dao.account.AccountDao;
import com.zyrc.housekeeper.model.entity.AccountEntity;
import com.zyrc.housekeeper.service.account.AccountService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(AccountServiceImpl.NAME)
@Transactional("transactionManager")
public class AccountServiceImpl implements AccountService {
	
	@Resource(name=AccountDaoImpl.NAME)
	AccountDao accountDao;

	public final static String NAME = "AccountServiceImpl";

	@Override
	public AccountEntity validate(String name, String psw) {
		if (name != null && psw != null) {
			AccountEntity account = accountDao.getByName(name);
			if (account != null && psw.equals(account.getPassword())){
				return account;
			}
		}
		return null;
	}

	
	
}
