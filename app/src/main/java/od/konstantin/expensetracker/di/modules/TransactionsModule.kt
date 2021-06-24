package od.konstantin.expensetracker.di.modules

import dagger.Binds
import dagger.Module
import od.konstantin.expensetracker.data.repositories.TransactionsRepositoryImpl
import od.konstantin.expensetracker.domain.repositories.TransactionsRepository

@Module
abstract class TransactionsModule {

    @Binds
    abstract fun provideTransactionsRepository(repository: TransactionsRepositoryImpl): TransactionsRepository
}