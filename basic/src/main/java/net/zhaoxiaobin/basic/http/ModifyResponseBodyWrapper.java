/**
 * Copyright (C), 2015-2019
 */
package net.zhaoxiaobin.basic.http;

import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * 用来获取原响应体
 * @author zhaoxb
 * @create 2019-09-26 17:52
 */
@Data
public class ModifyResponseBodyWrapper extends HttpServletResponseWrapper {
    private HttpServletResponse originalResponse;
    private ByteArrayOutputStream baos;
    private ServletOutputStream out;
    private PrintWriter writer;

    @SneakyThrows
    public ModifyResponseBodyWrapper(HttpServletResponse resp) {
        super(resp);

        /*
        原始响应对象
         */
        this.originalResponse = resp;

        /*
        缓存响应数据的输出流
         */
        baos = new ByteArrayOutputStream();

        /*
        该类是内部类
        可以将后续执行后的响应数据获取此输出流后写出（写到传入的ByteArrayOutputStream中）
         */
        out = new SubServletOutputStream(baos);

        /*
        字符流
         */
        writer = new PrintWriter(new OutputStreamWriter(baos));
    }


    @Override
    public ServletOutputStream getOutputStream() {
        return out;
    }


    @Override
    public PrintWriter getWriter() {
        return writer;
    }


    /**
     * 将out、writer中的数据强制输出到SubServletOutputStream的baos里面，否则取不到数据
     */
    public String getResponseBody() throws IOException {
        return this.getResponseBody(null);
    }

    public String getResponseBody(String charset) throws IOException {
        out.flush();
        writer.flush();
        return new String(baos.toByteArray(), StringUtils.isBlank(charset) ? this.getCharacterEncoding() : charset);
    }


    class SubServletOutputStream extends ServletOutputStream {
        private ByteArrayOutputStream baos;

        public SubServletOutputStream(ByteArrayOutputStream baos) {
            this.baos = baos;
        }

        @Override
        public void write(int b) {
            baos.write(b);
        }

        @Override
        public void write(byte[] b) {
            baos.write(b, 0, b.length);
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }
    }
}