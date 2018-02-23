/*
 * Copyright 2016, The Android Open Source Project
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

package com.example.android.architecture.blueprints.todoapp.data.source;

import android.util.Log;

import javax.inject.Inject;

import dagger.Reusable;

@Reusable //seeu 自訂依賴物 最簡單提供注入 (說明Module不是必須的)
public class Dependency1 {

    private final TasksDataSource mTasksRemoteDataSource;

    private final TasksDataSource mTasksLocalDataSource;

    @Inject
    Dependency1(@Remote TasksDataSource tasksRemoteDataSource,
                @Local TasksDataSource tasksLocalDataSource) {
        Log.i("trace", "Dependency1::Dependency1: " + tasksRemoteDataSource);
        mTasksRemoteDataSource = tasksRemoteDataSource;
        mTasksLocalDataSource = tasksLocalDataSource;
    }
}
