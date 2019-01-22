package com.zxm.rpc;

/**
 * @Author
 * @Description
 * @Date Create in 上午 10:35 2019/1/22 0022
 */
public class IPayImpl implements IPayInterface {
    @Override
    public boolean pay(int fee) {
        System.out.println("支付成功，支付金额为" + fee);
        return true;
    }
}
