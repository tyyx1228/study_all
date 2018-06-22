package com.ty.study.tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class PrintFile extends JPanel {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 200;
    private JFrame mainFrame;
    private int onlyFile = 0;
    private String suffix = "输入文件后缀";
    private int subStart = 0;

    public PrintFile() {
        //设置窗口的位置信息
        mainFrame = new JFrame("目录打印工具---QQ:171418298  maoxiangyi.cn");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        mainFrame.add(this, BorderLayout.WEST);
        mainFrame.setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        int x = (width - WIDTH) / 2;
        int y = (height - HEIGHT) / 2;
        mainFrame.setLocation(x, y);

        //设置窗口中的组件内容
        final JLabel label = new JLabel("选择目录");
        this.add(label);
        final JTextField selectPath = new JTextField(20);
        this.add(selectPath);
        JButton viewButton = new JButton("....");
        this.add(viewButton);
        final JCheckBox onlyFileCheckBox = new JCheckBox("只显示文件", true);
        this.add(onlyFileCheckBox);
        final JTextField fileSuffix = new JTextField(7);
        fileSuffix.setText("输入文件后缀");
        fileSuffix.setVisible(true);
        this.add(fileSuffix);
        final JLabel tips = new JLabel("目录信息");
        this.add(tips);
        final JTextArea txaDisplay = new JTextArea();
        JScrollPane resultArea = new JScrollPane(txaDisplay);
        resultArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(resultArea);

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jfc.showDialog(new JLabel(), "选择");
                File file = jfc.getSelectedFile();
                subStart = file.getAbsoluteFile().toString().length()+1;
                selectPath.setText(file.getAbsolutePath());
                StringBuffer sb = new StringBuffer();
                printFile(file, sb);
                txaDisplay.setText(sb.toString());
            }
        });

        onlyFileCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                onlyFile = e.getStateChange();
            }
        });
        fileSuffix.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                fileSuffix.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                suffix = fileSuffix.getText();
            }
        });


        //设置网络布局
        GridBagConstraints constraints = new GridBagConstraints();
        //是用来控制添加进的组件的显示位置
        constraints.fill = GridBagConstraints.BOTH;
        //该方法是为了设置如果组件所在的区域比组件本身要大时的显示情况
        //NONE：不调整组件大小。
        //HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
        //VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
        //BOTH：使组件完全填满其显示区域。
        constraints.gridwidth = 1;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
        constraints.weightx = 0;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        constraints.weighty = 0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        layout.setConstraints(label, constraints);//设置组件
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        constraints.weighty = 0;
        layout.setConstraints(selectPath, constraints);
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        constraints.weighty = 0;
        layout.setConstraints(viewButton, constraints);
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        constraints.weighty = 0;
        layout.setConstraints(onlyFileCheckBox, constraints);
        constraints.gridwidth = 0;
        constraints.weightx = 1;
        constraints.weighty = 0;
        layout.setConstraints(fileSuffix, constraints);
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        layout.setConstraints(tips, constraints);
        constraints.gridwidth = 0;
        constraints.weightx = 0;
        constraints.weighty = 1;
        layout.setConstraints(resultArea, constraints);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }

    private void printFile(File file, StringBuffer stringBuffer) {
        if (file.isDirectory()) {
            if (onlyFile == 2) {
                stringBuffer.append(file.getAbsolutePath().substring(subStart));
                stringBuffer.append("\n");
            }
            File[] files = file.listFiles();
            for (File f : files) {
                printFile(f, stringBuffer);
            }
        } else {
            if (!"输入文件后缀".equals(suffix)) {
                if (file.getAbsoluteFile().toString().endsWith(suffix)) {
                    stringBuffer.append(file.getAbsolutePath().substring(subStart));
                    stringBuffer.append("\n");
                }
            }else {
                stringBuffer.append(file.getAbsolutePath().substring(subStart));
                stringBuffer.append("\n");
            }
        }

    }

    public static void main(String[] args) {
        PrintFile printFile = new PrintFile();
    }
}