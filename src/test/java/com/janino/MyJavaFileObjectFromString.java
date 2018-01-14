package com.janino;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;


public class MyJavaFileObjectFromString extends SimpleJavaFileObject {

    /**
     * Java源文件的内容
     */
    final String code;

    /**
     * @param className 类的完整名称
     * @param code
     */
    public MyJavaFileObjectFromString(String className, String code) {
        //uri为类的资源定位符号, 如: com/stone/generate/Hello.java
        super(URI.create(className.replaceAll("\\.", "/") + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
}

