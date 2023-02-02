/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.biztechnique;

import reactor.core.publisher.Flux;

/**
 * flux mono
 *
 * @author kanglele
 * @version $Id: FluxMonoExample, v 0.1 2022/8/5 15:38 kanglele Exp $
 */
public class FluxMonoExample {

    public static void main(String[] args) {
        Flux.range(1, 10)
                .timeout(Flux.never(), v -> Flux.never())
                .subscribe(System.out::println);

        //onAssembly方法是将创建好的Mono对象进行装饰增强。

        //switchIfEmpty 如果为空怎么做，如：data.switchIfEmpty(Mono.error(() -> new NotFoundException("数据不存在"));


    }

}
