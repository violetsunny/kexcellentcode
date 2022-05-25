/**
 * llkang.com Inc.
 * Copyright (c) 2010-2022 All Rights Reserved.
 */
package top.kexcellent.back.code.model;

import lombok.Data;

/**
 * 示例模型-人
 *
 * @author kanglele
 * @version $Id: Person, v 0.1 2022/5/17 18:14 kanglele Exp $
 */
@Data
public class Person {

    private Long id;

    private String code;

    private String name;

    private Integer age;

    private Integer sex;

}
