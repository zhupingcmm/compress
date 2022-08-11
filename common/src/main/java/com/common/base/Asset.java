package com.common.base;

import com.common.exception.CompressException;

import java.util.List;

public class Asset <T>{

    public static <T> boolean notNull(T data) {
        if (data == null) {throw new CompressException(ResponseEnum.NO_ROWS_AFFECTED);}
        return true;
    }


    /**
     * 只新增或更新一行数据
     *
     * @param result
     * @return
     */
    public static boolean singleRowAffected(int result) {
        if (result == 0) {
            throw new CompressException(ResponseEnum.NO_ROWS_AFFECTED);
        } else if (result > 1) {
            throw new CompressException(ResponseEnum.TOO_MANY_ROWS_AFFECTED);
        }
        return true;
    }

    /**
     * 数据操作不全
     *
     * @param result
     * @param list
     * @return
     */
    public static boolean totalRowsAffected(int result, List list) {
        if (list.isEmpty() || list.size() != result) {
            throw new CompressException(ResponseEnum.NOT_TOTAL_ROWS_AFFECTED);
        }
        return true;
    }
}
