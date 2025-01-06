/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.aniverse.controller;

import com.aniverse.model.AnimeModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pranav
 */
public class MergeSort {
  public List<AnimeModel> sort(List<AnimeModel> animeList) {
        if (animeList.size() <= 1) {
            return animeList;  // Return the list as is if it's already sorted
        }

        // Split the list into two halves
        int mid = animeList.size() / 2;
        List<AnimeModel> first = animeList.subList(0, mid);
        List<AnimeModel> second = animeList.subList(mid, animeList.size());

        // Recursively sort both halves
        first = sort(first);
        second = sort(second);

        // Merge the sorted halves and return the result
        return merge(first, second);
    }

    public List<AnimeModel> merge(List<AnimeModel> first, List<AnimeModel> second) {
        List<AnimeModel> mainList = new ArrayList<>();
        int firstCounter = 0;
        int secondCounter = 0;

        // Compare elements from both halves and merge into the main list
        while (firstCounter < first.size() && secondCounter < second.size()) {
            if (first.get(firstCounter).getTitle().compareToIgnoreCase(second.get(secondCounter).getTitle()) <= 0) {
                mainList.add(first.get(firstCounter));
                firstCounter++;
            } else {
                mainList.add(second.get(secondCounter));
                secondCounter++;
            }
        }

        // Copy any remaining elements from the first half
        while (firstCounter < first.size()) {
            mainList.add(first.get(firstCounter));
            firstCounter++;
        }

        // Copy any remaining elements from the second half
        while (secondCounter < second.size()) {
            mainList.add(second.get(secondCounter));
            secondCounter++;
        }

        return mainList;  // Return the merged and sorted list
    }
}
