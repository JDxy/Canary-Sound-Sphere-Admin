package com.admin.canarysoundsphereadmin.models;
public class Author {
    private String _id;
    private String name;
    private String image;
    private int foundation_year;
    private String music_type;
    private String description;
    private String music_list;


    public Author(String _id, String name, String image, int foundation_year, String music_type, String description, String music_list) {
        this._id = _id;
        this.name = name;
        this.image = image;
        this.foundation_year = foundation_year;
        this.music_type = music_type;
        this.description = description;
        this.music_list = music_list;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getFoundation_year() {
        return foundation_year;
    }

    public void setFoundation_year(int foundation_year) {
        this.foundation_year = foundation_year;
    }

    public String getMusic_type() {
        return music_type;
    }

    public void setMusic_type(String music_type) {
        this.music_type = music_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMusic_list() {
        return music_list;
    }

    public void setMusic_list(String music_list) {
        this.music_list = music_list;
    }
}
