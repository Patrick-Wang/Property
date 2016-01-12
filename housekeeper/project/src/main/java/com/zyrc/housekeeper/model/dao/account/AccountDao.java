package com.zyrc.housekeeper.model.dao.account;

import com.zyrc.housekeeper.model.entity.AccountEntity;


public interface AccountDao {

	AccountEntity getByName(String name);

}
