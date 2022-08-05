package com.sidabw.ann.cache.basic3.run;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoEntity implements Serializable {

    private String name;

    private String describe;
}
