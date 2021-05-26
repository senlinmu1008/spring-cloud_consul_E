package net.zhaoxiaobin.basic.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

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
public class Resp_Entity {
    @ApiModelProperty(value = "响应码")
    @XStreamAlias("returnCode")
    @JsonProperty("returnCode")
    @JacksonXmlProperty(localName = "returnCode")
    private String returnCode;

    @ApiModelProperty(value = "响应信息")
    @XStreamAlias("returnMsg")
    @JsonProperty("returnMsg")
    @JacksonXmlProperty(localName = "returnMsg")
    private String returnMsg;

    @ApiModelProperty(value = "响应详细信息")
    @XStreamAlias("details")
    @JsonProperty("details")
    @JacksonXmlProperty(localName = "details")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Resp_Detail> details;

}
