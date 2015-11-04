import com.google.common.collect.Table

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 11/4/15
 * (C) 2015 Damian Wieczorek
 */
operator fun <R, C, V> Table<R, C, V>.set(rowKey: R, columnKey: C, value: V) = put(rowKey, columnKey, value)
