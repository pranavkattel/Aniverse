/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aniverse.controller;

import com.aniverse.model.AnimeModel;
import java.util.List;

/**
 *
 * @author Pranav
 */
public class BinarySearch {
    public AnimeModel searchByName(String searchValue, List<AnimeModel> animeList, int left, int right) {
        if (right < left) {
            return null;
        }

        int mid = (left + right) / 2;

        if (searchValue.equalsIgnoreCase(animeList.get(mid).getTitle())) {
            return animeList.get(mid);
        } else if (searchValue.compareToIgnoreCase(animeList.get(mid).getTitle()) < 0) {
            return searchByName(searchValue, animeList, left, mid - 1);
        } else {
            return searchByName(searchValue, animeList, mid + 1, right);
        }
    }
}
