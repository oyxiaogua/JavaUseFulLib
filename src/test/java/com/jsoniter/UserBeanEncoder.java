package com.jsoniter;

public class UserBeanEncoder implements com.jsoniter.spi.Encoder {
	public void encode(java.lang.Object obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
		if (obj == null) {
			stream.writeNull();
			return;
		}
		encode_((UserBean) obj, stream);
	}

	public static void encode_(UserBean obj, com.jsoniter.output.JsonStream stream) throws java.io.IOException {
		stream.writeObjectStart();
		stream.writeIndention();
		stream.writeObjectField("myFirstName");
		stream.writeVal((String) obj.getFirstName());
		stream.writeMore();
		stream.writeObjectField("myLastName");
		stream.writeVal((String) obj.getLastName());
		stream.writeMore();
		stream.writeObjectField("myScore");
		stream.writeVal((int) obj.getScore());
		stream.writeObjectEnd();
	}
}