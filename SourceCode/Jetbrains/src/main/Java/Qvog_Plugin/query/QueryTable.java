package Qvog_Plugin.query;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTabbedPane;
import com.intellij.ui.table.JBTable;
import kotlin.Pair;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class QueryTable extends DialogWrapper {
    private static final Logger log = LoggerFactory.getLogger(QueryTable.class);
    private Map<String, List<Pair<String, String>>> result;

    public QueryTable(Project project, Map<String, List<Pair<String, String>>> input) {
        super(project);
        this.result = input;
        init();
        setTitle("Qvog Result");
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JBTabbedPane tabbedPane = new JBTabbedPane();
        for(Map.Entry<String, List<Pair<String, String>>> entry : result.entrySet()) {
            JPanel panel1 = new JPanel();
            JLabel label = new JLabel(entry.getKey());
            List<Pair<String, String>> pairs = entry.getValue();
            JBTable table = new JBTable();
            table.setName(entry.getKey());
            DefaultTableModel model = new DefaultTableModel();
//            model.addColumn("Source");
            String[] source = new String[pairs.size()];
            table.setShowColumns(true);
            if(!pairs.get(0).component2().equals(""))
            {
//                model.addColumn("Sink");
                String[] sink = new String[pairs.size()];
                for(int i = 0; i < pairs.size(); i++ )
                {
                    source[i] = pairs.get(i).component1();
                    sink[i] = pairs.get(i).component2();
                }
                model.addColumn("Source", source);
                model.addColumn("Sink", sink);
            }
            else
            {
                for(int i = 0; i < pairs.size(); i++ )
                {
                    source[i] = pairs.get(i).component1();
                }
                model.addColumn("Source", source);
            }
            table.setModel(model);
            table.setRowHeight(30);
            table.setFillsViewportHeight(true);
            table.setAutoResizeMode(JBTable.AUTO_RESIZE_LAST_COLUMN);
            table.setAutoCreateRowSorter(true);
            table.setPreferredSize(new Dimension(1300, 300));
            JBScrollPane scrollPane = new JBScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setPreferredSize(new Dimension(1300, 300));
            panel1.add(scrollPane, BorderLayout.CENTER);
            panel1.setPreferredSize(new Dimension(1300, 384));
            String[] keys = entry.getKey().split("\\.");
            tabbedPane.addTab(keys[keys.length -1], panel1);
        }
        tabbedPane.setSelectedIndex(0);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.addChangeListener(e -> {
            int n = tabbedPane.getSelectedIndex();
            tabbedPane.setSelectedComponent(tabbedPane.getComponentAt(n));
        });
        tabbedPane.setPreferredSize(new Dimension(1300, 384));
        panel.add(tabbedPane, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(1300, 384));
        return panel;
    }
}
