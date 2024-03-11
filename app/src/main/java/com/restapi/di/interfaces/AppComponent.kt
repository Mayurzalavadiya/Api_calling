package com.restapi.di.interfaces

import com.restapi.di.module.RetrofitModule
import com.restapi.ui.activity.demo.activity.DemoActivity
import com.restapi.ui.activity.demo2.activity.PaginationActivity
import com.restapi.ui.activity.task1.activity.FactActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface AppComponent {

    fun inject(demoActivity: DemoActivity)

    fun inject(paginationActivity: PaginationActivity)

    fun inject(factActivity: FactActivity)
}