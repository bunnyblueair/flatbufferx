package io.flatbufferx.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

public class FlutterBufferXAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        System.out.println(e.getProject().getBasePath());
        final VirtualFile virtualFolder = e.getData(LangDataKeys.VIRTUAL_FILE);

        if (virtualFolder == null) {
            Messages.showErrorDialog("Please select package", "Error Display");
            return;
        }
        VirtualFile virtualFileSourcePath = ProjectRootManager.getInstance(e.getProject()).getFileIndex().getSourceRootForFile(virtualFolder);

        if (virtualFileSourcePath == null) {
            Messages.showErrorDialog("Please select source root", "Error Display");
        }


        String packegeName = ProjectRootManager.getInstance(e.getProject()).getFileIndex().getPackageNameByDirectory(virtualFolder);
        if (packegeName == null || packegeName.isEmpty()) {
            Messages.showErrorDialog("Please select package", "Error Display");
            return;
        }

        FlatBufferXDialog dialog = new FlatBufferXDialog(e, packegeName, virtualFileSourcePath.getPath());
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setVisible(true);

    }


}
