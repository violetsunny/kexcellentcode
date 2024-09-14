/**
 * LY.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package top.kexcellent.back.code.processor;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner8;
import javax.tools.Diagnostic;
import java.util.EnumSet;

/**
 * @author kll49556
 * @version $Id: NameChecker, v 0.1 2018/7/18 11:32 kll49556 Exp $
 */
public class NameChecker {

    private Messager messager;

    NameCheckScanner nameCheckScanner = new NameCheckScanner();

    NameChecker(ProcessingEnvironment processingEnvironment){
        this.messager = processingEnvironment.getMessager();
    }

    public void checkNames(Element element){
        nameCheckScanner.scan(element);
    }

    /**
     * 检查器实现类
     */
    private class NameCheckScanner extends ElementScanner8<Void,Void>{
        @Override
        public Void visitType(TypeElement e, Void aVoid) {
            scan(e.getTypeParameters(),aVoid);
            checkCamelCase(e,true);
            return super.visitType(e, aVoid);
        }

        /**
         * 方法命名是否合法
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitExecutable(ExecutableElement e, Void aVoid) {
            if(e.getKind() == ElementKind.METHOD){
                Name name = e.getSimpleName();
                if(name.contentEquals(e.getEnclosingElement().getSimpleName())){
                    messager.printMessage(Diagnostic.Kind.WARNING,"普通方法"+name+"不应该与类名重复，避免与构造函数混淆",e);
                    checkCamelCase(e,false);
                }
            }
            return super.visitExecutable(e, aVoid);
        }

        /**
         * 变量命名是否合法
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitVariable(VariableElement e, Void aVoid) {
            if(e.getKind() == ElementKind.ENUM_CONSTANT || e.getConstantValue() != null || heuristicallyConstant(e)){
                checkAllCaps(e);
            }else{
                checkCamelCase(e,false);
            }

            return super.visitVariable(e, aVoid);
        }


        /**
         * 判断变量是否是常量
         * @param e
         * @return
         */
        private boolean heuristicallyConstant(VariableElement e){
            if(e.getEnclosingElement().getKind() == ElementKind.INTERFACE){
                return true;
            }else if(e.getKind() == ElementKind.FIELD && e.getModifiers().containsAll(EnumSet.of(Modifier.PUBLIC,Modifier.STATIC,Modifier.FINAL))) {
                return true;
            }else{
                return false;
            }
        }

        /**
         * 检查是否符合驼式命名法
         * @param e
         * @param initialCaps
         */
        private void checkCamelCase(Element e ,boolean initialCaps){
            String name = e.getSimpleName().toString();
            boolean previousUpper = false;
            boolean conventional = true;//是否符合要求规则判断
            int firstCodePoint = name.codePointAt(0);//转成下标所在值对应的ASCII值
            if(Character.isUpperCase(firstCodePoint)){//是否属于大学字母范围
                previousUpper = true;
                if(!initialCaps){
                    messager.printMessage(Diagnostic.Kind.WARNING,"名称"+name+"应当以小写字母开头",e);
                    return;
                }
            }else if(Character.isLowerCase(firstCodePoint)){
                if(initialCaps){
                    messager.printMessage(Diagnostic.Kind.WARNING,"名称"+name+"应当以大写字母开头",e);
                    return;
                }
            }else {
                conventional = false;
            }

            if(conventional){
                int cp = firstCodePoint;
                for(int i = Character.charCount(cp);i < name.length(); i += Character.charCount(cp)){
                    cp = name.codePointAt(i);
                    if(Character.isUpperCase(cp)){
                        if(previousUpper){
                            conventional = false;
                            break;
                        }
                        previousUpper = true;
                    }else {
                        previousUpper = false;
                    }

                }

                if(!conventional){
                    messager.printMessage(Diagnostic.Kind.WARNING,"名称"+name+"要符合驼式命名方法（Camel,Case,Names）",e);
                }
            }
        }


        /**
         * 常量大写检查
         * @param e
         */
        private void checkAllCaps(Element e){
            String name = e.getSimpleName().toString();
            boolean conventional = true;//是否符合要求规则判断
            int firstCodePoint = name.codePointAt(0);
            if(!Character.isUpperCase(firstCodePoint)){
                conventional = false;
            }else {
                boolean previousUnderscore = false;
                int cp = firstCodePoint;
                for(int i = Character.charCount(cp);i < name.length(); i += Character.charCount(cp)){
                    cp = name.codePointAt(i);
                    if(cp == (int)'_'){
                        if(previousUnderscore){
                            conventional = false;
                            break;
                        }
                        previousUnderscore = true;
                    }else{
                        previousUnderscore = false;
                        if(!Character.isUpperCase(cp) && !Character.isDigit(cp)){
                            conventional = false;
                            break;
                        }
                    }
                }
            }

            if(!conventional){
                messager.printMessage(Diagnostic.Kind.WARNING,"常量"+name+"应该用大写字母",e);
            }
        }
    }

}
