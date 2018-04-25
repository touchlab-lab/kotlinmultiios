package co.touchlab.kurgan.architecture.database.framework

import android.content.Context
import co.touchlab.kurgan.architecture.database.support.SupportSQLiteOpenHelper

class FrameworkSQLiteOpenHelperFactory : SupportSQLiteOpenHelper.Factory {
    override fun create(configuration: SupportSQLiteOpenHelper.Configuration): SupportSQLiteOpenHelper {
        return FrameworkSQLiteOpenHelper(configuration.context.nativeContext() as Context,
                configuration.name,
                configuration.callback!!)
    }
}