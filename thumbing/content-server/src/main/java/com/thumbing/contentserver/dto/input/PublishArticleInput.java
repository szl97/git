package com.thumbing.contentserver.dto.input;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author Stan Sai
 * @date 2020-08-19 9:43
 */
@Data
public class PublishArticleInput implements Serializable {
    private String title;
    private Set<Long> tagIds;
    private String content;
    private List<String> graphIds;
}
