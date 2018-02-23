package com.example.android.architecture.blueprints.todoapp.tasks;

import com.example.android.architecture.blueprints.todoapp.di.ActivityScoped;
import com.example.android.architecture.blueprints.todoapp.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link TasksPresenter}.
 */
@Module //seeu 個別activity的module(依賴工廠)
public abstract class TasksModule {
    @FragmentScoped
    @ContributesAndroidInjector //seeu 這可能與併入android生命週期有關 依賴物必須是一個framework type
    abstract TasksFragment tasksFragment();

    @ActivityScoped
    @Binds //seeu 有點像轉型 輸入TasksPresenter類 輸出TasksContract.Presenter介面
    // 讓dagger知道TasksContract.Presenter要用TasksPresenter注入 注射處就可以宣告成其介面或父類
    abstract TasksContract.Presenter taskPresenter(TasksPresenter presenter);

    //seeu 不用@Binds也是可以 只是注射處的型別指定要精準(不能用其父類或介面)
    //abstract TasksPresenter taskPresenter();
}
