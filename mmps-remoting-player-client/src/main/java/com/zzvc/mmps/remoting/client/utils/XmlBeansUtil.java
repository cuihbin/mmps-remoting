package com.zzvc.mmps.remoting.client.utils;

import java.io.File;
import java.io.IOException;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

public class XmlBeansUtil {
	private static XmlOptions xo = new XmlOptions();

	static {
		xo.setUseDefaultNamespace();
		xo.setCharacterEncoding("UTF-8");
	}
	
	static public void saveXmlDocument(XmlObject doc, String filename) throws IOException {
		saveXmlDocument(doc, new File(filename));
	}
	
	static public void saveXmlDocument(XmlObject doc, File file) throws IOException {
		if (file.getParentFile() != null && !file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				throw new IOException("Cannot make directory " + file.getParentFile().getAbsolutePath());
			}
		}
		doc.save(file, xo);
	}
	
	static public XmlObject loadXmlDocument(Class<XmlObject> clazz, File file) throws IOException, XmlException {
		XmlObject xobj = XmlObject.Factory.parse(file, xo);
		if (clazz.isInstance(xobj)) {
			if (xobj.validate()) {
				return xobj;
			}
		}
		throw new XmlException("Not a valid XML document for " + clazz.getName());
	}

}
