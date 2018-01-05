package com.shankeerthan;

import javafx.scene.control.ProgressBar;

public class ProgressUpdateable implements Runnable {
    ProgressBar progressBar;
    double progress = 0;

    ProgressUpdateable(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public void run() {
        progressBar.setProgress(progress);
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}
