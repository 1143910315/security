package com.linjiahao.security.tools;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class YmlReader {
    /**
     * @param key 从application.yml读取指定键值（支持形如server.port[0]的对象访问），在字符前加斜杠（\）可以转义为文本，但不保证正常工作
     * @return 返回从yml读出的对象
     */
    public static Object load(String key) {
        Object o = null;
        try {
            Yaml yaml = new Yaml();
            URL url = YmlReader.class.getClassLoader().getResource("application.yml");
            if (url != null) {
                //获取test.yaml文件中的配置数据，然后将值转换为Map
                Object obj = yaml.load(new FileInputStream(url.getFile()));
                o = obj;
                int start = 0;
                int len = key.length();
                boolean tr = false;
                StringBuilder sb = new StringBuilder();
                boolean inKeyName = true, inArray = false, inArrayIndex = false;
                while (start < len) {
                    char c = key.charAt(start++);
                    if (tr) {
                        sb.append(c);
                        tr = false;
                        continue;
                    }
                    switch (c) {
                        case '.':
                            o = getObject(o, sb.toString(), inKeyName, inArray, inArrayIndex);
                            inKeyName = true;
                            inArrayIndex = inArray = false;
                            sb.delete(0, sb.length());
                            break;
                        case '[':
                            o = getObject(o, sb.toString(), inKeyName, inArray, inArrayIndex);
                            inArray = true;
                            inKeyName = inArrayIndex = false;
                            sb.delete(0, sb.length());
                            break;
                        case ']':
                            o = getObject(o, sb.toString(), inKeyName, inArray, inArrayIndex);
                            inArrayIndex = true;
                            inKeyName = inArray = false;
                            sb.delete(0, sb.length());
                            break;
                        case '\\':
                            tr = true;
                            break;
                        default:
                            sb.append(c);
                    }
                }
                o = getObject(o, sb.toString(), inKeyName, inArray, inArrayIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
            o = null;
        }
        return o;
    }

    private static Object getObject(Object obj, String key, boolean inKeyName, boolean inArray, boolean inArrayIndex) {
        try {
            //如果字符串为空，则什么也不做
            if (key != null && !key.equals("")) {
                if (inKeyName) {
                    Map<String, Object> map = (Map<String, Object>) obj;
                    obj = map.get(key);
                } else if (inArray) {
                    List<Object> list = (List<Object>) obj;
                    obj = list.get(Integer.parseInt(key));
                } else if (inArrayIndex) {
                    System.out.println("bug");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * @param key 从application.yml读取指定键值（不支持数组形式，仅支持形如server.port的对象访问）
     * @return 如果出错或未找到，返回null，找到时，返回值可能因配置文件定义问题，返回map，list或string等
     */
    public static Object load_old(String key) {
        Object o = null;
        try {
            Yaml yaml = new Yaml();
            URL url = YmlReader.class.getClassLoader().getResource("application.yml");
            if (url != null) {
                //获取test.yaml文件中的配置数据，然后将值转换为Map
                Map<String, Object> map = yaml.load(new FileInputStream(url.getFile()));
                String[] split = key.split("\\.");
                for (String s : split) {
                    o = map.get(s);
                    try {
                        map = (Map<String, Object>) o;
                    } catch (Exception e) {
                        map = null;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return o;
    }
}
