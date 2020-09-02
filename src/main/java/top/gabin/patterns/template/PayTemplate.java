package top.gabin.patterns.template;

public abstract class PayTemplate {
    public final void confirmPay(PayForm payForm) throws NotMyOrderException {
        // 1、校验用户
        validCustomer(payForm);
        // 2、扭转状态
        updateStatus(payForm);
        // 3、订单回调处理
        callback(payForm);
        // 4、同步到第三方系统
        if (needSyncThirdParty()) {
            syncThirdParty(payForm);
        }
    }

    // 钩子方法
    private boolean needSyncThirdParty() {
        return false;
    }


    private void updateStatus(PayForm payForm) {

    }

    protected abstract void validCustomer(PayForm payForm) throws NotMyOrderException;

    protected abstract void callback(PayForm payForm);

    protected abstract void syncThirdParty(PayForm payForm);

}
