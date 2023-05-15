package com.marwaeltayeb.souq.utils;

import com.marwaeltayeb.souq.R;

import java.util.ArrayList;
import java.util.List;

public class Slide {

    private Slide(){}

    private static final List<Integer> slides = new ArrayList<>();

    static {
        slides.add(R.drawable.sl1);
        slides.add(R.drawable.sl2);
        slides.add(R.drawable.sl3);
        slides.add(R.drawable.sl4);
        slides.add(R.drawable.sl5);
        slides.add(R.drawable.sl6);
        slides.add(R.drawable.sl7);
    }

    public static List<Integer> getSlides() {
        return slides;
    }
}
