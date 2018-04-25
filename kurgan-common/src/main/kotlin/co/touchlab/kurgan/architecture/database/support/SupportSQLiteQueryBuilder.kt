package co.touchlab.kurgan.architecture.database.support

class SupportSQLiteQueryBuilder private constructor(table: String) {
    companion object {
        val sLimitPattern = Regex("\\s*\\d+\\s*(,\\s*\\d+\\s*)?")
        fun builder(tableName:String):SupportSQLiteQueryBuilder = SupportSQLiteQueryBuilder(tableName)

        private fun appendClause(s: StringBuilder, name: String, clause: String?) {
            if (!isEmpty(clause)) {
                s.append(name)
                s.append(clause)
            }
        }

        /**
         * Add the names that are non-null in columns to s, separating
         * them with commas.
         */
        private fun appendColumns(s: StringBuilder, columns: Array<String>) {
            val n = columns.size

            for (i in 0 until n) {
                val column = columns[i]
                if (i > 0) {
                    s.append(", ")
                }
                s.append(column)
            }
            s.append(' ')
        }

        private fun isEmpty(input: String?): Boolean {
            return input == null || input.isEmpty()
        }
    }

    private var mDistinct = false
    private val mTable:String = table
    private var mColumns:Array<String>? = null
    private var mSelection:String? = null
    private var mBindArgs:Array<Any?>? = null
    private var mGroupBy:String? = null
    private var mHaving:String? = null
    private var mOrderBy:String? = null
    private var mLimit:String? = null

    /**
     * Adds DISTINCT keyword to the query.
     *
     * @return this
     */
    fun distinct():SupportSQLiteQueryBuilder {
        mDistinct = true
        return this
    }

    /**
     * Sets the given list of columns as the columns that will be returned.
     *
     * @param columns The list of column names that should be returned.
     *
     * @return this
     */
    fun columns( columns:Array<String>):SupportSQLiteQueryBuilder {
        mColumns = columns
        return this
    }

    /**
     * Sets the arguments for the WHERE clause.
     *
     * @param selection The list of selection columns
     * @param bindArgs The list of bind arguments to match against these columns
     *
     * @return this
     */
    fun selection(selection:String , bindArgs:Array<Any?>):SupportSQLiteQueryBuilder {
        mSelection = selection;
        mBindArgs = bindArgs;
        return this;
    }

    /**
     * Adds a GROUP BY statement.
     *
     * @param groupBy The value of the GROUP BY statement.
     *
     * @return this
     */
    fun groupBy(groupBy:String):SupportSQLiteQueryBuilder {
        mGroupBy = groupBy;
        return this;
    }

    /**
     * Adds a HAVING statement. You must also provide {@link #groupBy(String)} for this to work.
     *
     * @param having The having clause.
     *
     * @return this
     */
    fun having(having:String):SupportSQLiteQueryBuilder {
        mHaving = having;
        return this;
    }

    /**
     * Adds an ORDER BY statement.
     *
     * @param orderBy The order clause.
     *
     * @return this
     */
    fun orderBy(orderBy:String):SupportSQLiteQueryBuilder {
        mOrderBy = orderBy;
        return this;
    }

    /**
     * Adds a LIMIT statement.
     *
     * @param limit The limit value.
     *
     * @return this
     */
    fun limit(limit:String):SupportSQLiteQueryBuilder {
        if (!isEmpty(limit) && !sLimitPattern.matches(limit)) {
            throw IllegalArgumentException("invalid LIMIT clauses:" + limit);
        }
        mLimit = limit;
        return this;
    }

    /**
     * Creates the {@link SupportSQLiteQuery} that can be passed into
     * {@link SupportSQLiteDatabase#query(SupportSQLiteQuery)}.
     *
     * @return a new query
     */
    fun  create():SupportSQLiteQuery {
        if (isEmpty(mGroupBy) && !isEmpty(mHaving)) {
            throw IllegalArgumentException(
                    "HAVING clauses are only permitted when using a groupBy clause");
        }
        val query = StringBuilder(120)

        query.append("SELECT ")
        if (mDistinct) {
            query.append("DISTINCT ");
        }
        if (mColumns != null && mColumns?.size != 0) {
            appendColumns(query, mColumns!!)
        } else {
            query.append(" * ");
        }
        query.append(" FROM ");
        query.append(mTable);
        appendClause(query, " WHERE ", mSelection);
        appendClause(query, " GROUP BY ", mGroupBy);
        appendClause(query, " HAVING ", mHaving);
        appendClause(query, " ORDER BY ", mOrderBy);
        appendClause(query, " LIMIT ", mLimit);

        return SimpleSQLiteQuery(query.toString(), mBindArgs)
    }
}