package Qvog_Plugin.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class JavaQueryType {
    private static List<String> JavaQueryList = new ArrayList<>();
    private static Map<String, String> JavaQueryTypeMap = new TreeMap<String, String>();
    private static JavaQueryType JavaQueryType = new JavaQueryType();
    public static JavaQueryType getInstance() {
        return JavaQueryType;
    }

    private JavaQueryType() {
        // 增加新的Query后，请添加到List和Map中
        JavaQueryTypeMap.put("IntegerOverflow", "IntegerOverflow");
        JavaQueryList.add("all");
        JavaQueryList.add("IntegerOverflow");
    }

    public static List<String> getJavaQueryList() {
        return JavaQueryList;
    }

    public static String getJavaQueryType(String key) {
        if(key.equals("all"))
        {
            return "all";
        }
        else return JavaQueryTypeMap.get(key);
    }
}
