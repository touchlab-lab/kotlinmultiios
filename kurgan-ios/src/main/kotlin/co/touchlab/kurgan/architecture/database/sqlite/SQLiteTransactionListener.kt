package co.touchlab.kurgan.architecture.database.sqlite

import objcsrc.*

actual interface SQLiteTransactionListener{
    /**
     * Called immediately after the transaction begins.
     */
    actual fun onBegin()

    /**
     * Called immediately before commiting the transaction.
     */
    actual fun onCommit()

    /**
     * Called if the transaction is about to be rolled back.
     */
    actual fun onRollback()
}

class WrappedTransactionListener(val wrappedListener:SQLiteTransactionListener):ComKgalliganJustdbextractSharedIosSQLiteTransactionListener(){
    override fun onBegin(): Unit{
        wrappedListener.onBegin()
    }

    override fun onCommit(): Unit{
        wrappedListener.onCommit()
    }

    override fun onRollback(): Unit{
        wrappedListener.onRollback()
    }
}