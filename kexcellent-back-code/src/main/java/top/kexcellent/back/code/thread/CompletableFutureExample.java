/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.thread;

import com.google.common.collect.Lists;
import top.kdla.framework.common.help.MultiThreadInvokeHelp;
import top.kexcellent.back.code.model.Person;

import java.util.List;
import java.util.function.Supplier;

/**
 * 多线程协作示例
 *
 * @author kanglele
 * @version $Id: CompletableFutureExample, v 0.1 2022/5/17 18:26 kanglele Exp $
 */
public class CompletableFutureExample {

    private void example1() throws Exception {
        List<Supplier<String>> suppliers = Lists.newArrayList(() -> {
            //.....
            Person person = new Person();
            person.setName("阿康");
            return person.getName();
        },() -> {
            //.....
            Person person = new Person();
            person.setName("阿垚");
            return person.getName();
        });

        List<String> msg = MultiThreadInvokeHelp.invokeGet(suppliers,ExecutorFactory.getInstance());

    }

    private void example2() {

    }
}
