package com.prprv.verify.template;

import lombok.Builder;
import lombok.Data;

/**
 * @author Yoooum
 */
@Data
@Builder
public class EmailBuilder {
    private String from;
    private String to;
    private String subject;
    private String html;

}
