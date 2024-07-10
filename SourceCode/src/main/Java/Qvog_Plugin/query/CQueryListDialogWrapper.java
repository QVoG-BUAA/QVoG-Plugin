package Qvog_Plugin.query;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.ListSpeedSearch;
import com.intellij.ui.components.JBList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

public class CQueryListDialogWrapper extends DialogWrapper {
    private JBList<String> list;

    private DefaultListModel<String> defaultListModel;

    public CQueryListDialogWrapper(Project project) {
        super(project);
        init();
        setTitle("C Query");
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        defaultListModel = new DefaultListModel<>();
        for (String query : CQueryType.getCQueryTypeList()) {
            defaultListModel.addElement(query);
        }
        list = new JBList<>(defaultListModel);

        // 修饰每一行的元素
        ColoredListCellRenderer<String> coloredListCellRenderer = new ColoredListCellRenderer<String>() {
            @Override
            protected void customizeCellRenderer(@NotNull JList<? extends String> list, String value, int index, boolean selected, boolean hasFocus) {
                append(value);
            }
        };

        list.setCellRenderer(coloredListCellRenderer);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel("Select a Query:"), BorderLayout.NORTH);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
//        panel.add(decorator.createPanel(), BorderLayout.CENTER);
        return panel;
    }

    public String getSelectedQuery() {
        return CQueryType.getCQueryType(list.getSelectedValue());
    }

    @NotNull

    @Override
    protected @Nullable ValidationInfo doValidate() {
        return super.doValidate();
    }
}
