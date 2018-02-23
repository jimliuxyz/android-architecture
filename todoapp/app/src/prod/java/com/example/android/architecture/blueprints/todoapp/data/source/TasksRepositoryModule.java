package com.example.android.architecture.blueprints.todoapp.data.source;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksDao;
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource;
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase;
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource;
import com.example.android.architecture.blueprints.todoapp.util.AppExecutors;
import com.example.android.architecture.blueprints.todoapp.util.DiskIOThreadExecutor;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This is used by Dagger to inject the required arguments into the {@link TasksRepository}.
 */
@Module
public class TasksRepositoryModule {

    private static final int THREAD_COUNT = 3;

    //seeu 當module中有同樣回傳類型的provider就要用@Qualifier區別 注入處才能區別需要的是哪個
    @Singleton
    @Provides
    @Local //1.回傳TasksDataSource @Qualifier設@Local
    TasksDataSource provideTasksLocalDataSource(TasksDao dao, AppExecutors executors) {
        return new TasksLocalDataSource(executors, dao);
    }

    @Singleton
    @Provides
    @Remote //2.回傳也是TasksDataSource @Qualifier設@Remote
    TasksDataSource provideTasksRemoteDataSource() {
        return new TasksRemoteDataSource();
    }

    @Singleton
    @Provides
    ToDoDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), ToDoDatabase.class, "Tasks.db")
                .build();
    }

    @Singleton
    @Provides
    TasksDao provideTasksDao(ToDoDatabase db) {
        return db.taskDao();
    }

    @Singleton
    @Provides
    AppExecutors provideAppExecutors() {
        //seeu AppExecutors沒有依賴其他輸入 僅是提供別人依賴 作為基礎的依賴提供者
        return new AppExecutors(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }

    //seeu 不一定要建provide函數 但若Dep建構時需要伴隨一些參數時可以考慮使用 否則只要在Dep的建構子加@Inject
    //seeu provide有點像是取代了@Inject 有provide就可以不用在Dep加@inject了
//    @Singleton
//    @Provides
//    Dependency1 provideD1(TasksRemoteDataSource remote, TasksLocalDataSource local){
//        return new Dependency1(remote, local);
//    }

    //seeu 可以取代@Binds 但這裡的scope會受Component牽制故不能再設定
//    @Provides
//    TasksContract.Presenter provideTasksPresenter(TasksRepository tasksRepository) {
//        return new TasksPresenter(tasksRepository);
//    }
}
