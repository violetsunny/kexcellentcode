package top.kexcellent.back.code.service;

/**
 * 错误工厂
 * 
 * @author kllp0648
 * @version $Id: DemollkangBizErrorFactory.java, v 0.1 2017年3月17日 上午10:10:10 kllp0648 Exp $
 */
public class BizErrorFactory extends AbstractErrorFactory{
    @Override
    protected String provideErrorBundleName() {
        return "Demollkang-biz";
    }

    /**
     * 拿到DemollkangBizErrorFactory对象 单例
     * 
     * @return
     */
    public static BizErrorFactory getInstance(){
        return BizErrorFactoryHolder.INSTANCE;
    }
    
    /**
     * 静态内部类持有DemollkangBizErrorFactory对象
     * 
     * @author kllp0648
     * @version $Id: OrderLogErrorFactory.java, v 0.1 2017年2月4日 下午3:05:27 kllp0648 Exp $
     */
    private static final class BizErrorFactoryHolder{
       private static final BizErrorFactory INSTANCE = new BizErrorFactory();
    }
    
    /**
     * 参数不为空
     * 
     * @param paramName
     * @return
     */
    public Error invalidParam(String paramName) {
        return createError("LY0521025201", paramName);
    }
    
    /**
     * 方法对象不为空
     * 
     * @param method
     * @return
     */
    public Error invalidParamMethod(String method) {
        return createError("LY0521025202", method);
    }
    
    /**
     * 对象不为空
     * 
     * @param object
     * @return
     */
    public Error invalidParamObject(String object) {
        return createError("LY0521025203", object);
    }

    /**
     * 调用失败
     * @param code
     * @param msg
     * @return
     */
    public Error callFail(String code,String msg){
        return createError(code,msg);
    }
}
