/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.back.code.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * @author kanglele01
 * @version $Id: MongoTestEntity, v 0.1 2020/8/28 14:47 kanglele01 Exp $
 */
@Data
@Document(collection = "klltest")
public class MongoTestEntity implements Serializable {
    @Id
    private String id;

    private String title;

    private String description;

    private String by;

    private String url;

    private List<String> tags;

    private Integer likes;
}
