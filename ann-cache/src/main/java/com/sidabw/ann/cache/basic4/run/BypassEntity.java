package com.sidabw.ann.cache.basic4.run;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BypassEntity implements Serializable {

    private String name;

    private String describe;

}
