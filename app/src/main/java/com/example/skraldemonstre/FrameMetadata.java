package com.example.skraldemonstre;

//Class adapted from: https://github.com/googlesamples/mlkit/blob/3f41503f4e917129039391d3d4096da6bfa75673/android/automl/app/src/main/java/com/google/mlkit/vision/automl/demo/FrameMetadata.java#L26
public class FrameMetadata {

    private final int width;
    private final int height;

    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }

    private FrameMetadata(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static class Builder {
        private int width;
        private int height;

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public FrameMetadata build() {
            return new FrameMetadata(width, height);
        }
    }
}
