package org.apache.joran.action;

import org.apache.joran.ErrorItem;
import org.apache.joran.ExecutionContext;

import org.apache.log4j.Logger;
import org.apache.log4j.config.PropertySetter;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.xml.sax.Attributes;

public class ParamAction extends Action {

  final static Logger logger = Logger.getLogger(ParamAction.class);

	static String NO_NAME = "No name attribute in <param> element";
	static String NO_VALUE = "No name attribute in <param> element";
  
  boolean inError = false;
  
  public void begin(ExecutionContext ec, String localName, Attributes attributes) {
		String name = attributes.getValue(NAME_ATTRIBUTE);
		String value = attributes.getValue(VALUE_ATTRIBUTE);

    if(name==null) {
			inError = true;
			logger.error(NO_NAME);
			ec.addError(new ErrorItem(NO_NAME));	
    	return;
    }

		if(value==null) {
			inError = true;
			logger.error(NO_VALUE);
			ec.addError(new ErrorItem(NO_VALUE));	
			return;
		}
    
    // remove both leading and trailing spaces
    value = value.trim();
    
		Object o = ec.peekObject();
		PropertySetter propSetter = new PropertySetter(o);		
		value = ec.subst(OptionConverter.convertSpecialChars(value));
    
    // allow for variable substitution for name as well
    name = ec.subst(name);
    
    LogLog.debug("In ParamAction setting parameter ["+name+"] to value ["+value+"].");
		propSetter.setProperty(name, value);
  }

  public void end(ExecutionContext ec, String localName) {
  }

  public void finish(ExecutionContext ec) {
  }
}