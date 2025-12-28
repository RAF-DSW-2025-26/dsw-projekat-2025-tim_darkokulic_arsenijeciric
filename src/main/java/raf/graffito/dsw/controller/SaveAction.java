package raf.graffito.dsw.controller;

import raf.graffito.dsw.core.ApplicationFramework;
import raf.graffito.dsw.gui.swing.MainFrame;
import raf.graffito.dsw.model.Project;
import raf.graffito.dsw.model.repository.GraffRepository;
import raf.graffito.dsw.serialization.Serializer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class SaveAction extends AbstractGraffAction {

    private String currentProjectPath;

    public SaveAction() {
        putValue(NAME, "Save");
        putValue(SHORT_DESCRIPTION, "Save project");
        putValue(ACCELERATOR_KEY, javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
    }

    public void setCurrentProjectPath(String path) {
        this.currentProjectPath = path;
    }

    public String getCurrentProjectPath() {
        return currentProjectPath;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GraffRepository repository = ApplicationFramework.getInstance().getRepository();
        Project project = getSelectedProject(repository);

        if (project == null) {
            JOptionPane.showMessageDialog(
                    MainFrame.getInstance(),
                    "Please select a project to save.",
                    "No Project Selected",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String filePath = currentProjectPath;

        if (filePath == null || filePath.isEmpty()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));
            fileChooser.setDialogTitle("Save Project");

            if (fileChooser.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.endsWith(".json")) {
                    filePath += ".json";
                }
            } else {
                return;
            }
        }

        try {
            Serializer serializer = new Serializer();
            serializer.saveToFile(project, filePath);
            currentProjectPath = filePath;
            JOptionPane.showMessageDialog(
                    MainFrame.getInstance(),
                    "Project saved successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    MainFrame.getInstance(),
                    "Failed to save project: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private Project getSelectedProject(GraffRepository repository) {
        for (raf.graffito.dsw.model.GraffNode node : repository.getWorkspace().getChildren()) {
            if (node instanceof Project) {
                return (Project) node;
            }
        }
        return null;
    }
}

