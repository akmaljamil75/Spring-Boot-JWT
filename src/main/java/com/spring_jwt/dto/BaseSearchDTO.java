package com.spring_jwt.dto;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseSearchDTO {

    private Map<String, Object> filters;

}
