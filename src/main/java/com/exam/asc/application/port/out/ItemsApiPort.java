package com.exam.asc.application.port.out;

import com.exam.asc.domain.model.Item;

import java.util.List;

public interface ItemsApiPort {

    List<Item> obtenerItems();
}
