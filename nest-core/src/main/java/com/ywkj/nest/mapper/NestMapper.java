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


    public <T> T map(Object source, Class<T> destinationClass) {
        return mapper.map(source, destinationClass);
    }

    public void map(Object source, Object destination) {
        mapper.map(source, destination);
    }

    public <T> T map(Object source, Class<T> destinationClass, String mapId) {
        return mapper.map(source, destinationClass, mapId);
    }

    public void map(Object source, Object destination, String mapId) {
        mapper.map(source, destination, mapId);
    }

    public <T> List<T> mapList(List source, Class<T> destinationClass) {
        List<T> lst = new ArrayList<>();
        for (Object o : source) {
            lst.add(mapper.map(o, destinationClass));
        }
        return lst;
    }
}
