package Qvog_Plugin.query;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QueryDialogWrapper extends DialogWrapper {
    private static final Logger log = LoggerFactory.getLogger(QueryDialogWrapper.class);
    private String Language;

    private JBList<String> list;

    private DefaultListModel<String> defaultListModel;

    public QueryDialogWrapper(Project project, String language){
        super(project);
        Language = language;
        init();
        setTitle("Select Query");
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        defaultListModel = new DefaultListModel<>();
        List<String> queryList = new ArrayList<>();
        if(Language.equals("cxx"))
        {
            queryList = CQueryType.getCQueryTypeList();
        }
        else
        {
            queryList = JavaQueryType.getJavaQueryList();
        }

        for (int i = 0; i < queryList.size(); i++)
        {
            defaultListModel.addElement(queryList.get(i));
        }
        list = new JBList<>(defaultListModel);

        // 修饰每一行的元素
        ColoredListCellRenderer<String> coloredListCellRenderer = new ColoredListCellRenderer<String>() {
            @Override
            protected void customizeCellRenderer(@NotNull JList<? extends String> list, String value, int index, boolean selected, boolean hasFocus) {
                append(value);
            }
        };
        // 添加行高
        list.setFixedCellHeight(30);
        // 添加行高
        list.setFixedCellWidth(250);
        // 设置选择模式
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        //
        list.setCellRenderer(coloredListCellRenderer);
        //list.setPreferredSize(new Dimension(256, 512));

        JPanel panel = new JPanel();
        JBScrollPane scrollPane = new JBScrollPane(list);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel("Select a Query:"), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(320, 640));
//        panel.add(decorator.createPanel(), BorderLayout.CENTER);
        return panel;
    }

    public List<String> getSelectedQuery() {
        List<String> res = new ArrayList<>();
        for(String value: list.getSelectedValuesList())
        {
            if(res.equals("all"))
            {
                res.clear();
                res.add("all");
                return res;
            }
            if(Language.equals("cxx"))
            {
                res.add(CQueryType.getCQueryType(value));
            }
            else
            {
                res.add(JavaQueryType.getJavaQueryType(value));
            }

        }
        return res;
    }

    @NotNull

    @Override
    protected @Nullable ValidationInfo doValidate() {
        return super.doValidate();
    }
}
