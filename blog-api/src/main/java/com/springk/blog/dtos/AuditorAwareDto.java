package com.springk.blog.dtos;

import lombok.Data;

import java.util.Date;

@Data
public abstract class AuditorAwareDto <T>{
    protected T createdBy;
    protected Date createdDate;
    protected T lastModifiedBy;
    protected Date lastModifiedDate;
}
