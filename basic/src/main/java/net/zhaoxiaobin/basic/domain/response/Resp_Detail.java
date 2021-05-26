/**
 * Copyright (C), 2015-2019
 */
package net.zhaoxiaobin.basic.domain.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author zhaoxb
 * @create 2019-11-15 22:46
 */
@Accessors(chain = true)
@Data
public class Resp_Detail {
    @ApiModelProperty(value = "机构编号", example = "310000001")
    @JsonProperty("orgId")
    @JacksonXmlProperty(localName = "orgId")
    @XStreamAlias("orgId")
    @JacksonXmlCData
    @Length(max = 40, message = "机构编号长度不能超过40")
    private String orgId;

    @ApiModelProperty(value = "机构名称", example = "大田后生仔机构", required = true)
    @NotBlank(message = "机构名称不能为空")
    @JsonProperty("orgName")
    @JacksonXmlProperty(localName = "orgName")
    @XStreamAlias("orgName")
    @JacksonXmlCData
    @Length(max = 240, message = "机构名称长度不能超过240")
    private String orgName;

    @JacksonXmlProperty(isAttribute = true)
    @JsonIgnore
    @JSONField(serialize = false)
    @XStreamAsAttribute
    private String type = "G";
}