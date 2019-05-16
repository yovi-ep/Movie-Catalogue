package com.yeputra.moviecatalogue.base;

import java.util.List;

/**
 * Created by yovi.putra
 *    on 14/Feb/2019 15:36
 * Company SIEMO - PT. Multipolar Technology, Tbk
 */
public interface FilterResultListener <T>{
    List<T> onFilterResult(String query);
}
