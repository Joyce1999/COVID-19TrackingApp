package com.CA326MyBubble.Layout_Models;

public class SlideModel {
    private String heading;
            private String subHeading;
            private int image;

    // Slides model
    public SlideModel(String heading, String subHeading, int image) {
        this.heading = heading;
        this.subHeading = subHeading;
        this.image = image;

    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
