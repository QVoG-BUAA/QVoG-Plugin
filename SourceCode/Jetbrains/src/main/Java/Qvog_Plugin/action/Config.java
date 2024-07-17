package Qvog_Plugin.action;

import Qvog_Plugin.action.ServerState;
import Qvog_Plugin.config.ConfigJsonCheck;
import Qvog_Plugin.config.InputWrapper;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;

public class Config extends AnAction{
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        ServerState server = ServerState.getInstance(project);
        InputWrapper wrapper = new InputWrapper(project);
        if(wrapper.showAndGet())
        {
            Map<String, String> in = wrapper.get();
            String ip = in.get("ip");
            int port = Integer.parseInt(in.get("port"));
            String filePathSplit = File.separator;
            String toolsPath = in.get("path");
            if (checkIp(ip) && checkPort(port) && checkTools(toolsPath)) {
                if(filePathSplit.equals("\\"))
                {
                    toolsPath = toolsPath.replace("/", "\\");
                }
                else
                {
                    toolsPath = toolsPath.replace("\\", "/");
                }
                if(toolsPath.endsWith("/") || toolsPath.endsWith("\\"))
                {
                    toolsPath = toolsPath.substring(0, toolsPath.length() - 1);
                }
                server.load(ip, port, toolsPath);

                ConfigJsonCheck.C2GraphConfigCheck(ip, port,
                        toolsPath + filePathSplit + "c2graph" + filePathSplit + "input",
                        toolsPath + filePathSplit + "c2graph" + filePathSplit + "config.json");
                ConfigJsonCheck.Java2GraphConfig(ip, port,
                        toolsPath + filePathSplit + "java2graph" + filePathSplit + "config.json");
                ConfigJsonCheck.QueryConfig(ip, port,
                        toolsPath + filePathSplit + "Query" + filePathSplit + "config.json");

                Messages.showMessageDialog(project, "Initialized successfully","Success", Messages.getInformationIcon());
            }
            else
            {
                Messages.showMessageDialog(project, "Input data incorrect, please re-enter","Error", Messages.getInformationIcon());
            }
        }
        else
        {
            Messages.showMessageDialog(project, "An error occurred during input, please re-enter","Error", Messages.getInformationIcon());
        }
    }

    public static boolean checkIp(String ip) {
        String[] ips = ip.split("\\.");
        if(ips.length != 4) {
            return false;
        }
        else
        {
            for(int i = 0; i < 4; i++)
            {
                int i1 = Integer.parseInt(ips[i]);
                if(i1 > 255 || i1 < 0)
                {
                    return false;
                }
            }
            return true;
        }
    }

    public static boolean checkPort(int port) {
        if(port < 0 || port > 65535) {
            return false;
        }
        else
            return true;
    }

    public static boolean checkTools(String toolsPath) {
        return !toolsPath.isEmpty();
    }

}
