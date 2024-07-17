package Qvog_Plugin.query;

import java.lang.reflect.Field;
import java.util.*;

public class CQueryType {
    private static List<String> CQueryTypeList = new ArrayList<>();
    private static Map<String, String> CQueryTypeMap = new TreeMap<String, String>();
    private static CQueryType cQueryType = new CQueryType();

    public static CQueryType getInstance() {
        return cQueryType;
    }

    private CQueryType() {
        // 增加新的Query后，请添加到List和Map中
        CQueryTypeMap.put("FileNotClose", "misuse.locationProblem.forget.FileNotClose");
        CQueryTypeMap.put("MemoryNotDelete", "misuse.locationProblem.forget.MemoryNotDelete");
        CQueryTypeMap.put("MemoryNotFree", "misuse.locationProblem.forget.MemoryNotFree");
        CQueryTypeMap.put("SocketNotClose", "misuse.locationProblem.forget.SocketNotClose");
        CQueryTypeMap.put("MutexNotReleased", "misuse.locationProblem.occupy.MutexNotReleased");
        CQueryTypeMap.put("FileUseAfterClose", "misuse.locationProblem.premature.FileUseAfterClose");
        CQueryTypeMap.put("MysqlConnUseAfterClose", "misuse.locationProblem.premature.MysqlConnUseAfterClose");
        CQueryTypeMap.put("UseAfterDelete", "misuse.locationProblem.premature.UseAfterDelete");
        CQueryTypeMap.put("UseAfterFree", "misuse.locationProblem.premature.UseAfterFree");
        CQueryTypeMap.put("CalculateArrayOffset", "misuse.matchProblem.CalculateArrayOffset");
        CQueryTypeMap.put("CalculateArraySize", "misuse.matchProblem.CalculateArraySize");
        CQueryTypeMap.put("MallocWithDelete", "misuse.matchProblem.MallocWithDelete");
        CQueryTypeMap.put("NewWithDeleteArray", "misuse.matchProblem.NewWithDeleteArray");
        CQueryTypeMap.put("NewWithFree", "misuse.matchProblem.NewWithFree");
        CQueryTypeMap.put("RandAndSrand", "misuse.matchProblem.RandAndSrand");
        CQueryTypeMap.put("ReadFromWriteOnly", "misuse.matchProblem.ReadFromWriteOnly");
        //CQueryTypeMap.put("ReadFromReadWrite", "misuse.matchProblem.ReadFromReadWrite");
        CQueryTypeMap.put("SeekAtAppend", "misuse.matchProblem.SeekAtAppend");
        CQueryTypeMap.put("StrlenAfterMalloc", "misuse.matchProblem.StrlenAfterMalloc");
        CQueryTypeMap.put("StrlenInMalloc", "misuse.matchProblem.StrlenInMalloc");
        CQueryTypeMap.put("Write2ReadOnly", "misuse.matchProblem.Write2ReadOnly");
        CQueryTypeMap.put("FprintfArgs", "misuse.missingProblem.functionArgs.FprintfArgs");
        CQueryTypeMap.put("OpenArgs", "misuse.missingProblem.functionArgs.OpenArgs");
        CQueryTypeMap.put("PrintfArgs", "misuse.missingProblem.functionArgs.PrintfArgs");
        CQueryTypeMap.put("RsaSize", "misuse.missingProblem.functionArgs.RsaSize");
        CQueryTypeMap.put("FgetsReturn", "misuse.missingProblem.functionReturn.FgetsReturn");
        CQueryTypeMap.put("FunctionReturnCheck", "misuse.missingProblem.functionReturn.FunctionReturnCheck");
        CQueryTypeMap.put("Memcpy", "misuse.missingProblem.overflowCheck.Memcpy");
        CQueryTypeMap.put("SocketRecv", "misuse.missingProblem.overflowCheck.SocketRecv");
        CQueryTypeMap.put("Sprintf", "misuse.missingProblem.overflowCheck.Sprintf");
        CQueryTypeMap.put("Dlopen", "misuse.missingProblem.taintCheck.Dlopen");
        CQueryTypeMap.put("HtmlEscape", "misuse.missingProblem.taintCheck.HtmlEscape");
        CQueryTypeMap.put("ShellCommand", "misuse.missingProblem.taintCheck.ShellCommand");
        CQueryTypeMap.put("SqlCommand", "misuse.missingProblem.taintCheck.SqlCommand");
        CQueryTypeMap.put("Strcat", "misuse.missingProblem.taintCheck.Strcat");
        CQueryTypeMap.put("Encrypt3DES", "misuse.outdateProblem.Encrypt3DES");
        CQueryTypeMap.put("FunctionLastUseOptimize", "misuse.outdateProblem.FunctionLastUseOptimize");
        CQueryTypeMap.put("FunctionOutdated", "misuse.outdateProblem.FunctionOutdated");
        CQueryTypeMap.put("DoubleDelete", "misuse.twiceProblem.DoubleDelete");
        CQueryTypeMap.put("DoubleFree", "misuse.twiceProblem.DoubleFree");
        CQueryTypeMap.put("DoubleLock", "misuse.twiceProblem.DoubleLock");
        CQueryTypeMap.put("FileDoubleClose", "misuse.twiceProblem.FileDoubleClose");
        CQueryTypeMap.put("SocketDoubleClose", "misuse.twiceProblem.SocketDoubleClose");
        CQueryTypeList.add("all");
        CQueryTypeList.addAll(CQueryTypeMap.keySet());
//        CQueryTypeList = CQueryTypeMap.();
    }

    public static List<String> getCQueryTypeList() {
        return CQueryTypeList;
    }

    public static String getCQueryType(String key) {
        if(key.equals("all"))
        {
            return "all";
        }
        else return CQueryTypeMap.get(key);
    }

    public static void main(String[] args) {
        System.out.println("CQueryTypeMap: " + CQueryTypeMap);
    }
}