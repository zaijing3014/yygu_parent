package com.attguigu.abcommon.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzj
 * @date 2022/10/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YyghException extends RuntimeException {
    @ApiModelProperty(value = "状态码")
    private String mes;
    private Integer code;
}
