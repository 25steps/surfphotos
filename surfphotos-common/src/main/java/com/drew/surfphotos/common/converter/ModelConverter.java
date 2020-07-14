package com.drew.surfphotos.common.converter;

import java.util.List;

/**
 * Параметризированный интерфейс. Метод convert принимает источник класса и указываем в какой класс мы хотим его преобразовать
 * возвращает данный преобразованный класс, тоже в convertList
 */
public interface ModelConverter {
    <S,D> D convert(S source, Class<D> destinationClass);

    <S,D> List<D> convertList(List<S> source, Class<D> destinationClass);
}
