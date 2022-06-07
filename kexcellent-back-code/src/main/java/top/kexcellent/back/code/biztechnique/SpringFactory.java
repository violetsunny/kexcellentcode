/**
 * LY.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package top.kexcellent.back.code.biztechnique;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * Spring 容器监听类
 * @author xy23087
 * @version $Id: SpringFactory.java, v 0.1 2016年5月12日 上午8:54:58 xy23087 Exp $
 */

public class SpringFactory implements BeanFactoryAware{
    
    /** Spring容器 */
    private static BeanFactory beanFactory;  
    /** 
     * 回调方法 
     * @see BeanFactoryAware#setBeanFactory(BeanFactory)
     */
    @Override
    public void setBeanFactory(BeanFactory factory) throws BeansException {  
        // TODO Auto-generated method stub  
        beanFactory = factory;  
    }
    /**
     * 从Spring中获取指定名字的BEAN
     * @param beanName beanName
     * @param clazz 类型
     * @return Object
     */
    @SuppressWarnings("unchecked")
    public static Object getBean(String beanName, @SuppressWarnings("rawtypes") Class clazz){  
        
        return beanFactory.getBean(beanName, clazz);  
          
    }
}
      
    
