package com.dxl.wechatsell.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 17:22 on 2020/1/26.
 */
public class Date2LongSerializer extends JsonSerializer<Date> {

	//时间的转换，ms -> s
	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
		jsonGenerator.writeNumber(date.getTime() / 1000);
	}
}
