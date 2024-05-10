package com.loja.loja.Enum;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrderStatus {
    PENDING,
    PROCESSING,
    COMPLETE,
    CANCELED
}
