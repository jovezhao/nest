package com.ywkj.nest.mapper;

import com.ywkj.nest.core.utils.SpringUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 类型转换时使用
 * Created by Jove on 2016/9/26.
 */
public class NestMapper {


    private Mapper mapper;


    public NestMapper() {
    }

    public void setMappingFiles(List<String> mappingFiles) {
        this.mapper = new DozerBeanMapper(mappingFiles);
    }


    public <T> T map(Object o, Class<T> aClass) {
        return mapper.map(o, aClass);
    }

    public void map(Object o, Object o1) {
        mapper.map(o, o1);
    }

    public <T> T map(Object o, Class<T> aClass, String s) {
        return mapper.map(o, aClass, s);
    }

    public void map(Object o, Object o1, String s) {
        mapper.map(o, o1, s);
    }

    public <T> List<T> mapList(List oList, Class<T> aClass) {
        List<T> lst = new ArrayList<>();
        for (Object o : oList) {
            lst.add(mapper.map(o, aClass));
        }
        return lst;
    }
}
