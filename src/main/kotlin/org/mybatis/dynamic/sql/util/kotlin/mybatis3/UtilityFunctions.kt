/**
 *    Copyright 2016-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.dynamic.sql.util.kotlin.mybatis3

import org.mybatis.dynamic.sql.BasicColumn
import org.mybatis.dynamic.sql.SqlBuilder
import org.mybatis.dynamic.sql.SqlTable
import org.mybatis.dynamic.sql.insert.InsertDSL
import org.mybatis.dynamic.sql.insert.MultiRowInsertDSL
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider
import org.mybatis.dynamic.sql.render.RenderingStrategies
import org.mybatis.dynamic.sql.select.QueryExpressionDSL
import org.mybatis.dynamic.sql.select.SelectModel
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider
import org.mybatis.dynamic.sql.util.kotlin.DeleteCompleter
import org.mybatis.dynamic.sql.util.kotlin.SelectCompleter
import org.mybatis.dynamic.sql.util.kotlin.UpdateCompleter
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils

fun deleteFrom(table: SqlTable, complete: DeleteCompleter) =
        complete(SqlBuilder.deleteFrom(table)).build().render(RenderingStrategies.MYBATIS3)

fun <T> insert(mapper: (InsertStatementProvider<T>) -> Int, record: T, table: SqlTable,
               completer: InsertDSL<T>.() -> InsertDSL<T>) =
        MyBatis3Utils.insert(mapper, record, table, completer)

fun <T> insertMultiple(mapper: (MultiRowInsertStatementProvider<T>) -> Int, records: Collection<T>, table: SqlTable,
                       completer: MultiRowInsertDSL<T>.() -> MultiRowInsertDSL<T>) =
        MyBatis3Utils.insertMultiple(mapper, records, table, completer)

fun QueryExpressionDSL.FromGatherer<SelectModel>.from(table: SqlTable,
                                                      complete: SelectCompleter) =
        complete(from(table)).build().render(RenderingStrategies.MYBATIS3)

fun QueryExpressionDSL.FromGatherer<SelectModel>.from(table: SqlTable, alias: String,
                                                      complete: SelectCompleter) =
        complete(from(table, alias)).build().render(RenderingStrategies.MYBATIS3)

fun update(table: SqlTable, complete: UpdateCompleter) =
        complete(SqlBuilder.update(table)).build().render(RenderingStrategies.MYBATIS3)

/**
 * Function to handle platform type issues. Use this function, instead of calling MyBatis3Utils directly, to
 * avoid having to specify a return type explicitly.
 */
fun <T> selectDistinct(mapper: (SelectStatementProvider) -> List<T>, selectList: List<BasicColumn>, table: SqlTable,
                       completer: SelectCompleter): List<T> =
        MyBatis3Utils.selectDistinct(mapper, selectList, table, completer)

/**
 * Function to handle platform type issues. Use this function, instead of calling MyBatis3Utils directly, to
 * avoid having to specify a return type explicitly.
 */
fun <T> selectList(mapper: (SelectStatementProvider) -> List<T>, selectList: List<BasicColumn>, table: SqlTable,
                   completer: SelectCompleter): List<T> =
        MyBatis3Utils.selectList(mapper, selectList, table, completer)
