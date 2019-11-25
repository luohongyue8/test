package com.cat.demo.Object;

import java.util.ArrayList;
import java.util.List;

public class GenderCatInfo {
    private List<CatInfo> maleCats = new ArrayList<>();
    private List<CatInfo> femaleCats = new ArrayList<>();

    public List<CatInfo> getMaleCats() {
        return maleCats;
    }

    public void setMaleCats(List<CatInfo> maleCats) {
        this.maleCats = maleCats;
    }

    public List<CatInfo> getFemaleCats() {
        return femaleCats;
    }

    public void setFemaleCats(List<CatInfo> femaleCats) {
        this.femaleCats = femaleCats;
    }
}
