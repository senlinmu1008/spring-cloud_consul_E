package net.zhaoxiaobin.basic.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import java.util.List;

/**
 *
 * @author zhaoxb
 * @date 2019-11-15 22:48
 * @return
 */
@Accessors(chain = true)
@Data
@XStreamAlias("body")
@JacksonXmlRootElement(localName = "body")
public class Req_Entity {
    @ApiModelProperty(value = "区县代码", example = "310115")
    @XStreamAlias("districtId")
    @JsonProperty("districtId")
    @JacksonXmlProperty(localName = "districtId")
    @JacksonXmlCData
    @Length(max = 6, message = "区县代码长度不能超过6")
    private String districtId;

    @ApiModelProperty(value = "经办机构编号", example = "310000000")
    @XStreamAlias("orgId")
    @JsonProperty("orgId")
    @JacksonXmlProperty(localName = "orgId")
    @JacksonXmlCData
    @Length(max = 9, message = "经办机构编号长度不能超过9")
    private String orgId;

    @ApiModelProperty(value = "用户姓名", example = "zhaoxb", required = true)
    @NotBlank(message = "用户姓名不能为空")
    @XStreamAlias("userName")
    @JsonProperty("userName")
    @JacksonXmlProperty(localName = "userName")
    @JacksonXmlCData
    @Length(max = 180, message = "用户姓名长度不能超过180")
    private String userName;

    @ApiModelProperty(value = "出生日期", example = "19900307")
    @XStreamAlias("birthDay")
    @JsonProperty("birthDay")
    @JacksonXmlProperty(localName = "birthDay")
    @JacksonXmlCData
    @Length(max = 8, message = "出生日期长度不能超过8")
    private String birthDay;

    @ApiModelProperty(value = "证件号码", example = "110101199003079833", required = true)
    @NotBlank(message = "证件号码不能为空")
    @XStreamAlias("idNo")
    @JsonProperty("idNo")
    @JacksonXmlProperty(localName = "idNo")
    @JacksonXmlCData
    @Length(max = 18, message = "证件号码长度不能超过18")
    private String idNo;

    @ApiModelProperty(value = "手机号码", example = "13485527323", required = true)
    @NotBlank(message = "移动电话号码不能为空")
    @XStreamAlias("telPhone")
    @JsonProperty("telPhone")
    @JacksonXmlProperty(localName = "telPhone")
    @JacksonXmlCData
    @Length(max = 240, message = "移动电话号码长度不能超过240")
    private String telPhone;

    @ApiModelProperty(value = "请求详细信息")
    @Valid
    @XStreamAlias("details")
    @XStreamImplicit(itemFieldName = "details")
    @JsonProperty("details")
    @JacksonXmlProperty(localName = "details")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Req_Detail> details;

}
