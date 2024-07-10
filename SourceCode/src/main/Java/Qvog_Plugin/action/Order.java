package Qvog_Plugin.action;

import kotlin.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.Callable;

import static java.nio.channels.FileChannel.open;

public class Order {
    private static final Logger log = LoggerFactory.getLogger(Order.class);

    //
    public static void main(String[] args) throws Exception {
//        boolean res = CQuery(
//                "E:/File/CGQL",
//                "E:/File/data/c");
//        boolean res = C2Graph("E:/File/CGQL",
//                "E:/File/data/c", "target.c");
//        System.out.println(res);
    }

    public static boolean C2Graph(String toolsPath, String targetPath,String fileName) throws IOException, InterruptedException {
        String C2Path = toolsPath + "/c2graph/";
        String version = System.getProperty("os.name");
        boolean res = move(C2Path + "input/", targetPath + "/" + fileName);
        if(!res)
        {
            return false;
        }
        String cmd = "java -jar C2Graph.jar";
        ProcessBuilder processBuilder = new ProcessBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String GraphPath = targetPath + "/C2Graph" + sdf.format(System.currentTimeMillis()) + ".log";
        boolean result;
        if(version.toLowerCase().contains("windows"))
        {
            processBuilder.command("cmd.exe", "/c", cmd);

        } else if (version.toLowerCase().contains("linux")) {
            processBuilder.command("bash", "-c", cmd);
        }
        else {
            System.out.println("error");
            return false;
        }
        result = ExecProcess(C2Path, processBuilder, GraphPath);
        res = delete(C2Path + "input/" + fileName);
        if(!res)
        {
            return false;
        }
        else
            return result;
    }

    public static Map<String, List<Pair<String, String>>> QueryForTarget(
            String toolsPath,
            String targetPath,
            List<String> Queries,
            String language
    ) throws IOException, InterruptedException
    {
        String QueryPath = toolsPath + "/Query/";
        String version = System.getProperty("os.name");
        String cmd = "java -jar QVogine.jar --language " + language;

        if(Queries.size() == 1 && Queries.get(0).equals("all"))
        {
            cmd += "";
        }
        else
        {
            cmd += " --query ";
            for(String query : Queries)
            {
                cmd += query + " ";
            }
        }

        ProcessBuilder processBuilder = new ProcessBuilder();
        if(version.toLowerCase().contains("windows"))
        {
            processBuilder.command("cmd.exe", "/c", cmd);

        } else if (version.toLowerCase().contains("linux")) {
            processBuilder.command("bash", "-c", cmd);
        }
        else {
            System.out.println("error");
            return null;
        }
        File directory = new File(QueryPath);
        processBuilder.directory(directory);

        try{
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            List<String> lines = new ArrayList<>();

            StringBuilder res = new StringBuilder();
            while((line = reader.readLine()) != null)
            {
                System.out.println(line);
                lines.add(line);
            }
            int exitCode = process.waitFor();
            System.out.println("Exit with " + String.valueOf(exitCode));
            if(exitCode == 0)
            {
                return queryResultProcess(lines);
            }
            else {
                return null;
            }
        } catch (IOException | InterruptedException exception)
        {
            exception.printStackTrace();
            return null;
        }
    }

    public static boolean Java2Graph(String toolsPath, String targetPath, String fileName) throws IOException, InterruptedException {
        String JavaPath = toolsPath + "/java2graph/";

        boolean res = move(JavaPath + "input/", targetPath + "/" + fileName);
        if(!res)
        {
            return false;
        }
        String version = System.getProperty("os.name");
        String cmd = "java -jar Java2Graph.jar -i input -o output";
        ProcessBuilder processBuilder = new ProcessBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String GraphFile = targetPath + "/Java2Graph" + sdf.format(System.currentTimeMillis()) + ".log";
        boolean result;
        if(version.toLowerCase().contains("windows"))
        {
            processBuilder.command("cmd.exe", "/c", cmd);
        } else if (version.toLowerCase().contains("linux")) {
            processBuilder.command("bash", "-c", cmd);
        }
        else {
            System.out.println("error");
            return false;
        }
        result = ExecProcess(JavaPath, processBuilder, GraphFile);
        res = delete(JavaPath + "input/" + fileName);
        if(!res)
        {
            return false;
        }
        else
            return result;
    }

