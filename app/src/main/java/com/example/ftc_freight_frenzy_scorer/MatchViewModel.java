package com.example.ftc_freight_frenzy_scorer;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

public class MatchViewModel extends AndroidViewModel {

    private MatchRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private final LiveData<List<Match>> mAllMatches;
    private final LiveData<Integer> mLastMatch;

    public MatchViewModel(Application application) {
        super(application);
        mRepository = new MatchRepository(application);
        mAllMatches = mRepository.getAllWords();
        mLastMatch = mRepository.getLastMatch();
    }

    LiveData<List<Match>> getAllWords() {
        return mAllMatches;
    }

    LiveData<Integer> getLastMatch() {
        return mLastMatch;
    }

    void insert(Match match) {
        mRepository.insert(match);
    }
}
