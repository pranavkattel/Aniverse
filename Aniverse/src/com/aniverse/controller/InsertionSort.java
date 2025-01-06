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
public class InsertionSort {
    public List<AnimeModel> sortByScore(List<AnimeModel> animeList, boolean ascending) {
        for (int i = 1; i < animeList.size(); i++) {
            AnimeModel key = animeList.get(i);
            int j = i - 1;

            while (j >= 0 && ((ascending && animeList.get(j).getScore() > key.getScore()) ||
                             (!ascending && animeList.get(j).getScore() < key.getScore()))) {
                animeList.set(j + 1, animeList.get(j));
                j--;
            }
            animeList.set(j + 1, key);
        }
        return animeList;
    }
}
