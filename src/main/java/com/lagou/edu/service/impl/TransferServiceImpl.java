package com.lagou.edu.service.impl;

import com.lagou.edu.annotation.Autowird;
import com.lagou.edu.annotation.Service;
import com.lagou.edu.annotation.Transactional;
import com.lagou.edu.dao.AccountDao;
import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.TransferService;
import com.lagou.edu.utils.ConnectionUtils;
import com.lagou.edu.utils.TransactionManager;

/**
 * @author 应癫
 */
@Service("transferService")
public class TransferServiceImpl implements TransferService {

    //private AccountDao accountDao = new JdbcAccountDaoImpl();

    // private AccountDao accountDao = (AccountDao) BeanFactory.getBean("accountDao");

    // 最佳状态
    @Autowird
    private AccountDao jdbcAccountDaoImpl;

    // 构造函数传值/set方法传值

    public void setAccountDao(AccountDao accountDao) {
        this.jdbcAccountDaoImpl = jdbcAccountDaoImpl;
    }



    @Override
    //@Transactional
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {

        /*try{
            // 开启事务(关闭事务的自动提交)
            TransactionManager.getInstance().beginTransaction();*/

            Account from = jdbcAccountDaoImpl.queryAccountByCardNo(fromCardNo);
            Account to = jdbcAccountDaoImpl.queryAccountByCardNo(toCardNo);

            from.setMoney(from.getMoney()-money);
            to.setMoney(to.getMoney()+money);

            jdbcAccountDaoImpl.updateAccountByCardNo(to);
                int c = 1/0;
            jdbcAccountDaoImpl.updateAccountByCardNo(from);

        /*    // 提交事务

            TransactionManager.getInstance().commit();
        }catch (Exception e) {
            e.printStackTrace();
            // 回滚事务
            TransactionManager.getInstance().rollback();

            // 抛出异常便于上层servlet捕获
            throw e;

        }*/




    }
}
