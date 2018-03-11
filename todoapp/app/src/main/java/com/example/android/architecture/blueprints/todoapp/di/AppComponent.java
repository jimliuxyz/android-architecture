package com.example.android.architecture.blueprints.todoapp.di;

import android.app.Application;

import com.example.android.architecture.blueprints.todoapp.ToDoApplication;
import com.example.android.architecture.blueprints.todoapp.data.source.Client1;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * This is a Dagger component. Refer to {@link ToDoApplication} for the list of Dagger components
 * used in this application.
 * <p>
 * Even though Dagger allows annotating a {@link Component} as a singleton, the code
 * itself must ensure only one instance of the class is created. This is done in {@link
 * ToDoApplication}.
 * //{@link AndroidSupportInjectionModule}
 * // is the module from Dagger.Android that helps with the generation
 * // and location of subcomponents.
 */
@Singleton
@Component(modules = {TasksRepositoryModule.class,
        ApplicationModule.class,
        ActivityBindingModule.class,
        //seeu 至少加入AndroidSupportInjectionModule
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<ToDoApplication> {

    TasksRepository getTasksRepository();//seeu 都用在AndroidTest中 不知何目的?

    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }

    //seeu 自訂要接受注射的Client 一定要指定切確的型別 要讓dagger能看到@Inject在哪 才知道需要哪些依賴
    //像是不能用Object,雖然是Client1的父類,但Object類型上並沒有註解要注入什麼
    //如果Client1也是透過dagger產生 就不需要這這個動作 除非client1是自己new出來的
    void injectAny(Client1 any);
}
