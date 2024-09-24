/**
 * LY.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package top.kexcellent.back.code.service;

/**
 * @author kll49556
 * @version $Id: SingletonTemplate, v 0.1 2018/7/28 14:23 kll49556 Exp $
 *
 * 推荐写法 单例
 *
 * 关于单例，我们总是应该记住：线程安全，延迟加载，序列化与反序列化安全，反射安全是很重重要
 */
public class SingletonTemplate {

    private SingletonTemplate(){}

    public static SingletonTemplate getInstance(){
        return SingletonEnum.INSTANCE.getInstance();
    }

    private enum SingletonEnum{

        INSTANCE;

        private SingletonTemplate singletonTemplate;

        //JVM保证这个构造器只会执行一次
        //枚举序列化是由jvm保证的，每一个枚举类型和定义的枚举变量在JVM中都是唯一的，在枚举类型的序列化和反序列化上，
        //Java做了特殊的规定：在序列化时Java仅仅是将枚举对象的name属性输出到结果中，反序列化的时候则是通过java.lang.Enum的valueOf方法来根据名字查找枚举对象。
        //同时，编译器是不允许任何对这种序列化机制的定制的并禁用了writeObject、readObject、readObjectNoData、writeReplace和readResolve等方法，
        //从而保证了枚举实例的唯一性
        SingletonEnum(){
            singletonTemplate = new SingletonTemplate();
        }

        public SingletonTemplate getInstance(){
            return singletonTemplate;
        }
    }

    public static void main(String[] args) {
        System.out.println(SingletonTemplate.getInstance());
    }
}
