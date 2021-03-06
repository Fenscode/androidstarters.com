package <%= appPackage %>.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import <%= appPackage %>.di.component.ArmsComponent;
import <%= appPackage %>.di.module.ArmsModule;
import <%= appPackage %>.lifecycle.delegate.AppLifecycles;
import <%= appPackage %>.lifecycle.delegate.ILifecycle;
import <%= appPackage %>.lifecycle.di.component.LifecycleComponent;
import <%= appPackage %>.lifecycle.di.module.LifecycleModule;
import <%= appPackage %>.repository.IRepository;
import <%= appPackage %>.repository.di.component.RepositoryComponent;
import <%= appPackage %>.repository.di.module.RepositoryModule;

/**
 * Created by xiaobailong24 on 2017/7/13.
 * MVVMArms ArmsApplication
 */
public class ArmsApplication extends Application
        implements IArms, ILifecycle, IRepository, HasActivityInjector, HasSupportFragmentInjector {
    //Dagger.Android Activity 注入
    @Inject
    DispatchingAndroidInjector<Activity> mActivityInjector;
    //Dagger.Android Fragment 注入
    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;

    private AppLifecycles mAppDelegate;

    /**
     * 这里会在 {@link ArmsApplication#onCreate} 之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     *
     * @param context
     */
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        mAppDelegate = new AppDelegate(context);
        mAppDelegate.attachBaseContext(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAppDelegate.onCreate(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mAppDelegate.onTerminate(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return this.mActivityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return this.mFragmentInjector;
    }

    @Override
    public LifecycleComponent getLifecycleComponent() {
        return ((ILifecycle) mAppDelegate).getLifecycleComponent();
    }

    @Override
    public LifecycleModule getLifecycleModule() {
        return ((ILifecycle) mAppDelegate).getLifecycleModule();
    }

    @Override
    public RepositoryComponent getRepositoryComponent() {
        return ((IRepository) mAppDelegate).getRepositoryComponent();
    }

    @Override
    public RepositoryModule getRepositoryModule() {
        return ((IRepository) mAppDelegate).getRepositoryModule();
    }

    @Override
    public ArmsComponent getArmsComponent() {
        return ((IArms) mAppDelegate).getArmsComponent();
    }

    @Override
    public ArmsModule getArmsModule() {
        return ((IArms) mAppDelegate).getArmsModule();
    }
}
