package com.loja.loja.Enum;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Availability {
    AVAILABLE,
    SOLD_OUT,
    LACKING
}
