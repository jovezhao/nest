package com.zhaofujun.nest.generator.generator;

import com.zhaofujun.nest.generator.model.NEntity;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

public abstract class Generator {
    public abstract Template getTemplate(Configuration cfg);

    public abstract Object getDataModel(NEntity nEntity);

    public abstract Writer getWriter();


    public void generate(NEntity nEntity) throws IOException, TemplateException {
        //配置freemarker
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setDirectoryForTemplateLoading(new File("./templates"));
        cfg.setDefaultEncoding("UTF-8");
//       cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW);

        //生成数据模型
        Object dataModel = getDataModel(nEntity);
        //读取模板
        Template temp = getTemplate(cfg);
        try (Writer writer = getWriter()) {
            temp.process(dataModel, writer);
        }

    }
}
