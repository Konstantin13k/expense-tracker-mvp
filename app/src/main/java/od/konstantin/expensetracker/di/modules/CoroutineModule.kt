package od.konstantin.expensetracker.di.modules

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import od.konstantin.expensetracker.di.qualifiers.DefaultDispatcher
import od.konstantin.expensetracker.di.qualifiers.IODispatcher
import od.konstantin.expensetracker.di.qualifiers.MainDispatcher
import javax.inject.Singleton

@Module
class CoroutineModule {

    @Singleton
    @Provides
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Singleton
    @Provides
    @IODispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}