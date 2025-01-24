package com.vscing.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserApiLocationVo {

    @Schema(description = "城市经度")
    private String lng;

    @Schema(description = "城市维度")
    private String lat;

}
