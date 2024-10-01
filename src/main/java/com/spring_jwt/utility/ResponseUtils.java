package com.spring_jwt.utility;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    public static ResponseEntity<Object> success(String message, Object data, HttpStatus httpStatus)
    {   
        Map<String, Object>  map = new HashMap<String, Object>();
        map.put("status", "success");
        map.put("message", message);
        map.put("data", data);
        map.put("statsuCode", httpStatus.value());
        return new ResponseEntity<Object>(map, httpStatus);
    }

    public static ResponseEntity<Object> success(String message, HttpStatus httpStatus)
    {   
        Map<String, Object>  map = new HashMap<String, Object>();
        map.put("status", "success");
        map.put("message", message);
        map.put("statsuCode", httpStatus.value());
        return new ResponseEntity<Object>(map, httpStatus); 
    }

    public static ResponseEntity<Object> failed(String message, HttpStatus httpStatus)
    {   
        Map<String, Object>  map = new HashMap<String, Object>();
        map.put("statsuCode", httpStatus.value());
        map.put("status", "failed");
        map.put("message", message);
        return new ResponseEntity<Object>(map, httpStatus);
    }

    public static ResponseEntity<Object> failed(String message, Object data, HttpStatus httpStatus)
    {   
        Map<String, Object>  map = new HashMap<String, Object>();
        map.put("status", "failed");
        map.put("message", message);
        map.put("data", data);
        map.put("statsuCode", httpStatus.value());
        return new ResponseEntity<Object>(map, httpStatus);
    }

}