    public static boolean move(String filePath, String targetFilePath) throws IOException, InterruptedException {
        String version = System.getProperty("os.name");
        ProcessBuilder processBuilder = new ProcessBuilder();
        if(version.toLowerCase().contains("windows"))
        {
            String cmd = "copy /y " + targetFilePath.replace("/", "\\") + " " + filePath.replace("/", "\\");
            processBuilder.command("cmd.exe", "/c", cmd);
        } else if (version.toLowerCase().contains("linux")) {
            String cmd = "cp " + targetFilePath + " " + filePath;
            processBuilder.command("bash", "-c", cmd);
        }
        else {
            System.out.println("error");
            return false;
        }
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        System.out.println("Exit with " + String.valueOf(exitCode));
        if(exitCode == 0)
        {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean delete(String targetFilePath) throws IOException, InterruptedException {
        String version = System.getProperty("os.name");
        ProcessBuilder processBuilder = new ProcessBuilder();
        if(version.toLowerCase().contains("windows"))
        {
            String cmd = "del " + targetFilePath;
            processBuilder.command("cmd.exe", "/c", cmd.replace("/", "\\"));
        } else if (version.toLowerCase().contains("linux")) {
            String cmd = "rm " + targetFilePath;
            processBuilder.command("bash", "-c", cmd);
        }
        else {
            System.out.println("error");
            return false;
        }
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        System.out.println("Exit with " + String.valueOf(exitCode));
        if(exitCode == 0)
        {
            return true;
        }
        else {
            return false;
        }
    }


    private static boolean ExecProcess(String path, ProcessBuilder processBuilder, String filename) {
        File directory = new File(path);
        processBuilder.directory(directory);
        try{
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            System.out.println("Exit with " + String.valueOf(exitCode));

            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            StringBuilder res = new StringBuilder();
            while((line = reader.readLine()) != null)
            {
                System.out.println(line);
                res.append(line).append("\r\n");
            }
            Files.write(Paths.get(filename), res.toString().getBytes());
            if(exitCode == 0)
            {
                return true;
            }
            else {
                return false;
            }
        } catch (IOException | InterruptedException exception)
        {
            exception.printStackTrace();
            return false;
        }
    }


    private static Map<String, List<Pair<String, String>>> queryResultProcess(List<String> lines) {
        int i = 1;
        int j = 0;
        Map<String, List<Pair<String, String>>> map = new HashMap<>();
        List<String> Queries = new ArrayList<>();
        List<Pair<String, String>> pairs = new ArrayList<>();
        boolean whetherSink = false;
        while (i < lines.size()) {
            String line = lines.get(i);
            if (line.equals("Queries to run:")) {
                i++;
                while (i < lines.size() && !lines.get(i).equals("==========")) {
                    Queries.add(lines.get(i));
                    i++;
                }
            }
            if (line.startsWith("|") && line.contains("source")) {
                if (line.contains("sink")) {
                    whetherSink = true;
                }
                i += 2;
                while (i < lines.size() && lines.get(i).startsWith("|")) {
                    String[] str = lines.get(i).split("\\|");
                    String source = str[1].trim();
                    String sink = "";
                    if (whetherSink) {
                        sink = str[2].trim();
                    }
                    Pair<String, String> pair = new Pair<>(source, sink);
                    if (!pairs.contains(pair)) {
                        pairs.add(pair);
                    }
                    i++;
                }
                if (pairs.size() > 0) {
                    map.put(Queries.get(j), new ArrayList<>(pairs));
                }
                pairs.clear();
                whetherSink = false;
                i++;
                j++;
            } else {
                i++;
            }
        }
        return map;
    }

}
