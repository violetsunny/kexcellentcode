/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.biztechnique;

import top.kexcellent.back.code.model.Person;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * 去重示例
 *
 * @author kanglele
 * @version $Id: Distinct, v 0.1 2022/5/17 18:12 kanglele Exp $
 */
public class DistinctExample {

    private void example1(List<Person> persons){
        //对code相同的人去重
        persons = persons.stream().collect(
                collectingAndThen(
                        toCollection(() -> new TreeSet<>(Comparator.comparing(Person::getCode))), ArrayList::new)
        );
    }

}
