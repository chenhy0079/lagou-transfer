package com.lagou.edu.utils;

import com.lagou.edu.dao.impl.JdbcAccountDaoImpl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class CommentScan {


    public static  List<Class> findClass(String packageName) throws IOException, ClassNotFoundException {
        return findClass(packageName, new ArrayList<>());
    }


    private static List<Class> findClass(String packageName, List<Class> clazzs) throws ClassNotFoundException, IOException {
        //将报名替换成目录
        String fileName = packageName.replaceAll("\\.", "/");
        //通过classloader来获取文件列表
        String file1 = CommentScan.class.getClassLoader().getResource(fileName).getFile();
        File file = new File(file1);

        File[] files = file.listFiles();
        for (File f:files) {
            //如果是目录，这进一个寻找
            if (f.isDirectory()) {
                //截取路径最后的文件夹名
                String currentPathName = f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf(File.separator)+1);
                //进一步寻找
                findClass(packageName+"."+currentPathName, clazzs);
            } else {
                //如果是class文件
                if (f.getName().endsWith(".class")) {
                    //反射出实例

                    Class clazz = CommentScan.class.getClassLoader().loadClass(packageName+"."+f.getName().replace(".class",""));
                    clazzs.add(clazz);
                }
            }
        }
        return clazzs;
    }
}
