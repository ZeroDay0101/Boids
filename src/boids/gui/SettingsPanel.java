package boids.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SettingsPanel extends JPanel {
    private final JSlider cohesionSlider = new JSlider(1, 500, 13);
    private final JSlider alignmentSlider = new JSlider(1, 500, 13);
    private final JSlider separationSlider = new JSlider(1, 500, 85);


    public SettingsPanel() throws HeadlessException {
        Map<JSlider, JLabel> sliderLabelMap = new HashMap<>();
        JPanel parentPanel = new JPanel();
        parentPanel.setBackground(Color.pink);
        parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));


        // Cohesion Slider
        JPanel cohesionPanel = new JPanel();
        cohesionPanel.setBackground(Color.pink);


        JLabel cohesionLabel = new JLabel("Cohesion weight:");
        cohesionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cohesionSlider.setBackground(Color.pink);
        JLabel cohesionLabelValue = new JLabel(String.valueOf((double) cohesionSlider.getValue() / 10));

        cohesionPanel.add(cohesionSlider);
        cohesionPanel.add(cohesionLabelValue);

        sliderLabelMap.put(cohesionSlider, cohesionLabelValue);

        // alignment Slider
        JPanel alignmentPanel = new JPanel();
        alignmentPanel.setBackground(Color.pink);


        JLabel alignmentLabel = new JLabel("Alignment weight:");
        alignmentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        alignmentSlider.setBackground(Color.pink);
        JLabel alignmentLabelValue = new JLabel(String.valueOf((double) alignmentSlider.getValue() / 10));

        alignmentPanel.add(alignmentSlider);
        alignmentPanel.add(alignmentLabelValue);

        sliderLabelMap.put(alignmentSlider, alignmentLabelValue);

//        // Separation Slider
        JPanel separationPanel = new JPanel();
        separationPanel.setBackground(Color.pink);


        JLabel separationLabel = new JLabel("Separation weight:");
        separationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        separationSlider.setBackground(Color.pink);
        JLabel separationLabelValue = new JLabel(String.valueOf((double) separationSlider.getValue() / 10));

        separationPanel.add(separationSlider);
        separationPanel.add(separationLabelValue);

        sliderLabelMap.put(separationSlider, separationLabelValue);
//
        addListenersToSlider(sliderLabelMap);

        parentPanel.add(cohesionLabel);
        parentPanel.add(cohesionPanel);

        parentPanel.add(alignmentLabel);
        parentPanel.add(alignmentPanel);

        parentPanel.add(separationLabel);
        parentPanel.add(separationPanel);


        this.add(parentPanel);


    }

    private void addListenersToSlider(Map<JSlider, JLabel> sliderHashMap) {
        ChangeListener listener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                JLabel correspondingLabel = sliderHashMap.get(source);
                if (correspondingLabel != null) {
                    correspondingLabel.setText(String.valueOf((double) source.getValue() / 10));
                }
            }
        };
        for (JSlider slider : sliderHashMap.keySet()) {
            slider.addChangeListener(listener);
        }
    }

    public JSlider getCohesionSlider() {
        return cohesionSlider;
    }

    public JSlider getAlignmentSlider() {
        return alignmentSlider;
    }

    public JSlider getSeparationSlider() {
        return separationSlider;
    }
}
