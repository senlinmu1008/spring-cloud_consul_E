package net.zhaoxiaobin.basic.domain.request;

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
 * @date 2019-11-15 22:51
 * @return 
 */
@Accessors(chain = true)
@Data
public class Req_Detail {

    @ApiModelProperty(value = "学校", example = "浙江大学", required = true)
    @NotBlank(message = "学校不能为空")
    @XStreamAlias("school")
    @JsonProperty("school")
    @JacksonXmlProperty(localName = "school")
    @JacksonXmlCData
    @Length(max = 240, message = "学校不能超过240")
    private String school;

    @ApiModelProperty(value = "邮政编码", example = "310000", required = true)
    @NotBlank(message = "邮政编码不能为空")
    @XStreamAlias("zipCode")
    @JsonProperty("zipCode")
    @JacksonXmlProperty(localName = "zipCode")
    @JacksonXmlCData
    @Length(max = 6, message = "邮政编码长度不能超过6")
    private String zipCode;

    @JacksonXmlProperty(isAttribute = true)
    @JsonIgnore
    @JSONField(serialize = false)
    @XStreamAsAttribute
    private String type = "G";

}
