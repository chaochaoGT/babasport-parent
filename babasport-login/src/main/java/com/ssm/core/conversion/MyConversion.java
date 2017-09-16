package com.ssm.core.conversion;

import org.springframework.core.convert.converter.Converter;

import javax.persistence.Id;

/**
 * 自定义的登录转换器
 * Created by IntelliJ IDEA.
 * User: Administrator
 */
public class MyConversion implements Converter<String,String>
{


    @Override
    public String convert(String source) {
        try {
            if (source!=null){
                source=source.trim();
                if (!"".equals(source)){
                    return source;
                }else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
