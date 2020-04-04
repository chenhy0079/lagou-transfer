package com.lagou.edu.factory;

import com.lagou.edu.annotation.Autowird;
import com.lagou.edu.annotation.Service;
import com.lagou.edu.annotation.Transactional;
import com.lagou.edu.utils.CommentScan;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author 应癫
 *
 * 工厂类，生产对象（使用反射技术）
 */
public class AnnotationBeanFactory {

    /**
     * 任务一：读取解析xml，通过反射技术实例化对象并且存储待用（map集合）
     * 任务二：对外提供获取实例对象的接口（根据id获取）
     */

    private static Map<String,Object> map = new HashMap<>();  // 存储对象



    static {


        try {
            List<Class> classes = CommentScan.findClass("com.lagou.edu");

            for (Class aClass : classes) {

                setService(aClass);
            }

            setFeild();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void setFeild()throws  Exception{


        Collection<Object> values = map.values();

        Set<Map.Entry<String, Object>> entries = map.entrySet();

        for (Map.Entry<String, Object> entry : entries) {

            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if(field.getAnnotation(Autowird.class)!=null){

                    for (Object value : values) {
                        if(field.getType() == value.getClass()){
                            field.set(entry.getValue(),value);
                        }else {

                            List<Class<?>> classes = Arrays.asList(value.getClass().getInterfaces());

                            if(classes.contains(field.getType())){
                                field.set(entry.getValue(),value);
                            }
                        }
                    }


                }
            }
        }
    }

    private static void setService(Class<?> className)throws  Exception{

        Service service = className.getAnnotation(Service.class);

        if(service!=null){
            Object o = className.newInstance();
            if(service.value()!=null&&!service.value().equals("")){
                map.put(service.value(), o);
            }else{
                map.put(lowerFirstCapse(className.getName()), className.newInstance());
            }

        }

    }


    /**
     * 首字母小写
     * @param str
     * @return
     */
    public static String lowerFirstCapse(String str){

        str=str.substring(str.lastIndexOf(".")+1, str.length());

        char[]chars = str.toCharArray();

        chars[0] += 32;

        return String.valueOf(chars);

    }
    // 任务二：对外提供获取实例对象的接口（根据id获取）
    public static  Object getBean(String id) {
        return map.get(id);
    }

}
