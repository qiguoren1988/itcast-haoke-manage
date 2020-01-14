package com.mmall.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author solang
 * @date 2020-01-09 10:40
 */
@Setter
@Getter
public class TestVo {

    @NotBlank
    private String msg;

    @NotNull
    private Integer id;

//    @NotEmpty
//    private List<String> str;
}
