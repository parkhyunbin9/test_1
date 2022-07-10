package com.reco.prob.domain.imagemeta;

public enum ImageFormat {
      JPG("jpg")
    , JPEG("jpeg")
    , JPE("jpe")
    , PNG("png")
    , GIF("gif")
    , PDF("pdf")
    , TIFF("tiff")
    , BMP("bmp");

    private final String format;

    ImageFormat(String format) {
        this.format = format;
    }

    public String getImageFormat() {
        return this.format;
    }
}
