package com.example.android.architecture.blueprints.todoapp;

import android.app.Application;
import android.os.Handler;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.android.architecture.blueprints.todoapp.data.source.Client1;
import com.example.android.architecture.blueprints.todoapp.data.source.Dependency1;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;
import com.example.android.architecture.blueprints.todoapp.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * We create a custom {@link Application} class that extends  {@link DaggerApplication}.
 * We then override applicationInjector() which tells Dagger how to make our @Singleton Component
 * We never have to call `component.inject(this)` as {@link DaggerApplication} will do that for us.
 */
public class ToDoApplication extends DaggerApplication {
    @Inject
    TasksRepository tasksRepository;

    @Inject
    Dependency1 dep1;

    @Inject
    Lazy<Dependency1> dep1Provider;

    @Inject
    Client1 client1 = new Client1();

    Client1 client2 = new Client1();

    DaggerAppComponent injector;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {

        new Handler().postDelayed(
            new Runnable() {
                @Override
                public void run() {
                    //1.dep已自動注入
                    Log.i("trace", "ToDoApplication::applicationInjector: " + dep1);

                    //2.自動產生了provider函數
                    Log.i("trace", "ToDoApplication::applicationInjector: " + dep1Provider.get());

                    //3.透過dagger建立的物件
                    Log.i("trace", "ToDoApplication::applicationInjector: " + client1.dep);

                    //4.自建C 自注入依賴
                    injector.injectAny(client2);
                    Log.i("trace", "ToDoApplication::applicationInjector: " + client2.dep);

                    //0. >> dep1 === dep1Provider.get() === client1.dep === client2.dep
                }
            }
        ,3000);

        //0.實體化注射器 此時注射器已經可以使用了
        injector = (DaggerAppComponent)DaggerAppComponent.builder().application(this).build();
        return injector;
    }

    /**
     * Our Espresso tests need to be able to get an instance of the {@link TasksRepository}
     * so that we can delete all tasks before running each test
     */
    @VisibleForTesting
    public TasksRepository getTasksRepository() {
        return tasksRepository;
    }
}
