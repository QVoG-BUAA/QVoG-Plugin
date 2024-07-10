package Qvog_Plugin.action;

import Qvog_Plugin.query.QueryDialogWrapper;
import Qvog_Plugin.query.QueryTable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Query extends AnAction{
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        ServerState server = ServerState.getInstance(project);
        if(server == null || server.getState().ip == null ||server.getState().ip.isEmpty()) {
            Messages.showMessageDialog(project, "The plugin is not initialized, please initialize the config first",
                    "Error", Messages.getInformationIcon());
            return;
        }
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        String dirPath = psiFile.getVirtualFile().getParent().getPath();
        String fileName = psiFile.getName();
//        String lastFileName = server.getState().targetFileName;
        if(fileName.endsWith(".c") || fileName.endsWith(".cpp")) {
             try {
                QueryDialogWrapper dialog = new QueryDialogWrapper(project, "cxx");
                if(dialog.showAndGet()) {
                    List<String> queries = dialog.getSelectedQuery();
                    dialog.close(0);
                    Map<String, List<Pair<String, String>>> res =
                            Order.QueryForTarget(server.getState().toolsPath, dirPath, queries, "cxx");
                    if(res != null)
                    {
                        QueryTable cQueryTable = new QueryTable(project, res);
                        cQueryTable.show();
                    }
                    else
                    {
                        Messages.showMessageDialog(project, "CQuery failed",
                                "Error", Messages.getInformationIcon());
                    }
                }
                else {
                    dialog.close(0);
                    Messages.showMessageDialog(project, "Something failed",
                            "Error", Messages.getInformationIcon());
                }
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        } else if (fileName.endsWith(".java")) {
            try {
                QueryDialogWrapper dialog = new QueryDialogWrapper(project, "java");
                if (dialog.showAndGet()) {
                    List<String> queries = dialog.getSelectedQuery();
                    dialog.close(0);
                    Map<String, List<Pair<String, String>>> res =
                            Order.QueryForTarget(server.getState().toolsPath, dirPath, queries, "java");
                    if (res != null) {
                        QueryTable queryTable = new QueryTable(project, res);
                        queryTable.show();
                    } else {
                        Messages.showMessageDialog(project, "JavaQuery failed",
                                "Error", Messages.getInformationIcon());
                    }
                }
                else {
                    dialog.close(0);
                    Messages.showMessageDialog(project, "Something failed",
                            "Error", Messages.getInformationIcon());
                }
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        } else
        {
            Messages.showMessageDialog(project, "The plugin currently does not support this file type",
                    "Error", Messages.getInformationIcon());
        }
    }
}
