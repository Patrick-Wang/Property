package com.zyrc.housekeeper.service.account;

import com.zyrc.housekeeper.model.entity.AccountEntity;

public interface AccountService {

	AccountEntity validate(String name, String psw);


}
