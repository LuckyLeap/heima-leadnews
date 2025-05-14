package com.heima.search.pojos;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 联想词表
 */
@Data
@Document("ap_associate_words")
public class ApAssociateWords implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 联想词
     */
    private String associateWords;

    /**
     * 创建时间
     */
    private Date createdTime;

}