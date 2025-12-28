package org.example.portfolio.enums;

public enum ImageSize {
    PROJECT_CARD (500, 0),

    PROJECT_BANNER (1200, 600),

    BLOG_THUMBNAIL (800, 450),

    BLOG_CONTENT (1000, 0),

    PROFILE_AVATAR (200, 200),

    HERO_BANNER (1600, 800);

    private final int width;
    private final int height;

    ImageSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
