package com.example.hdvideoplayer.fragment;

public class Videomodel {
    String name;
    String path;
    String album;
    String artist;
    long  dure;


    public Videomodel(String name, String path, String album, String artist, long  dure) {
        this.name = name;
        this.path = path;
        this.album = album;
        this.artist = artist;
        this.dure=dure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getDure() {
        return dure;
    }

    public void setDure(long  dure) {
        this.dure = dure;
    }

    @Override
    public String toString() {
        return "Videomodel{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", dure='" + dure + '\'' +
                '}';
    }
}
