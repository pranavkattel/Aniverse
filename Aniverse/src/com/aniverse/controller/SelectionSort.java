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
public class SelectionSort {
    public List<AnimeModel> sortByEpisodes(List<AnimeModel> animeList, boolean ascending) {
        for (int i = 0; i < animeList.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < animeList.size(); j++) {
                if ((ascending && animeList.get(j).getEpisodes() < animeList.get(minIndex).getEpisodes()) ||
                    (!ascending && animeList.get(j).getEpisodes() > animeList.get(minIndex).getEpisodes())) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                AnimeModel temp = animeList.get(i);
                animeList.set(i, animeList.get(minIndex));
                animeList.set(minIndex, temp);
            }
        }
        return animeList;
    }
}
