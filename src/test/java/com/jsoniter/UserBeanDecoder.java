package com.jsoniter;

import java.io.IOException;

public class UserBeanDecoder implements com.jsoniter.spi.Decoder {

	public Object decode(JsonIterator iter) throws IOException {
		UserBean obj = new UserBean();
		for (String field = iter.readObject(); field != null; field = iter.readObject()) {
		    switch (field) {
		        case "myFirstName":
		            obj.setFirstName(iter.readString());
		            continue;
		        case "myLastName":
		        	obj.setLastName(iter.readString());
		        	continue;
		        case "myScore":
		        	obj.setScore(iter.readInt());
		            continue;
		        default:
		            iter.skip();
		    }
		}
		return obj;
	}

}
