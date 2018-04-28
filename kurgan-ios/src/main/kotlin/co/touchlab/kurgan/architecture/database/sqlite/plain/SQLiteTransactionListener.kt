package co.touchlab.kurgan.architecture.database.sqlite.plain

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