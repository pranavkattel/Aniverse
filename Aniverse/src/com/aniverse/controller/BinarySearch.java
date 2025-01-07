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
   public AnimeModel searchByTitle(String searchValue, List<AnimeModel> animeList,
            int left, int right) {

        // Base Case: if the right index is smaller than left, return null
        if (right < left) {
            return null;
        }

        // Calculate mid index
        int mid = (left + right) / 2;

        // Check if searchValue matches the title at the mid index
        if (searchValue.compareToIgnoreCase(animeList.get(mid).getTitle()) == 0) {
            return animeList.get(mid);
        } 
        // If the search value is smaller, search the left half
        else if (searchValue.compareToIgnoreCase(animeList.get(mid).getTitle()) < 0) {
            return searchByTitle(searchValue, animeList, left, mid - 1);
        } 
        // If the search value is greater, search the right half
        else {
            return searchByTitle(searchValue, animeList, mid + 1, right);
        }
    }
}
