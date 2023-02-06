package com.jingde.equipment.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by oceanover on 2019-04-04.
 */
public class XmlUtil {
    public static String toXmlString(Object obj) {
        String result;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static <T> T parseObject(String input, Class<T> claaz) {
        Object result;
        try {
            JAXBContext context = JAXBContext.newInstance(claaz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            result = unmarshaller.unmarshal(new StringReader(input));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return (T) result;
    }
}
