package com.jovezhao.nest.mapper;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 类型转换时使用
 * Created by Jove on 2016/9/26.
 */
public class NestMapper {

    private DozerBeanMapper mapper;

    public void setMappingFiles(Resource[] mappingFiles) throws IOException {
        if (mappingFiles != null) {
            ArrayList mappings = new ArrayList(mappingFiles.length);
            for (Resource mappingFile : mappingFiles) {
                URL url = mappingFile.getURL();
                mappings.add(url.toString());
            }

            mapper.setMappingFiles(mappings);
        }
    }

    public NestMapper() {
        mapper = new DozerBeanMapper();
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
